package View;

import Controller.UsuarioController;
import Model.NivelAcesso;
import Model.StatusUsuario;
import Model.Usuario;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CadastroUsuario extends JFrame {

    private final UsuarioController controller;
    private final Usuario usuarioEmEdicao;

    private JLabel labelTitulo;
    private JComboBox<String> comboTipo;
    private JTextField campoNome;
    private JTextField campoEmail;
    private JTextField campoSenha;
    private JTextField campoTelefone;
    private JComboBox<StatusUsuario> comboStatus;

    private JLabel labelDocumento;
    private JTextField campoDocumento;
    private JLabel labelDataNascimento;
    private JTextField campoDataNascimento;
    private JLabel labelDescricao;
    private JTextField campoDescricao;
    private JLabel labelRaio;
    private JTextField campoRaio;
    private JCheckBox checkVerificado;
    private JLabel labelNivel;
    private JComboBox<NivelAcesso> comboNivel;

    private JButton botaoSalvar;
    private JButton botaoLimpar;
    private JButton botaoVoltar;

    public CadastroUsuario(Usuario usuarioEmEdicao) {
        this.usuarioEmEdicao = usuarioEmEdicao;
        initComponents();
        this.controller = new UsuarioController(this);
        ligarBotoes();
        ajustarCamposPorTipo();
        if (usuarioEmEdicao != null) {
            controller.carregarUsuario(usuarioEmEdicao);
        }
    }

    private void initComponents() {
        setTitle(usuarioEmEdicao == null ? "Mao na Roda - Novo usuario" : "Mao na Roda - Editar usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 540);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        labelTitulo = new JLabel(usuarioEmEdicao == null ? "Cadastro de usuario" : "Edicao de usuario");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(16f));
        labelTitulo.setBounds(30, 15, 300, 25);
        painel.add(labelTitulo);

        painel.add(criarLabel("Tipo:", 55));
        comboTipo = new JComboBox<>(new String[]{"Cliente", "Prestador", "Administrador"});
        comboTipo.setBounds(170, 55, 300, 25);
        comboTipo.setEnabled(usuarioEmEdicao == null);
        painel.add(comboTipo);

        painel.add(criarLabel("Nome:", 95));
        campoNome = criarCampo(95);
        painel.add(campoNome);

        painel.add(criarLabel("E-mail:", 135));
        campoEmail = criarCampo(135);
        painel.add(campoEmail);

        painel.add(criarLabel("Senha:", 175));
        campoSenha = criarCampo(175);
        painel.add(campoSenha);

        painel.add(criarLabel("Telefone:", 215));
        campoTelefone = criarCampo(215);
        painel.add(campoTelefone);

        painel.add(criarLabel("Status:", 255));
        comboStatus = new JComboBox<>(StatusUsuario.values());
        comboStatus.setBounds(170, 255, 300, 25);
        painel.add(comboStatus);

        labelDocumento = criarLabel("CPF:", 295);
        painel.add(labelDocumento);
        campoDocumento = criarCampo(295);
        painel.add(campoDocumento);

        labelDataNascimento = criarLabel("Nascimento:", 335);
        painel.add(labelDataNascimento);
        campoDataNascimento = criarCampo(335);
        painel.add(campoDataNascimento);

        labelDescricao = criarLabel("Atuacao:", 335);
        painel.add(labelDescricao);
        campoDescricao = criarCampo(335);
        painel.add(campoDescricao);

        labelNivel = criarLabel("Nivel:", 335);
        painel.add(labelNivel);
        comboNivel = new JComboBox<>(NivelAcesso.values());
        comboNivel.setBounds(170, 335, 300, 25);
        painel.add(comboNivel);

        labelRaio = criarLabel("Raio (km):", 375);
        painel.add(labelRaio);
        campoRaio = criarCampo(375);
        painel.add(campoRaio);

        checkVerificado = new JCheckBox("Cadastro verificado pela moderacao");
        checkVerificado.setBounds(168, 410, 300, 25);
        painel.add(checkVerificado);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.setBounds(30, 455, 130, 32);
        painel.add(botaoSalvar);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBounds(175, 455, 130, 32);
        painel.add(botaoLimpar);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(340, 455, 130, 32);
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
        campo.setBounds(170, y, 300, 25);
        return campo;
    }

    private void ligarBotoes() {
        comboTipo.addActionListener(evento -> ajustarCamposPorTipo());
        botaoSalvar.addActionListener(evento -> controller.salvar());
        botaoLimpar.addActionListener(evento -> controller.limpar());
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    // os campos especificos de cada subclasse ocupam a mesma area da tela
    private void ajustarCamposPorTipo() {
        String tipo = getTipoSelecionado();
        boolean cliente = tipo.equals("Cliente");
        boolean prestador = tipo.equals("Prestador");
        boolean administrador = tipo.equals("Administrador");

        labelDocumento.setVisible(!administrador);
        campoDocumento.setVisible(!administrador);
        labelDocumento.setText(prestador ? "CPF ou CNPJ:" : "CPF:");

        labelDataNascimento.setVisible(cliente);
        campoDataNascimento.setVisible(cliente);

        labelDescricao.setVisible(prestador);
        campoDescricao.setVisible(prestador);
        labelRaio.setVisible(prestador);
        campoRaio.setVisible(prestador);
        checkVerificado.setVisible(prestador);

        labelNivel.setVisible(administrador);
        comboNivel.setVisible(administrador);
    }

    public Usuario getUsuarioEmEdicao() {
        return usuarioEmEdicao;
    }

    public String getTipoSelecionado() {
        return String.valueOf(comboTipo.getSelectedItem());
    }

    public void setTipoSelecionado(String tipo) {
        comboTipo.setSelectedItem(tipo);
    }

    public JTextField getCampoNome() {
        return campoNome;
    }

    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JTextField getCampoSenha() {
        return campoSenha;
    }

    public JTextField getCampoTelefone() {
        return campoTelefone;
    }

    public JComboBox<StatusUsuario> getComboStatus() {
        return comboStatus;
    }

    public JTextField getCampoDocumento() {
        return campoDocumento;
    }

    public JTextField getCampoDataNascimento() {
        return campoDataNascimento;
    }

    public JTextField getCampoDescricao() {
        return campoDescricao;
    }

    public JTextField getCampoRaio() {
        return campoRaio;
    }

    public JCheckBox getCheckVerificado() {
        return checkVerificado;
    }

    public JComboBox<NivelAcesso> getComboNivel() {
        return comboNivel;
    }

    public void exibeMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }
}
