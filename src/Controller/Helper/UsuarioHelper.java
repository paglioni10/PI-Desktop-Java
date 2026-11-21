package Controller.Helper;

import Model.Administrador;
import Model.Cliente;
import Model.Datas;
import Model.NivelAcesso;
import Model.Prestador;
import Model.StatusUsuario;
import Model.Usuario;
import View.CadastroUsuario;
import java.text.ParseException;
import java.util.Date;

public class UsuarioHelper implements IHelper {

    private final CadastroUsuario view;

    public UsuarioHelper(CadastroUsuario view) {
        this.view = view;
    }

    @Override
    public Usuario obterModelo() {
        Usuario usuario = criarPorTipo();

        usuario.setNome(view.getCampoNome().getText().trim());
        usuario.setEmail(view.getCampoEmail().getText().trim());
        usuario.setSenha(view.getCampoSenha().getText());
        usuario.setTelefone(view.getCampoTelefone().getText().trim());
        usuario.setStatus((StatusUsuario) view.getComboStatus().getSelectedItem());

        Usuario original = view.getUsuarioEmEdicao();
        if (original != null) {
            usuario.setId(original.getId());
            usuario.setDataCadastro(original.getDataCadastro());
        } else {
            usuario.setDataCadastro(new Date());
        }

        preencherDadosEspecificos(usuario);
        return usuario;
    }

    private Usuario criarPorTipo() {
        String tipo = view.getTipoSelecionado();
        if (tipo.equals("Prestador")) {
            return new Prestador();
        }
        if (tipo.equals("Administrador")) {
            return new Administrador();
        }
        return new Cliente();
    }

    private void preencherDadosEspecificos(Usuario usuario) {
        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            cliente.setCpf(view.getCampoDocumento().getText().trim());
            cliente.setDataNascimento(converterData(view.getCampoDataNascimento().getText()));
        } else if (usuario instanceof Prestador) {
            Prestador prestador = (Prestador) usuario;
            prestador.setCpfCnpj(view.getCampoDocumento().getText().trim());
            prestador.setDescricaoProfissional(view.getCampoDescricao().getText().trim());
            prestador.setRaioAtendimentoKm(converterInteiro(view.getCampoRaio().getText()));
            prestador.setVerificado(view.getCheckVerificado().isSelected());
            Usuario original = view.getUsuarioEmEdicao();
            if (original instanceof Prestador) {
                prestador.setNotaMedia(((Prestador) original).getNotaMedia());
            }
        } else if (usuario instanceof Administrador) {
            Administrador administrador = (Administrador) usuario;
            administrador.setNivelAcesso((NivelAcesso) view.getComboNivel().getSelectedItem());
            Usuario original = view.getUsuarioEmEdicao();
            if (original instanceof Administrador) {
                administrador.setUltimoAcesso(((Administrador) original).getUltimoAcesso());
            }
        }
    }

    public void setarModelo(Usuario usuario) {
        view.setTipoSelecionado(usuario.getTipo());
        view.getCampoNome().setText(usuario.getNome());
        view.getCampoEmail().setText(usuario.getEmail());
        view.getCampoSenha().setText(usuario.getSenha());
        view.getCampoTelefone().setText(usuario.getTelefone());
        view.getComboStatus().setSelectedItem(usuario.getStatus());

        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            view.getCampoDocumento().setText(cliente.getCpf());
            view.getCampoDataNascimento().setText(Datas.formatar(cliente.getDataNascimento()));
        } else if (usuario instanceof Prestador) {
            Prestador prestador = (Prestador) usuario;
            view.getCampoDocumento().setText(prestador.getCpfCnpj());
            view.getCampoDescricao().setText(prestador.getDescricaoProfissional());
            view.getCampoRaio().setText(String.valueOf(prestador.getRaioAtendimentoKm()));
            view.getCheckVerificado().setSelected(prestador.isVerificado());
        } else if (usuario instanceof Administrador) {
            view.getComboNivel().setSelectedItem(((Administrador) usuario).getNivelAcesso());
        }
    }

    @Override
    public void limparTela() {
        view.getCampoNome().setText("");
        view.getCampoEmail().setText("");
        view.getCampoSenha().setText("");
        view.getCampoTelefone().setText("");
        view.getCampoDocumento().setText("");
        view.getCampoDataNascimento().setText("");
        view.getCampoDescricao().setText("");
        view.getCampoRaio().setText("");
        view.getCheckVerificado().setSelected(false);
        view.getComboStatus().setSelectedItem(StatusUsuario.ATIVO);
        view.getCampoNome().requestFocus();
    }

    private Date converterData(String texto) {
        try {
            return Datas.converter(texto);
        } catch (ParseException erro) {
            return null;
        }
    }

    private int converterInteiro(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException erro) {
            return 0;
        }
    }
}
