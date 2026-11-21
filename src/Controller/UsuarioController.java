package Controller;

import Controller.Helper.UsuarioHelper;
import Model.DAO.UsuarioDAO;
import Model.Datas;
import Model.Usuario;
import View.CadastroUsuario;
import View.ListaUsuarios;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioController {

    private final CadastroUsuario view;
    private final UsuarioHelper helper;
    private final UsuarioDAO usuarioDAO;

    public UsuarioController(CadastroUsuario view) {
        this.view = view;
        this.helper = new UsuarioHelper(view);
        this.usuarioDAO = new UsuarioDAO();
    }

    public void carregarUsuario(Usuario usuario) {
        helper.setarModelo(usuario);
    }

    public void salvar() {
        String erroDeValidacao = validar();
        if (erroDeValidacao != null) {
            view.exibeMensagem(erroDeValidacao);
            return;
        }

        Usuario usuario = helper.obterModelo();

        try {
            if (usuarioDAO.emailJaCadastrado(usuario.getEmail(), usuario.getId())) {
                view.exibeMensagem("Ja existe um usuario com esse e-mail");
                return;
            }

            if (view.getUsuarioEmEdicao() == null) {
                usuarioDAO.insert(usuario);
                view.exibeMensagem("Usuario cadastrado com sucesso");
            } else {
                usuarioDAO.update(usuario);
                view.exibeMensagem("Usuario atualizado com sucesso");
            }
            voltar();
        } catch (SQLException erro) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "Falha ao gravar usuario", erro);
            view.exibeMensagem("Nao foi possivel gravar o usuario no banco de dados");
        }
    }

    private String validar() {
        if (view.getCampoNome().getText().trim().isEmpty()) {
            return "Informe o nome";
        }

        String email = view.getCampoEmail().getText().trim();
        if (email.isEmpty()) {
            return "Informe o e-mail";
        }
        if (!email.contains("@") || !email.contains(".")) {
            return "E-mail em formato invalido";
        }

        if (view.getCampoSenha().getText().trim().isEmpty()) {
            return "Informe a senha";
        }

        String tipo = view.getTipoSelecionado();

        if (!tipo.equals("Administrador") && view.getCampoDocumento().getText().trim().isEmpty()) {
            return tipo.equals("Prestador") ? "Informe o CPF ou CNPJ" : "Informe o CPF";
        }

        if (tipo.equals("Cliente")) {
            String nascimento = view.getCampoDataNascimento().getText().trim();
            if (nascimento.isEmpty()) {
                return "Informe a data de nascimento";
            }
            try {
                Datas.converter(nascimento);
            } catch (ParseException erro) {
                return "Data de nascimento invalida, use o formato dd/mm/aaaa";
            }
        }

        if (tipo.equals("Prestador")) {
            if (view.getCampoDescricao().getText().trim().isEmpty()) {
                return "Informe a area de atuacao do prestador";
            }
            try {
                int raio = Integer.parseInt(view.getCampoRaio().getText().trim());
                if (raio <= 0) {
                    return "O raio de atendimento deve ser maior que zero";
                }
            } catch (NumberFormatException erro) {
                return "O raio de atendimento deve ser um numero inteiro";
            }
        }

        return null;
    }

    public void limpar() {
        helper.limparTela();
    }

    public void voltar() {
        new ListaUsuarios().setVisible(true);
        view.dispose();
    }
}
