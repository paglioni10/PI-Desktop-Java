package Controller;

import View.MenuPrincipal;
import View.Sobre;

public class SobreController {

    private final Sobre view;

    public SobreController(Sobre view) {
        this.view = view;
    }

    public void voltar() {
        new MenuPrincipal().setVisible(true);
        view.dispose();
    }
}
