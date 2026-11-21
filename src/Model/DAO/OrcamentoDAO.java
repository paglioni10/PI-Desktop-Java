package Model.DAO;

import Model.Datas;
import Model.Orcamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrcamentoDAO {

    public void insert(Orcamento orcamento) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO orcamento (valor, prazo_execucao_dias, validade, observacoes, status, "
                    + "id_solicitacao) VALUES (?, ?, ?, ?, ?, ?)");
            comando.setDouble(1, orcamento.getValor());
            comando.setInt(2, orcamento.getPrazoExecucaoDias());
            comando.setString(3, Datas.paraBanco(orcamento.getValidade()));
            comando.setString(4, orcamento.getObservacoes());
            comando.setString(5, orcamento.getStatus().name());
            comando.setInt(6, orcamento.getSolicitacao().getId());
            comando.executeUpdate();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
    }
}
