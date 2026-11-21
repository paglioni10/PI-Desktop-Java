package Controller;

import Model.Cliente;
import Model.DAO.ServicoDAO;
import Model.DAO.SolicitacaoDAO;
import Model.Servico;
import Model.SolicitacaoServico;
import Model.StatusSolicitacao;
import View.MenuPrincipal;
import View.NovaSolicitacao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SolicitacaoController {

    private final NovaSolicitacao view;
    private final ServicoDAO servicoDAO;
    private final SolicitacaoDAO solicitacaoDAO;

    public SolicitacaoController(NovaSolicitacao view) {
        this.view = view;
        this.servicoDAO = new ServicoDAO();
        this.solicitacaoDAO = new SolicitacaoDAO();
    }

    public void carregarServicos() {
        try {
            List<Servico> servicos = servicoDAO.selectAtivos();
            view.getComboServico().removeAllItems();
            for (Servico servico : servicos) {
                view.getComboServico().addItem(servico);
            }
            mostrarPrecoDoServico();
        } catch (SQLException erro) {
            Logger.getLogger(SolicitacaoController.class.getName()).log(Level.SEVERE, "Falha ao carregar servicos", erro);
            view.exibeMensagem("Nao foi possivel carregar os servicos disponiveis");
        }
    }

    public void mostrarPrecoDoServico() {
        Servico servico = (Servico) view.getComboServico().getSelectedItem();
        if (servico == null) {
            view.getLabelPreco().setText(" ");
            return;
        }
        view.getLabelPreco().setText(String.format("%s - a partir de R$ %.2f",
                servico.getPrestador().getNome(), servico.getPrecoBase()));
    }

    public void enviar() {
        Servico servico = (Servico) view.getComboServico().getSelectedItem();
        if (servico == null) {
            view.exibeMensagem("Nenhum servico disponivel para solicitar");
            return;
        }

        String descricao = view.getAreaDescricao().getText().trim();
        if (descricao.length() < 10) {
            view.exibeMensagem("Descreva o problema com pelo menos 10 caracteres");
            return;
        }

        SolicitacaoServico solicitacao = new SolicitacaoServico();
        solicitacao.setDataSolicitacao(new Date());
        solicitacao.setDescricaoNecessidade(descricao);
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacao.setServico(servico);
        solicitacao.setCliente((Cliente) Sessao.getUsuarioLogado());

        try {
            solicitacaoDAO.insert(solicitacao);
            view.exibeMensagem("Solicitacao enviada, aguarde o orcamento do prestador");
            view.getAreaDescricao().setText("");
        } catch (SQLException erro) {
            Logger.getLogger(SolicitacaoController.class.getName()).log(Level.SEVERE, "Falha ao gravar solicitacao", erro);
            view.exibeMensagem("Nao foi possivel enviar a solicitacao");
        }
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }
}
