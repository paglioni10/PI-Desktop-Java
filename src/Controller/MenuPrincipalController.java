package Controller;

import View.CadastroServico;
import View.ListaSolicitacoes;
import View.ListaUsuarios;
import View.Login;
import View.MenuPrincipal;
import View.NovaSolicitacao;

public class MenuPrincipalController {

    private final MenuPrincipal view;

    public MenuPrincipalController(MenuPrincipal view) {
        this.view = view;
    }

    public void abrirUsuarios() {
        new ListaUsuarios().setVisible(true);
        view.dispose();
    }

    public void abrirCadastroServico() {
        new CadastroServico().setVisible(true);
        view.dispose();
    }

    public void abrirNovaSolicitacao() {
        new NovaSolicitacao().setVisible(true);
        view.dispose();
    }

    public void abrirSolicitacoes() {
        new ListaSolicitacoes().setVisible(true);
        view.dispose();
    }

    public void sair() {
        Sessao.encerrar();
        new Login().setVisible(true);
        view.dispose();
    }
}
