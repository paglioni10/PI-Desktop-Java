package Model;

import java.util.Date;

public class Prestador extends Usuario {

    private String cpfCnpj;
    private String descricaoProfissional;
    private int raioAtendimentoKm;
    private double notaMedia;
    private boolean verificado;

    public Prestador() {
        super();
    }

    public Prestador(int id, String nome, String email, String senha, String telefone, Date dataCadastro,
            StatusUsuario status, String cpfCnpj, String descricaoProfissional, int raioAtendimentoKm,
            double notaMedia, boolean verificado) {
        super(id, nome, email, senha, telefone, dataCadastro, status);
        this.cpfCnpj = cpfCnpj;
        this.descricaoProfissional = descricaoProfissional;
        this.raioAtendimentoKm = raioAtendimentoKm;
        this.notaMedia = notaMedia;
        this.verificado = verificado;
    }

    @Override
    public boolean autenticar(String senhaInformada) {
        return super.autenticar(senhaInformada) && verificado;
    }

    @Override
    public String getTipo() {
        return "Prestador";
    }

    @Override
    public String getResumoPerfil() {
        return descricaoProfissional + " - atende ate " + raioAtendimentoKm
                + " km - nota " + notaMedia;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getDescricaoProfissional() {
        return descricaoProfissional;
    }

    public void setDescricaoProfissional(String descricaoProfissional) {
        this.descricaoProfissional = descricaoProfissional;
    }

    public int getRaioAtendimentoKm() {
        return raioAtendimentoKm;
    }

    public void setRaioAtendimentoKm(int raioAtendimentoKm) {
        this.raioAtendimentoKm = raioAtendimentoKm;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}
