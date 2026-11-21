package Model;

import java.util.Date;

public class Orcamento {

    private int id;
    private double valor;
    private int prazoExecucaoDias;
    private Date validade;
    private String observacoes;
    private StatusOrcamento status;
    private SolicitacaoServico solicitacao;

    public Orcamento() {
        this.status = StatusOrcamento.ENVIADO;
    }

    public Orcamento(int id, double valor, int prazoExecucaoDias, Date validade, String observacoes,
            StatusOrcamento status, SolicitacaoServico solicitacao) {
        this.id = id;
        this.valor = valor;
        this.prazoExecucaoDias = prazoExecucaoDias;
        this.validade = validade;
        this.observacoes = observacoes;
        this.status = status;
        this.solicitacao = solicitacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getPrazoExecucaoDias() {
        return prazoExecucaoDias;
    }

    public void setPrazoExecucaoDias(int prazoExecucaoDias) {
        this.prazoExecucaoDias = prazoExecucaoDias;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    public SolicitacaoServico getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoServico solicitacao) {
        this.solicitacao = solicitacao;
    }
}
