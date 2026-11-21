package Model.DAO;

import Model.Categoria;
import Model.Prestador;
import Model.Servico;
import Model.StatusServico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    private static final String SELECT_BASE
            = "SELECT s.id, s.titulo, s.descricao, s.preco_base, s.tempo_estimado_horas, s.status, "
            + "u.id AS id_prestador, u.nome AS nome_prestador, "
            + "cat.id AS id_categoria, cat.nome AS nome_categoria "
            + "FROM servico s "
            + "JOIN usuario u ON u.id = s.id_prestador "
            + "JOIN categoria cat ON cat.id = s.id_categoria ";

    public void insert(Servico servico) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO servico (titulo, descricao, preco_base, tempo_estimado_horas, status, "
                    + "id_prestador, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)");
            comando.setString(1, servico.getTitulo());
            comando.setString(2, servico.getDescricao());
            comando.setDouble(3, servico.getPrecoBase());
            comando.setInt(4, servico.getTempoEstimadoHoras());
            comando.setString(5, servico.getStatus().name());
            comando.setInt(6, servico.getPrestador().getId());
            comando.setInt(7, servico.getCategoria().getId());
            comando.executeUpdate();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
    }

    public List<Servico> selectAtivos() throws SQLException {
        return buscar(SELECT_BASE + "WHERE s.status = 'ATIVO' ORDER BY s.titulo", 0);
    }

    public List<Servico> selectPorPrestador(int idPrestador) throws SQLException {
        return buscar(SELECT_BASE + "WHERE s.id_prestador = ? ORDER BY s.titulo", idPrestador);
    }

    private List<Servico> buscar(String sql, int parametro) throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            if (parametro > 0) {
                comando.setInt(1, parametro);
            }
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                servicos.add(montarServico(resultado));
            }
            resultado.close();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
        return servicos;
    }

    private Servico montarServico(ResultSet resultado) throws SQLException {
        Prestador prestador = new Prestador();
        prestador.setId(resultado.getInt("id_prestador"));
        prestador.setNome(resultado.getString("nome_prestador"));

        Categoria categoria = new Categoria();
        categoria.setId(resultado.getInt("id_categoria"));
        categoria.setNome(resultado.getString("nome_categoria"));

        Servico servico = new Servico();
        servico.setId(resultado.getInt("id"));
        servico.setTitulo(resultado.getString("titulo"));
        servico.setDescricao(resultado.getString("descricao"));
        servico.setPrecoBase(resultado.getDouble("preco_base"));
        servico.setTempoEstimadoHoras(resultado.getInt("tempo_estimado_horas"));
        servico.setStatus(StatusServico.valueOf(resultado.getString("status")));
        servico.setPrestador(prestador);
        servico.setCategoria(categoria);
        return servico;
    }
}
