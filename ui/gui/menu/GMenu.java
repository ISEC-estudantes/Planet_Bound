package Planet_Bound.ui.gui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class GMenu extends MenuBar {
    final Menu file;
    final Menu help;
    public GMenu(){
        file = new Menu("File");
        MenuItem novo = new MenuItem("Novo");
        MenuItem open = new MenuItem("Open...");
        MenuItem save = new MenuItem("Salvar");
        MenuItem saveas = new MenuItem("Salvar as...");
        MenuItem close = new MenuItem("Fechar");

        help = new Menu("Ajuda");
        MenuItem ajuda = new MenuItem("Ajuda sobre Planet_Bound");

    }

}
