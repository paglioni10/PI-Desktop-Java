package Model.DAO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String PASTA_BANCO = "banco";
    private static final String ARQUIVO_BANCO = "maonaroda.db";
    private static final String ARQUIVO_SCRIPT = "script.sql";

    private static boolean estruturaVerificada = false;

    public static Connection abrir() throws SQLException {
        prepararBanco();
        Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + arquivoBanco().getPath());
        Statement comando = conexao.createStatement();
        comando.execute("PRAGMA foreign_keys = ON");
        comando.close();
        return conexao;
    }

    public static void fechar(Connection conexao) {
        if (conexao == null) {
            return;
        }
        try {
            conexao.close();
        } catch (SQLException erro) {
            System.out.println("Nao foi possivel fechar a conexao: " + erro.getMessage());
        }
    }

    private static synchronized void prepararBanco() throws SQLException {
        if (estruturaVerificada) {
            return;
        }

        File arquivo = arquivoBanco();
        boolean bancoNovo = !arquivo.exists();

        if (bancoNovo) {
            File pasta = arquivo.getParentFile();
            if (pasta != null && !pasta.exists()) {
                pasta.mkdirs();
            }
            executarScript();
        }

        estruturaVerificada = true;
    }

    private static void executarScript() throws SQLException {
        File script = arquivoScript();
        if (!script.exists()) {
            throw new SQLException("Script de criacao do banco nao encontrado em " + script.getAbsolutePath());
        }

        String conteudo;
        try {
            conteudo = new String(Files.readAllBytes(script.toPath()), Charset.forName("UTF-8"));
        } catch (IOException erro) {
            throw new SQLException("Nao foi possivel ler o script do banco", erro);
        }

        Connection conexao = DriverManager.getConnection("jdbc:sqlite:" + arquivoBanco().getPath());
        Statement comando = conexao.createStatement();
        try {
            for (String instrucao : conteudo.split(";")) {
                String limpa = removerComentarios(instrucao);
                if (!limpa.isEmpty()) {
                    comando.execute(limpa);
                }
            }
        } finally {
            comando.close();
            conexao.close();
        }
    }

    private static String removerComentarios(String instrucao) {
        StringBuilder resultado = new StringBuilder();
        for (String linha : instrucao.split("\n")) {
            if (!linha.trim().startsWith("--")) {
                resultado.append(linha).append("\n");
            }
        }
        return resultado.toString().trim();
    }

    private static File arquivoBanco() {
        return new File(pastaBanco(), ARQUIVO_BANCO);
    }

    private static File arquivoScript() {
        return new File(pastaBanco(), ARQUIVO_SCRIPT);
    }

    // o projeto pode ser executado pela IDE ou pelo jar em dist, por isso as duas tentativas
    private static File pastaBanco() {
        File naRaiz = new File(PASTA_BANCO);
        if (naRaiz.isDirectory()) {
            return naRaiz;
        }
        return new File("..", PASTA_BANCO);
    }
}
