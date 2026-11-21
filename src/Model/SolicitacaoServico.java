package Model;

import java.util.Date;

public class SolicitacaoServico {

    private int id;
    private Date dataSolicitacao;
    private String descricaoNecessidade;
    private StatusSolicitacao status;
    private double valorFinal;
    private Cliente cliente;
    private Servico servico;

    public SolicitacaoServico() {
        this.dataSolicitacao = new Date();
        this.status = StatusSolicitacao.PENDENTE;
    }

    public SolicitacaoServico(int id, Date dataSolicitacao, String descricaoNecessidade, StatusSolicitacao status,
            double valorFinal, Cliente cliente, Servico servico) {
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
        this.descricaoNecessidade = descricaoNecessidade;
        this.status = status;
        this.valorFinal = valorFinal;
        this.cliente = cliente;
        this.servico = servico;
    }

    public boolean estaPendente() {
        return status == StatusSolicitacao.PENDENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getDescricaoNecessidade() {
        return descricaoNecessidade;
    }

    public void setDescricaoNecessidade(String descricaoNecessidade) {
        this.descricaoNecessidade = descricaoNecessidade;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return "Solicitacao " + id + " - " + descricaoNecessidade;
    }
}
