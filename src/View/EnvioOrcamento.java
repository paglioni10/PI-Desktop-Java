package View;

import Controller.OrcamentoController;
import Model.SolicitacaoServico;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EnvioOrcamento extends JFrame {

    private final OrcamentoController controller;
    private final SolicitacaoServico solicitacao;

    private JLabel labelTitulo;
    private JLabel labelCliente;
    private JLabel labelNecessidade;
    private JTextField campoValor;
    private JTextField campoPrazo;
    private JTextField campoValidade;
    private JTextArea areaObservacoes;
    private JButton botaoEnviar;
    private JButton botaoVoltar;

    public EnvioOrcamento(SolicitacaoServico solicitacao) {
        this.solicitacao = solicitacao;
        initComponents();
        this.controller = new OrcamentoController(this);
        ligarBotoes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Envio de orcamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(560, 450);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Orcamento da solicitacao " + solicitacao.getId());
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(30, 15, 400, 25);
        painel.add(labelTitulo);

        labelCliente = new JLabel("Cliente: " + solicitacao.getCliente().getNome()
                + "  |  Servico: " + solicitacao.getServico().getTitulo());
        labelCliente.setBounds(30, 45, 500, 20);
        painel.add(labelCliente);

        labelNecessidade = new JLabel("Pedido: " + solicitacao.getDescricaoNecessidade());
        labelNecessidade.setBounds(30, 68, 500, 20);
        painel.add(labelNecessidade);

        JLabel labelValor = new JLabel("Valor (R$):");
        labelValor.setBounds(30, 110, 120, 25);
        painel.add(labelValor);

        campoValor = new JTextField();
        campoValor.setBounds(160, 110, 350, 25);
        painel.add(campoValor);

        JLabel labelPrazo = new JLabel("Prazo (dias):");
        labelPrazo.setBounds(30, 150, 120, 25);
        painel.add(labelPrazo);

        campoPrazo = new JTextField();
        campoPrazo.setBounds(160, 150, 350, 25);
        painel.add(campoPrazo);

        JLabel labelValidade = new JLabel("Valido ate:");
        labelValidade.setBounds(30, 190, 120, 25);
        painel.add(labelValidade);

        campoValidade = new JTextField();
        campoValidade.setBounds(160, 190, 350, 25);
        painel.add(campoValidade);

        JLabel labelObservacoes = new JLabel("Observacoes:");
        labelObservacoes.setBounds(30, 230, 120, 25);
        painel.add(labelObservacoes);

        areaObservacoes = new JTextArea();
        areaObservacoes.setLineWrap(true);
        areaObservacoes.setWrapStyleWord(true);

        JScrollPane rolagem = new JScrollPane(areaObservacoes);
        rolagem.setBounds(160, 230, 350, 110);
        painel.add(rolagem);

        botaoEnviar = new JButton("Enviar orcamento");
        botaoEnviar.setBounds(160, 365, 180, 32);
        painel.add(botaoEnviar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(380, 365, 130, 32);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private void ligarBotoes() {
        botaoEnviar.addActionListener(evento -> controller.enviar());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    public SolicitacaoServico getSolicitacao() {
        return solicitacao;
    }

    public JTextField getCampoValor() {
        return campoValor;
    }

    public JTextField getCampoPrazo() {
        return campoPrazo;
    }

    public JTextField getCampoValidade() {
        return campoValidade;
    }

    public JTextArea getAreaObservacoes() {
        return areaObservacoes;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
