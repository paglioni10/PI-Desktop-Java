package Controller.Helper;

import View.Login;

public class LoginHelper {

    private final Login view;

    public LoginHelper(Login view) {
        this.view = view;
    }

    public String obterEmail() {
        return view.getCampoEmail().getText().trim();
    }

    public String obterSenha() {
        return view.getCampoSenha().getText();
    }

    public void limparTela() {
        view.getCampoEmail().setText("");
        view.getCampoSenha().setText("");
        view.getCampoEmail().requestFocus();
    }
}
