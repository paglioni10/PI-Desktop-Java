package Controller;

import Model.DAO.ServicoDAO;
import Model.DAO.SolicitacaoDAO;
import Model.DAO.UsuarioDAO;
import Model.SolicitacaoServico;
import Model.StatusSolicitacao;
import View.MenuPrincipal;
import View.Resumo;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResumoController {

    private final Resumo view;
    private final UsuarioDAO usuarioDAO;
    private final ServicoDAO servicoDAO;
    private final SolicitacaoDAO solicitacaoDAO;

    public ResumoController(Resumo view) {
        this.view = view;
        this.usuarioDAO = new UsuarioDAO();
        this.servicoDAO = new ServicoDAO();
        this.solicitacaoDAO = new SolicitacaoDAO();
    }

    public void carregarIndicadores() {
        try {
            List<SolicitacaoServico> todas = solicitacaoDAO.selectAll();

            view.getModelo().setRowCount(0);
            view.getModelo().addRow(new Object[]{"Clientes cadastrados", usuarioDAO.selectPorTipo("CLIENTE").size()});
            view.getModelo().addRow(new Object[]{"Prestadores cadastrados", usuarioDAO.selectPorTipo("PRESTADOR").size()});
            view.getModelo().addRow(new Object[]{"Administradores", usuarioDAO.selectPorTipo("ADMINISTRADOR").size()});
            view.getModelo().addRow(new Object[]{"Servicos ativos no catalogo", servicoDAO.selectAtivos().size()});
            view.getModelo().addRow(new Object[]{"Solicitacoes registradas", todas.size()});
            view.getModelo().addRow(new Object[]{"Aguardando orcamento", solicitacaoDAO.selectPendentes().size()});
            view.getModelo().addRow(new Object[]{"Com orcamento enviado", contarPorStatus(todas, StatusSolicitacao.ORCAMENTO_ENVIADO)});
            view.getModelo().addRow(new Object[]{"Concluidas", contarPorStatus(todas, StatusSolicitacao.CONCLUIDO)});
        } catch (SQLException erro) {
            Logger.getLogger(ResumoController.class.getName()).log(Level.SEVERE, "Falha ao montar o resumo", erro);
            view.exibeMensagem("Nao foi possivel carregar os indicadores");
        }
    }

    private int contarPorStatus(List<SolicitacaoServico> solicitacoes, StatusSolicitacao status) {
        int total = 0;
        for (SolicitacaoServico solicitacao : solicitacoes) {
            if (solicitacao.getStatus() == status) {
                total++;
            }
        }
        return total;
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }
}
