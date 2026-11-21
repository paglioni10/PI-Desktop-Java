package Model.DAO;

import Model.Administrador;
import Model.Cliente;
import Model.Datas;
import Model.NivelAcesso;
import Model.Prestador;
import Model.StatusUsuario;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String SELECT_BASE
            = "SELECT u.id, u.nome, u.email, u.senha, u.telefone, u.data_cadastro, u.status, u.tipo, "
            + "c.cpf, c.data_nascimento, "
            + "p.cpf_cnpj, p.descricao_profissional, p.raio_atendimento_km, p.nota_media, p.verificado, "
            + "a.nivel_acesso, a.ultimo_acesso "
            + "FROM usuario u "
            + "LEFT JOIN cliente c ON c.id_usuario = u.id "
            + "LEFT JOIN prestador p ON p.id_usuario = u.id "
            + "LEFT JOIN administrador a ON a.id_usuario = u.id ";

    public void insert(Usuario usuario) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            conexao.setAutoCommit(false);

            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO usuario (nome, email, senha, telefone, data_cadastro, status, tipo) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getTelefone());
            comando.setString(5, Datas.paraBanco(usuario.getDataCadastro()));
            comando.setString(6, usuario.getStatus().name());
            comando.setString(7, usuario.getTipo().toUpperCase());
            comando.executeUpdate();

            ResultSet chave = comando.getGeneratedKeys();
            if (chave.next()) {
                usuario.setId(chave.getInt(1));
            }
            chave.close();
            comando.close();

            inserirDadosEspecificos(conexao, usuario);

            conexao.commit();
        } catch (SQLException erro) {
            conexao.rollback();
            throw erro;
        } finally {
            Conexao.fechar(conexao);
        }
    }

    private void inserirDadosEspecificos(Connection conexao, Usuario usuario) throws SQLException {
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO cliente (id_usuario, cpf, data_nascimento) VALUES (?, ?, ?)");
            comando.setInt(1, cliente.getId());
            comando.setString(2, cliente.getCpf());
            comando.setString(3, Datas.paraBanco(cliente.getDataNascimento()));
            comando.executeUpdate();
            comando.close();
        } else if (usuario instanceof Prestador) {
            Prestador prestador = (Prestador) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO prestador (id_usuario, cpf_cnpj, descricao_profissional, raio_atendimento_km, "
                    + "nota_media, verificado) VALUES (?, ?, ?, ?, ?, ?)");
            comando.setInt(1, prestador.getId());
            comando.setString(2, prestador.getCpfCnpj());
            comando.setString(3, prestador.getDescricaoProfissional());
            comando.setInt(4, prestador.getRaioAtendimentoKm());
            comando.setDouble(5, prestador.getNotaMedia());
            comando.setInt(6, prestador.isVerificado() ? 1 : 0);
            comando.executeUpdate();
            comando.close();
        } else if (usuario instanceof Administrador) {
            Administrador administrador = (Administrador) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "INSERT INTO administrador (id_usuario, nivel_acesso, ultimo_acesso) VALUES (?, ?, ?)");
            comando.setInt(1, administrador.getId());
            comando.setString(2, administrador.getNivelAcesso().name());
            comando.setString(3, Datas.paraBanco(administrador.getUltimoAcesso()));
            comando.executeUpdate();
            comando.close();
        }
    }

    public void update(Usuario usuario) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            conexao.setAutoCommit(false);

            PreparedStatement comando = conexao.prepareStatement(
                    "UPDATE usuario SET nome = ?, email = ?, senha = ?, telefone = ?, status = ? WHERE id = ?");
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getTelefone());
            comando.setString(5, usuario.getStatus().name());
            comando.setInt(6, usuario.getId());
            comando.executeUpdate();
            comando.close();

            atualizarDadosEspecificos(conexao, usuario);

            conexao.commit();
        } catch (SQLException erro) {
            conexao.rollback();
            throw erro;
        } finally {
            Conexao.fechar(conexao);
        }
    }

    private void atualizarDadosEspecificos(Connection conexao, Usuario usuario) throws SQLException {
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "UPDATE cliente SET cpf = ?, data_nascimento = ? WHERE id_usuario = ?");
            comando.setString(1, cliente.getCpf());
            comando.setString(2, Datas.paraBanco(cliente.getDataNascimento()));
            comando.setInt(3, cliente.getId());
            comando.executeUpdate();
            comando.close();
        } else if (usuario instanceof Prestador) {
            Prestador prestador = (Prestador) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "UPDATE prestador SET cpf_cnpj = ?, descricao_profissional = ?, raio_atendimento_km = ?, "
                    + "verificado = ? WHERE id_usuario = ?");
            comando.setString(1, prestador.getCpfCnpj());
            comando.setString(2, prestador.getDescricaoProfissional());
            comando.setInt(3, prestador.getRaioAtendimentoKm());
            comando.setInt(4, prestador.isVerificado() ? 1 : 0);
            comando.setInt(5, prestador.getId());
            comando.executeUpdate();
            comando.close();
        } else if (usuario instanceof Administrador) {
            Administrador administrador = (Administrador) usuario;
            PreparedStatement comando = conexao.prepareStatement(
                    "UPDATE administrador SET nivel_acesso = ?, ultimo_acesso = ? WHERE id_usuario = ?");
            comando.setString(1, administrador.getNivelAcesso().name());
            comando.setString(2, Datas.paraBanco(administrador.getUltimoAcesso()));
            comando.setInt(3, administrador.getId());
            comando.executeUpdate();
            comando.close();
        }
    }

    public void delete(int id) throws SQLException {
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement("DELETE FROM usuario WHERE id = ?");
            comando.setInt(1, id);
            comando.executeUpdate();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
    }

    public List<Usuario> selectAll() throws SQLException {
        return buscar(SELECT_BASE + "ORDER BY u.nome", null);
    }

    public List<Usuario> selectPorTipo(String tipo) throws SQLException {
        return buscar(SELECT_BASE + "WHERE u.tipo = ? ORDER BY u.nome", tipo.toUpperCase());
    }

    public Usuario selectPorId(int id) throws SQLException {
        List<Usuario> encontrados = buscar(SELECT_BASE + "WHERE u.id = ?", String.valueOf(id));
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    public Usuario selectPorEmail(String email) throws SQLException {
        List<Usuario> encontrados = buscar(SELECT_BASE + "WHERE u.email = ?", email);
        return encontrados.isEmpty() ? null : encontrados.get(0);
    }

    public boolean emailJaCadastrado(String email, int idIgnorado) throws SQLException {
        Usuario encontrado = selectPorEmail(email);
        return encontrado != null && encontrado.getId() != idIgnorado;
    }

    private List<Usuario> buscar(String sql, String parametro) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(sql);
            if (parametro != null) {
                comando.setString(1, parametro);
            }
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                usuarios.add(montarUsuario(resultado));
            }
            resultado.close();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
        return usuarios;
    }

    // a coluna tipo define qual subclasse de Usuario sera criada
    private Usuario montarUsuario(ResultSet resultado) throws SQLException {
        String tipo = resultado.getString("tipo");

        if ("CLIENTE".equals(tipo)) {
            Cliente cliente = new Cliente();
            preencherDadosComuns(cliente, resultado);
            cliente.setCpf(resultado.getString("cpf"));
            cliente.setDataNascimento(Datas.doBanco(resultado.getString("data_nascimento")));
            return cliente;
        }

        if ("PRESTADOR".equals(tipo)) {
            Prestador prestador = new Prestador();
            preencherDadosComuns(prestador, resultado);
            prestador.setCpfCnpj(resultado.getString("cpf_cnpj"));
            prestador.setDescricaoProfissional(resultado.getString("descricao_profissional"));
            prestador.setRaioAtendimentoKm(resultado.getInt("raio_atendimento_km"));
            prestador.setNotaMedia(resultado.getDouble("nota_media"));
            prestador.setVerificado(resultado.getInt("verificado") == 1);
            return prestador;
        }

        Administrador administrador = new Administrador();
        preencherDadosComuns(administrador, resultado);
        String nivel = resultado.getString("nivel_acesso");
        administrador.setNivelAcesso(nivel == null ? NivelAcesso.MODERADOR : NivelAcesso.valueOf(nivel));
        administrador.setUltimoAcesso(Datas.doBanco(resultado.getString("ultimo_acesso")));
        return administrador;
    }

    private void preencherDadosComuns(Usuario usuario, ResultSet resultado) throws SQLException {
        usuario.setId(resultado.getInt("id"));
        usuario.setNome(resultado.getString("nome"));
        usuario.setEmail(resultado.getString("email"));
        usuario.setSenha(resultado.getString("senha"));
        usuario.setTelefone(resultado.getString("telefone"));
        usuario.setDataCadastro(Datas.doBanco(resultado.getString("data_cadastro")));
        usuario.setStatus(StatusUsuario.valueOf(resultado.getString("status")));
    }
}
