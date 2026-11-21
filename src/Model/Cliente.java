package Model;

import java.util.Date;

public class Cliente extends Usuario {

    private String cpf;
    private Date dataNascimento;

    public Cliente() {
        super();
    }

    public Cliente(int id, String nome, String email, String senha, String telefone, Date dataCadastro,
            StatusUsuario status, String cpf, Date dataNascimento) {
        super(id, nome, email, senha, telefone, dataCadastro, status);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    @Override
    public String getResumoPerfil() {
        return "Cliente desde " + Datas.formatar(dataCadastro) + " - CPF " + cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
