package View;

import Controller.ListaUsuariosController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ListaUsuarios extends JFrame {

    private final ListaUsuariosController controller;

    private JLabel labelTitulo;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoNovo;
    private JButton botaoEditar;
    private JButton botaoExcluir;
    private JButton botaoAtualizar;
    private JButton botaoVoltar;

    public ListaUsuarios() {
        initComponents();
        this.controller = new ListaUsuariosController(this);
        controller.carregarTabela();
        ligarBotoes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(760, 440);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Usuarios cadastrados");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(20, 15, 300, 25);
        painel.add(labelTitulo);

        modelo = new DefaultTableModel(new String[]{"Id", "Nome", "E-mail", "Tipo", "Telefone", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(20, 50, 710, 280);
        painel.add(rolagem);

        botaoNovo = new JButton("Novo");
        botaoNovo.setBounds(20, 350, 120, 30);
        painel.add(botaoNovo);

        botaoEditar = new JButton("Editar");
        botaoEditar.setBounds(150, 350, 120, 30);
        painel.add(botaoEditar);

        botaoExcluir = new JButton("Excluir");
        botaoExcluir.setBounds(280, 350, 120, 30);
        painel.add(botaoExcluir);

        botaoAtualizar = new JButton("Atualizar lista");
        botaoAtualizar.setBounds(410, 350, 140, 30);
        painel.add(botaoAtualizar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(610, 350, 120, 30);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private void ligarBotoes() {
        botaoNovo.addActionListener(evento -> controller.novoUsuario());
        botaoEditar.addActionListener(evento -> controller.editarSelecionado());
        botaoExcluir.addActionListener(evento -> controller.excluirSelecionado());
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

    public boolean confirmar(String pergunta) {
        int resposta = JOptionPane.showConfirmDialog(this, pergunta, "Confirmacao", JOptionPane.YES_NO_OPTION);
        return resposta == JOptionPane.YES_OPTION;
    }
}
