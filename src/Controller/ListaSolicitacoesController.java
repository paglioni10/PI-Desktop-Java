package Controller;

import Model.DAO.SolicitacaoDAO;
import Model.Datas;
import Model.SolicitacaoServico;
import View.EnvioOrcamento;
import View.ListaSolicitacoes;
import View.MenuPrincipal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaSolicitacoesController {

    private final ListaSolicitacoes view;
    private final SolicitacaoDAO solicitacaoDAO;
    private List<SolicitacaoServico> solicitacoes;

    public ListaSolicitacoesController(ListaSolicitacoes view) {
        this.view = view;
        this.solicitacaoDAO = new SolicitacaoDAO();
        this.solicitacoes = new ArrayList<>();
    }

    public void carregarTabela() {
        try {
            solicitacoes = solicitacaoDAO.selectPendentes();
            view.getModelo().setRowCount(0);
            for (SolicitacaoServico solicitacao : solicitacoes) {
                view.getModelo().addRow(new Object[]{
                    solicitacao.getId(),
                    Datas.formatar(solicitacao.getDataSolicitacao()),
                    solicitacao.getCliente().getNome(),
                    solicitacao.getServico().getTitulo(),
                    solicitacao.getDescricaoNecessidade(),
                    solicitacao.getStatus()
                });
            }
            if (solicitacoes.isEmpty()) {
                view.exibeMensagem("Nenhuma solicitacao pendente no momento");
            }
        } catch (SQLException erro) {
            Logger.getLogger(ListaSolicitacoesController.class.getName()).log(Level.SEVERE, "Falha ao listar solicitacoes", erro);
            view.exibeMensagem("Nao foi possivel carregar as solicitacoes");
        }
    }

    public void abrirEnvioDeOrcamento() {
        int linha = view.getTabela().getSelectedRow();
        if (linha < 0) {
            view.exibeMensagem("Selecione uma solicitacao na tabela");
            return;
        }

        SolicitacaoServico solicitacao = solicitacoes.get(linha);
        new EnvioOrcamento(solicitacao).setVisible(true);
        view.dispose();
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }
}
