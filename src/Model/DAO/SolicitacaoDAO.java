package Model.DAO;

import Model.Cliente;
import Model.Datas;
import Model.Prestador;
import Model.Servico;
import Model.SolicitacaoServico;
import Model.StatusSolicitacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {

    private static final String SELECT_BASE
            = "SELECT sol.id, sol.data_solicitacao, sol.descricao_necessidade, sol.status, sol.valor_final, "
            + "cli.id AS id_cliente, cli.nome AS nome_cliente, "
            + "s.id AS id_servico, s.titulo AS titulo_servico, "
            + "pre.nome AS nome_prestador "
            + "FROM solicitacao_servico sol "
            + "JOIN usuario cli ON cli.id = sol.id_cliente "
            + "JOIN servico s ON s.id = sol.id_servico "
            + "JOIN usuario pre ON pre.id = s.id_prestador ";

    public void insert(SolicitacaoServico solicitacao) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO solicitacao_servico (data_solicitacao, descricao_necessidade, status, "
                    + "valor_final, id_cliente, id_servico) VALUES (?, ?, ?, ?, ?, ?)");
            comando.setString(1, Datas.paraBanco(solicitacao.getDataSolicitacao()));
            comando.setString(2, solicitacao.getDescricaoNecessidade());
            comando.setString(3, solicitacao.getStatus().name());
            comando.setDouble(4, solicitacao.getValorFinal());
            comando.setInt(5, solicitacao.getCliente().getId());
            comando.setInt(6, solicitacao.getServico().getId());
            comando.executeUpdate();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
    }

    public void atualizarStatus(int id, StatusSolicitacao status, double valorFinal) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(
                    "UPDATE solicitacao_servico SET status = ?, valor_final = ? WHERE id = ?");
            comando.setString(1, status.name());
            comando.setDouble(2, valorFinal);
            comando.setInt(3, id);
            comando.executeUpdate();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
    }

    public List<SolicitacaoServico> selectPendentes() throws SQLException {
        return buscar(SELECT_BASE + "WHERE sol.status = 'PENDENTE' ORDER BY sol.data_solicitacao");
    }

    public List<SolicitacaoServico> selectAll() throws SQLException {
        return buscar(SELECT_BASE + "ORDER BY sol.data_solicitacao DESC");
    }

    private List<SolicitacaoServico> buscar(String sql) throws SQLException {
        List<SolicitacaoServico> solicitacoes = new ArrayList<>();
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                solicitacoes.add(montarSolicitacao(resultado));
            }
            resultado.close();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
        return solicitacoes;
    }

    private SolicitacaoServico montarSolicitacao(ResultSet resultado) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultado.getInt("id_cliente"));
        cliente.setNome(resultado.getString("nome_cliente"));

        Prestador prestador = new Prestador();
        prestador.setNome(resultado.getString("nome_prestador"));

        Servico servico = new Servico();
        servico.setId(resultado.getInt("id_servico"));
        servico.setTitulo(resultado.getString("titulo_servico"));
        servico.setPrestador(prestador);

        SolicitacaoServico solicitacao = new SolicitacaoServico();
        solicitacao.setId(resultado.getInt("id"));
        solicitacao.setDataSolicitacao(Datas.doBanco(resultado.getString("data_solicitacao")));
        solicitacao.setDescricaoNecessidade(resultado.getString("descricao_necessidade"));
        solicitacao.setStatus(StatusSolicitacao.valueOf(resultado.getString("status")));
        solicitacao.setValorFinal(resultado.getDouble("valor_final"));
        solicitacao.setCliente(cliente);
        solicitacao.setServico(servico);
        return solicitacao;
    }
}
