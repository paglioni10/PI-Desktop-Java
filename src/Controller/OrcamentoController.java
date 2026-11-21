package Controller;

import Model.DAO.OrcamentoDAO;
import Model.DAO.SolicitacaoDAO;
import Model.Datas;
import Model.Orcamento;
import Model.SolicitacaoServico;
import Model.StatusOrcamento;
import Model.StatusSolicitacao;
import View.EnvioOrcamento;
import View.ListaSolicitacoes;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrcamentoController {

    private final EnvioOrcamento view;
    private final OrcamentoDAO orcamentoDAO;
    private final SolicitacaoDAO solicitacaoDAO;

    public OrcamentoController(EnvioOrcamento view) {
        this.view = view;
        this.orcamentoDAO = new OrcamentoDAO();
        this.solicitacaoDAO = new SolicitacaoDAO();
    }

    public void enviar() {
        String erroDeValidacao = validar();
        if (erroDeValidacao != null) {
            view.exibeMensagem(erroDeValidacao);
            return;
        }

        SolicitacaoServico solicitacao = view.getSolicitacao();
        double valor = Double.parseDouble(view.getCampoValor().getText().trim().replace(",", "."));

        Orcamento orcamento = new Orcamento();
        orcamento.setValor(valor);
        orcamento.setPrazoExecucaoDias(Integer.parseInt(view.getCampoPrazo().getText().trim()));
        orcamento.setValidade(converterValidade());
        orcamento.setObservacoes(view.getAreaObservacoes().getText().trim());
        orcamento.setStatus(StatusOrcamento.ENVIADO);
        orcamento.setSolicitacao(solicitacao);

        try {
            orcamentoDAO.insert(orcamento);
            solicitacaoDAO.atualizarStatus(solicitacao.getId(), StatusSolicitacao.ORCAMENTO_ENVIADO, valor);
            view.exibeMensagem("Orcamento enviado, a solicitacao mudou para ORCAMENTO_ENVIADO");
            voltar();
        } catch (SQLException erro) {
            Logger.getLogger(OrcamentoController.class.getName()).log(Level.SEVERE, "Falha ao gravar orcamento", erro);
            view.exibeMensagem("Nao foi possivel enviar o orcamento");
        }
    }

    private String validar() {
        try {
            double valor = Double.parseDouble(view.getCampoValor().getText().trim().replace(",", "."));
            if (valor <= 0) {
                return "O valor do orcamento deve ser maior que zero";
            }
        } catch (NumberFormatException erro) {
            return "Valor invalido, use apenas numeros";
        }

        try {
            int prazo = Integer.parseInt(view.getCampoPrazo().getText().trim());
            if (prazo <= 0) {
                return "O prazo deve ser de pelo menos um dia";
            }
        } catch (NumberFormatException erro) {
            return "Prazo invalido, informe a quantidade de dias";
        }

        String validade = view.getCampoValidade().getText().trim();
        if (validade.isEmpty()) {
            return "Informe ate quando o orcamento e valido";
        }

        Date dataValidade = converterValidade();
        if (dataValidade == null) {
            return "Data de validade invalida, use o formato dd/mm/aaaa";
        }

        return null;
    }

    private Date converterValidade() {
        try {
            return Datas.converter(view.getCampoValidade().getText());
        } catch (ParseException erro) {
            return null;
        }
    }

    public void voltar() {
        new ListaSolicitacoes().setVisible(true);
        view.dispose();
    }
}
