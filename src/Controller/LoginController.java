package Controller;

import Controller.Helper.LoginHelper;
import Model.DAO.UsuarioDAO;
import Model.Prestador;
import Model.StatusUsuario;
import Model.Usuario;
import View.Login;
import View.MenuPrincipal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController {

    private final Login view;
    private final LoginHelper helper;
    private final UsuarioDAO usuarioDAO;

    public LoginController(Login view) {
        this.view = view;
        this.helper = new LoginHelper(view);
        this.usuarioDAO = new UsuarioDAO();
    }

    public void entrarNoSistema() {
        String email = helper.obterEmail();
        String senha = helper.obterSenha();

        if (email.isEmpty() || senha.isEmpty()) {
            view.exibeMensagem("Informe o e-mail e a senha");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.selectPorEmail(email);

            // a chamada e sempre a mesma, mas cada subclasse valida o acesso do seu jeito
            if (usuario == null || !usuario.autenticar(senha)) {
                view.exibeMensagem(mensagemDeRecusa(usuario, senha));
                return;
            }

            Sessao.iniciar(usuario);
            new MenuPrincipal().setVisible(true);
            view.dispose();
        } catch (SQLException erro) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Falha ao consultar o usuario", erro);
            view.exibeMensagem("Nao foi possivel acessar o banco de dados");
        }
    }

    private String mensagemDeRecusa(Usuario usuario, String senha) {
        if (usuario == null || !senha.equals(usuario.getSenha())) {
            return "E-mail ou senha invalidos";
        }
        if (usuario instanceof Prestador && !((Prestador) usuario).isVerificado()) {
            return "Cadastro de prestador ainda em analise pela moderacao";
        }
        if (usuario.getStatus() != StatusUsuario.ATIVO) {
            return "Conta " + usuario.getStatus().name().toLowerCase() + ", procure a administracao";
        }
        return "E-mail ou senha invalidos";
    }

    public void limpar() {
        helper.limparTela();
    }
}
