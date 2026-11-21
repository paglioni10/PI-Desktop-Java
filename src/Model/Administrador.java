package Model;

import java.util.Date;

public class Administrador extends Usuario {

    private NivelAcesso nivelAcesso;
    private Date ultimoAcesso;

    public Administrador() {
        super();
        this.nivelAcesso = NivelAcesso.MODERADOR;
    }

    public Administrador(int id, String nome, String email, String senha, String telefone, Date dataCadastro,
            StatusUsuario status, NivelAcesso nivelAcesso, Date ultimoAcesso) {
        super(id, nome, email, senha, telefone, dataCadastro, status);
        this.nivelAcesso = nivelAcesso;
        this.ultimoAcesso = ultimoAcesso;
    }

    @Override
    public boolean autenticar(String senhaInformada) {
        boolean liberado = super.autenticar(senhaInformada);
        if (liberado) {
            this.ultimoAcesso = new Date();
        }
        return liberado;
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }

    @Override
    public String getResumoPerfil() {
        if (ultimoAcesso == null) {
            return "Nivel " + nivelAcesso + " - primeiro acesso";
        }
        return "Nivel " + nivelAcesso + " - ultimo acesso em " + Datas.formatar(ultimoAcesso);
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }
}
