package View;

import Controller.SobreController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Sobre extends JFrame {

    private final SobreController controller;

    private JButton botaoVoltar;

    public Sobre() {
        initComponents();
        this.controller = new SobreController(this);
        botaoVoltar.addActionListener(evento -> controller.voltar());
    }

    private void initComponents() {
        setTitle("Mao na Roda - Sobre o sistema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        JLabel titulo = new JLabel("Mao na Roda", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(20f));
        titulo.setBounds(0, 20, 520, 28);
        painel.add(titulo);

        JLabel subtitulo = new JLabel("Marketplace de prestadores de servicos", SwingConstants.CENTER);
        subtitulo.setBounds(0, 50, 520, 20);
        painel.add(subtitulo);

        painel.add(criarLinha("Projeto Integrador - Avaliacao 2", 95));
        painel.add(criarLinha("Pratica de Linguagem de Programacao I - Java POO", 118));
        painel.add(criarLinha("Analise e Desenvolvimento de Sistemas - FMP", 141));
        painel.add(criarLinha("Professor: Rafael N.R.", 164));

        painel.add(criarLinha("Integrantes:", 205));
        painel.add(criarLinha("Henrique Fernandes dos Santos", 228));
        painel.add(criarLinha("Fabio Henrique Thieres Cardoso Santana", 251));
        painel.add(criarLinha("Nicolas Pietro Paglioni", 274));

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(195, 315, 130, 32);
        painel.add(botaoVoltar);

        getContentPane().add(painel);
    }

    private JLabel criarLinha(String texto, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(60, y, 400, 20);
        return label;
    }
}
