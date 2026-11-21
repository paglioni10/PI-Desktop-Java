package View;

import Controller.SolicitacaoController;
import Model.Servico;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NovaSolicitacao extends JFrame {

    private final SolicitacaoController controller;

    private JLabel labelTitulo;
    private JComboBox<Servico> comboServico;
    private JLabel labelPreco;
    private JTextArea areaDescricao;
    private JButton botaoEnviar;
    private JButton botaoVoltar;

    public NovaSolicitacao() {
        initComponents();
        this.controller = new SolicitacaoController(this);
        controller.carregarServicos();
        ligarComponentes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Solicitar orcamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(560, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Nova solicitacao de servico");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(30, 15, 350, 25);
        painel.add(labelTitulo);

        JLabel labelServico = new JLabel("Servico:");
        labelServico.setBounds(30, 60, 120, 25);
        painel.add(labelServico);

        comboServico = new JComboBox<>();
        comboServico.setBounds(150, 60, 360, 25);
        painel.add(comboServico);

        labelPreco = new JLabel(" ");
        labelPreco.setBounds(150, 90, 360, 20);
        painel.add(labelPreco);

        JLabel labelDescricao = new JLabel("O que precisa:");
        labelDescricao.setBounds(30, 125, 120, 25);
        painel.add(labelDescricao);

        areaDescricao = new JTextArea();
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);

        JScrollPane rolagem = new JScrollPane(areaDescricao);
        rolagem.setBounds(150, 125, 360, 140);
        painel.add(rolagem);

        botaoEnviar = new JButton("Enviar solicitacao");
        botaoEnviar.setBounds(150, 290, 180, 32);
        painel.add(botaoEnviar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(380, 290, 130, 32);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private void ligarComponentes() {
        comboServico.addActionListener(evento -> controller.mostrarPrecoDoServico());
        botaoEnviar.addActionListener(evento -> controller.enviar());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    public JComboBox<Servico> getComboServico() {
        return comboServico;
    }

    public JLabel getLabelPreco() {
        return labelPreco;
    }

    public JTextArea getAreaDescricao() {
        return areaDescricao;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
