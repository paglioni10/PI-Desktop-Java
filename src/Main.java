
import View.Login;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException erro) {
            System.out.println("Usando a aparencia padrao do Java: " + erro.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
