package View;

import Controller.ListaSolicitacoesController;
import Controller.Sessao;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ListaSolicitacoes extends JFrame {

    private final ListaSolicitacoesController controller;

    private JLabel labelTitulo;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoOrcamento;
    private JButton botaoAtualizar;
    private JButton botaoVoltar;

    public ListaSolicitacoes() {
        initComponents();
        this.controller = new ListaSolicitacoesController(this);
        controller.carregarTabela();
        ligarBotoes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Solicitacoes pendentes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 420);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Solicitacoes aguardando orcamento");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(20, 15, 400, 25);
        painel.add(labelTitulo);

        modelo = new DefaultTableModel(
                new String[]{"Id", "Data", "Cliente", "Servico", "Necessidade", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(20, 50, 750, 260);
        painel.add(rolagem);

        botaoOrcamento = new JButton("Enviar orcamento");
        botaoOrcamento.setBounds(20, 330, 180, 30);
        painel.add(botaoOrcamento);

        botaoAtualizar = new JButton("Atualizar lista");
        botaoAtualizar.setBounds(210, 330, 150, 30);
        painel.add(botaoAtualizar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(640, 330, 130, 30);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private void ligarBotoes() {
        // o administrador acompanha a fila, mas quem envia orcamento e o prestador
        botaoOrcamento.setEnabled(Sessao.getUsuarioLogado().getTipo().equals("Prestador"));
        botaoOrcamento.addActionListener(evento -> controller.abrirEnvioDeOrcamento());
        botaoAtualizar.addActionListener(evento -> controller.carregarTabela());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTable getTabela() {
        return tabela;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
