package View;

import Controller.ResumoController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Resumo extends JFrame {

    private final ResumoController controller;

    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoAtualizar;
    private JButton botaoVoltar;

    public Resumo() {
        initComponents();
        this.controller = new ResumoController(this);
        controller.carregarIndicadores();
        botaoAtualizar.addActionListener(evento -> controller.carregarIndicadores());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    private void initComponents() {
        setTitle("Mao na Roda - Resumo do sistema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 420);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("Indicadores da plataforma");
        titulo.setFont(titulo.getFont().deriveFont(16f));
        titulo.setBounds(30, 15, 350, 25);
        painel.add(titulo);

        modelo = new DefaultTableModel(new String[]{"Indicador", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(1).setMaxWidth(90);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(30, 55, 450, 255);
        painel.add(rolagem);

        botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBounds(30, 330, 140, 32);
        painel.add(botaoAtualizar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(350, 330, 130, 32);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
