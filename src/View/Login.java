package View;

import Controller.LoginController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login extends JFrame {

    private final LoginController controller;

    private JLabel labelTitulo;
    private JLabel labelSubtitulo;
    private JLabel labelEmail;
    private JLabel labelSenha;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoLimpar;

    public Login() {
        initComponents();
        this.controller = new LoginController(this);
    }

    private void initComponents() {
        setTitle("Mao na Roda - Acesso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 290);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel("Mao na Roda", SwingConstants.CENTER);
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(22f));
        labelTitulo.setBounds(0, 20, 400, 30);
        painel.add(labelTitulo);

        labelSubtitulo = new JLabel("Marketplace de prestadores de servicos", SwingConstants.CENTER);
        labelSubtitulo.setBounds(0, 50, 400, 20);
        painel.add(labelSubtitulo);

        labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(40, 100, 70, 25);
        painel.add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(115, 100, 240, 25);
        painel.add(campoEmail);

        labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(40, 140, 70, 25);
        painel.add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(115, 140, 240, 25);
        painel.add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(115, 195, 115, 30);
        painel.add(botaoEntrar);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBounds(240, 195, 115, 30);
        painel.add(botaoLimpar);

        getContentPane().add(painel);

        botaoEntrar.addActionListener(evento -> controller.entrarNoSistema());
        botaoLimpar.addActionListener(evento -> controller.limpar());

        getRootPane().setDefaultButton(botaoEntrar);
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JTextField getCampoSenha() {
        return campoSenha;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
