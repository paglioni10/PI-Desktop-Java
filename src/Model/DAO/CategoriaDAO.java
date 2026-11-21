package Model.DAO;

import Model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> selectAtivas() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        Connection conexao = Conexao.abrir();
        try {
            PreparedStatement comando = conexao.prepareStatement(
                    "SELECT id, nome, descricao, ativa FROM categoria WHERE ativa = 1 ORDER BY nome");
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                categorias.add(new Categoria(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("descricao"),
                        resultado.getInt("ativa") == 1));
            }
            resultado.close();
            comando.close();
        } finally {
            Conexao.fechar(conexao);
        }
        return categorias;
    }
}
