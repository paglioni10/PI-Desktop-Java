package Controller;

import Model.DAO.UsuarioDAO;
import Model.Usuario;
import View.CadastroUsuario;
import View.ListaUsuarios;
import View.MenuPrincipal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaUsuariosController {

    private final ListaUsuarios view;
    private final UsuarioDAO usuarioDAO;

    public ListaUsuariosController(ListaUsuarios view) {
        this.view = view;
        this.usuarioDAO = new UsuarioDAO();
    }

    public void carregarTabela() {
        try {
            List<Usuario> usuarios = usuarioDAO.selectAll();
            view.getModelo().setRowCount(0);
            for (Usuario usuario : usuarios) {
                view.getModelo().addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTipo(),
                    usuario.getTelefone(),
                    usuario.getStatus()
                });
            }
        } catch (SQLException erro) {
            Logger.getLogger(ListaUsuariosController.class.getName()).log(Level.SEVERE, "Falha ao listar usuarios", erro);
            view.exibeMensagem("Nao foi possivel carregar a lista de usuarios");
        }
    }

    public void novoUsuario() {
        CadastroUsuario cadastro = new CadastroUsuario(null);
        cadastro.setVisible(true);
        view.dispose();
    }

    public void editarSelecionado() {
        Integer id = idSelecionado();
        if (id == null) {
            return;
        }
        try {
            Usuario usuario = usuarioDAO.selectPorId(id);
            if (usuario == null) {
                view.exibeMensagem("Usuario nao encontrado, atualize a lista");
                return;
            }
            CadastroUsuario cadastro = new CadastroUsuario(usuario);
            cadastro.setVisible(true);
            view.dispose();
        } catch (SQLException erro) {
            Logger.getLogger(ListaUsuariosController.class.getName()).log(Level.SEVERE, "Falha ao buscar usuario", erro);
            view.exibeMensagem("Nao foi possivel abrir o usuario selecionado");
        }
    }

    public void excluirSelecionado() {
        Integer id = idSelecionado();
        if (id == null) {
            return;
        }

        if (id == Sessao.getUsuarioLogado().getId()) {
            view.exibeMensagem("Voce nao pode excluir o proprio usuario enquanto estiver logado");
            return;
        }

        String nome = String.valueOf(view.getModelo().getValueAt(view.getTabela().getSelectedRow(), 1));
        if (!view.confirmar("Excluir o usuario " + nome + "?")) {
            return;
        }

        try {
            usuarioDAO.delete(id);
            view.exibeMensagem("Usuario excluido");
            carregarTabela();
        } catch (SQLException erro) {
            Logger.getLogger(ListaUsuariosController.class.getName()).log(Level.SEVERE, "Falha ao excluir usuario", erro);
            view.exibeMensagem("Nao foi possivel excluir: o usuario possui registros vinculados");
        }
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }

    private Integer idSelecionado() {
        int linha = view.getTabela().getSelectedRow();
        if (linha < 0) {
            view.exibeMensagem("Selecione um usuario na tabela");
            return null;
        }
        return Integer.valueOf(String.valueOf(view.getModelo().getValueAt(linha, 0)));
    }
}
