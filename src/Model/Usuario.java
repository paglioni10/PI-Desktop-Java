package Model;

import java.util.Date;

public abstract class Usuario {

    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String telefone;
    protected Date dataCadastro;
    protected StatusUsuario status;

    public Usuario() {
        this.dataCadastro = new Date();
        this.status = StatusUsuario.ATIVO;
    }

    public Usuario(int id, String nome, String email, String senha, String telefone, Date dataCadastro, StatusUsuario status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }

    public boolean autenticar(String senhaInformada) {
        return senha != null
                && senha.equals(senhaInformada)
                && status == StatusUsuario.ATIVO;
    }

    public void atualizarPerfil(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public abstract String getTipo();

    public abstract String getResumoPerfil();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nome + " (" + getTipo() + ")";
    }
}
