package Model;

public class Servico {

    private int id;
    private String titulo;
    private String descricao;
    private double precoBase;
    private int tempoEstimadoHoras;
    private StatusServico status;
    private Prestador prestador;
    private Categoria categoria;

    public Servico() {
        this.status = StatusServico.ATIVO;
    }

    public Servico(int id, String titulo, String descricao, double precoBase, int tempoEstimadoHoras,
            StatusServico status, Prestador prestador, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.tempoEstimadoHoras = tempoEstimadoHoras;
        this.status = status;
        this.prestador = prestador;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public int getTempoEstimadoHoras() {
        return tempoEstimadoHoras;
    }

    public void setTempoEstimadoHoras(int tempoEstimadoHoras) {
        this.tempoEstimadoHoras = tempoEstimadoHoras;
    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
