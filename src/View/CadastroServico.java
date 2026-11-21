package View;

import Controller.ServicoController;
import Model.Categoria;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CadastroServico extends JFrame {

    private final ServicoController controller;

    private JLabel labelTitulo;
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JTextField campoPreco;
    private JTextField campoTempo;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoVoltar;

    public CadastroServico() {
        initComponents();
        this.controller = new ServicoController(this);
        controller.carregarCategorias();
        controller.carregarServicos();
        ligarBotoes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Servicos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 520);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Cadastro de servico");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(30, 15, 300, 25);
        painel.add(labelTitulo);

        painel.add(criarLabel("Titulo:", 55));
        campoTitulo = criarCampo(55);
        painel.add(campoTitulo);

        painel.add(criarLabel("Descricao:", 95));
        campoDescricao = criarCampo(95);
        painel.add(campoDescricao);

        painel.add(criarLabel("Preco base:", 135));
        campoPreco = criarCampo(135);
        painel.add(campoPreco);

        painel.add(criarLabel("Tempo (horas):", 175));
        campoTempo = criarCampo(175);
        painel.add(campoTempo);

        painel.add(criarLabel("Categoria:", 215));
        comboCategoria = new JComboBox<>();
        comboCategoria.setBounds(170, 215, 420, 25);
        painel.add(comboCategoria);

        botaoSalvar = new JButton("Cadastrar");
        botaoSalvar.setBounds(170, 255, 130, 30);
        painel.add(botaoSalvar);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBounds(310, 255, 130, 30);
        painel.add(botaoLimpar);

        JLabel labelLista = new JLabel("Servicos ja publicados por voce");
        labelLista.setBounds(30, 300, 300, 20);
        painel.add(labelLista);

        modelo = new DefaultTableModel(new String[]{"Id", "Titulo", "Categoria", "Preco", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(30, 325, 560, 110);
        painel.add(rolagem);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(460, 450, 130, 30);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private JLabel criarLabel(String texto, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(30, y, 130, 25);
        return label;
    }

    private JTextField criarCampo(int y) {
        JTextField campo = new JTextField();
        campo.setBounds(170, y, 420, 25);
        return campo;
    }

    private void ligarBotoes() {
        botaoSalvar.addActionListener(evento -> controller.salvar());
        botaoLimpar.addActionListener(evento -> controller.limpar());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    public JTextField getCampoTitulo() {
        return campoTitulo;
    }

    public JTextField getCampoDescricao() {
        return campoDescricao;
    }

    public JTextField getCampoPreco() {
        return campoPreco;
    }

    public JTextField getCampoTempo() {
        return campoTempo;
    }

    public JComboBox<Categoria> getComboCategoria() {
        return comboCategoria;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
