package Controller;

import Model.Categoria;
import Model.DAO.CategoriaDAO;
import Model.DAO.ServicoDAO;
import Model.Prestador;
import Model.Servico;
import Model.StatusServico;
import View.CadastroServico;
import View.MenuPrincipal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicoController {

    private final CadastroServico view;
    private final ServicoDAO servicoDAO;
    private final CategoriaDAO categoriaDAO;

    public ServicoController(CadastroServico view) {
        this.view = view;
        this.servicoDAO = new ServicoDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    public void carregarCategorias() {
        try {
            List<Categoria> categorias = categoriaDAO.selectAtivas();
            view.getComboCategoria().removeAllItems();
            for (Categoria categoria : categorias) {
                view.getComboCategoria().addItem(categoria);
            }
        } catch (SQLException erro) {
            Logger.getLogger(ServicoController.class.getName()).log(Level.SEVERE, "Falha ao carregar categorias", erro);
            view.exibeMensagem("Nao foi possivel carregar as categorias");
        }
    }

    public void carregarServicos() {
        try {
            List<Servico> servicos = servicoDAO.selectPorPrestador(Sessao.getUsuarioLogado().getId());
            view.getModelo().setRowCount(0);
            for (Servico servico : servicos) {
                view.getModelo().addRow(new Object[]{
                    servico.getId(),
                    servico.getTitulo(),
                    servico.getCategoria().getNome(),
                    String.format("R$ %.2f", servico.getPrecoBase()),
                    servico.getStatus()
                });
            }
        } catch (SQLException erro) {
            Logger.getLogger(ServicoController.class.getName()).log(Level.SEVERE, "Falha ao listar servicos", erro);
            view.exibeMensagem("Nao foi possivel carregar os servicos");
        }
    }

    public void salvar() {
        String erroDeValidacao = validar();
        if (erroDeValidacao != null) {
            view.exibeMensagem(erroDeValidacao);
            return;
        }

        Servico servico = new Servico();
        servico.setTitulo(view.getCampoTitulo().getText().trim());
        servico.setDescricao(view.getCampoDescricao().getText().trim());
        servico.setPrecoBase(Double.parseDouble(view.getCampoPreco().getText().trim().replace(",", ".")));
        servico.setTempoEstimadoHoras(Integer.parseInt(view.getCampoTempo().getText().trim()));
        servico.setStatus(StatusServico.ATIVO);
        servico.setCategoria((Categoria) view.getComboCategoria().getSelectedItem());
        servico.setPrestador((Prestador) Sessao.getUsuarioLogado());

        try {
            servicoDAO.insert(servico);
            view.exibeMensagem("Servico cadastrado com sucesso");
            limpar();
            carregarServicos();
        } catch (SQLException erro) {
            Logger.getLogger(ServicoController.class.getName()).log(Level.SEVERE, "Falha ao gravar servico", erro);
            view.exibeMensagem("Nao foi possivel gravar o servico no banco de dados");
        }
    }

    private String validar() {
        if (view.getCampoTitulo().getText().trim().isEmpty()) {
            return "Informe o titulo do servico";
        }
        if (view.getCampoDescricao().getText().trim().isEmpty()) {
            return "Informe a descricao do servico";
        }
        if (view.getComboCategoria().getSelectedItem() == null) {
            return "Selecione uma categoria";
        }

        try {
            double preco = Double.parseDouble(view.getCampoPreco().getText().trim().replace(",", "."));
            if (preco <= 0) {
                return "O preco base deve ser maior que zero";
            }
        } catch (NumberFormatException erro) {
            return "Preco base invalido, use apenas numeros";
        }

        try {
            int tempo = Integer.parseInt(view.getCampoTempo().getText().trim());
            if (tempo <= 0) {
                return "O tempo estimado deve ser maior que zero";
            }
        } catch (NumberFormatException erro) {
            return "Tempo estimado invalido, informe um numero inteiro de horas";
        }

        return null;
    }

    public void limpar() {
        view.getCampoTitulo().setText("");
        view.getCampoDescricao().setText("");
        view.getCampoPreco().setText("");
        view.getCampoTempo().setText("");
        view.getCampoTitulo().requestFocus();
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }
}
