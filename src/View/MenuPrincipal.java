package View;

import Controller.MenuPrincipalController;
import Controller.Sessao;
import Model.Usuario;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPrincipal extends JFrame {

    private final MenuPrincipalController controller;

    private JLabel labelBoasVindas;
    private JLabel labelPerfil;
    private JButton botaoUsuarios;
    private JButton botaoServicos;
    private JButton botaoNovaSolicitacao;
    private JButton botaoSolicitacoes;
    private JButton botaoSair;

    public MenuPrincipal() {
        initComponents();
        this.controller = new MenuPrincipalController(this);
        aplicarPermissoes();
        ligarBotoes();
    }

    private void initComponents() {
        setTitle("Mao na Roda - Menu principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 380);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelBoasVindas = new JLabel("", SwingConstants.CENTER);
        labelBoasVindas.setFont(labelBoasVindas.getFont().deriveFont(18f));
        labelBoasVindas.setBounds(0, 20, 520, 28);
        painel.add(labelBoasVindas);

        labelPerfil = new JLabel("", SwingConstants.CENTER);
        labelPerfil.setBounds(0, 50, 520, 20);
        painel.add(labelPerfil);

        botaoUsuarios = new JButton("Gerenciar usuarios");
        botaoUsuarios.setBounds(130, 100, 260, 35);
        painel.add(botaoUsuarios);

        botaoServicos = new JButton("Cadastrar servico");
        botaoServicos.setBounds(130, 145, 260, 35);
        painel.add(botaoServicos);

        botaoNovaSolicitacao = new JButton("Solicitar orcamento");
        botaoNovaSolicitacao.setBounds(130, 190, 260, 35);
        painel.add(botaoNovaSolicitacao);

        botaoSolicitacoes = new JButton("Solicitacoes pendentes");
        botaoSolicitacoes.setBounds(130, 235, 260, 35);
        painel.add(botaoSolicitacoes);

        botaoSair = new JButton("Sair");
        botaoSair.setBounds(130, 290, 260, 30);
        painel.add(botaoSair);

        getContentPane().add(painel);
    }

    // cada tipo de usuario enxerga apenas o que faz parte do seu papel
    private void aplicarPermissoes() {
        Usuario usuario = Sessao.getUsuarioLogado();
        labelBoasVindas.setText("Bem-vindo, " + usuario.getNome());
        labelPerfil.setText(usuario.getResumoPerfil());

        String tipo = usuario.getTipo();
        botaoUsuarios.setEnabled(tipo.equals("Administrador"));
        botaoServicos.setEnabled(tipo.equals("Prestador"));
        botaoNovaSolicitacao.setEnabled(tipo.equals("Cliente"));
        botaoSolicitacoes.setEnabled(tipo.equals("Prestador") || tipo.equals("Administrador"));
    }

    private void ligarBotoes() {
        botaoUsuarios.addActionListener(evento -> controller.abrirUsuarios());
        botaoServicos.addActionListener(evento -> controller.abrirCadastroServico());
        botaoNovaSolicitacao.addActionListener(evento -> controller.abrirNovaSolicitacao());
        botaoSolicitacoes.addActionListener(evento -> controller.abrirSolicitacoes());
        botaoSair.addActionListener(evento -> controller.sair());
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
