package Planet_Bound.ui.gui;

import Planet_Bound.logica.Ship;
import Planet_Bound.logica.ShipObservavel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Gui extends Application {

    ShipObservavel modelo;
    Root root;

    public static void main2(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage s) {
        //PlanetBound(s);
        modelo = new ShipObservavel(new Ship());
        // set title for the stage
        s.setTitle("Planet_Bound");
        root = new Root(modelo);

        // create a VBox
        var mb = getMB(s);
        VBox vb = new VBox(mb, root);

        // create a scene
        Scene sc = new Scene(vb, 720, 540);

        // set the scene
        s.setScene(sc);
        s.show();
        sc.heightProperty().addListener(e -> root.updateSize(sc.getWidth(), sc.getHeight() - mb.getHeight()));
        root.updateSize(sc.getWidth(), sc.getHeight() - mb.getHeight());
    }

    //returna um menu bar
    private MenuBar getMB(Stage s) {
        // create a menu
        Menu m = new Menu("File");

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory((new File(System.getProperty("user.dir"))));

        // create menuitems
        MenuItem m1 = new MenuItem("Save");
        m1.setOnAction(e -> {
            try {
                modelo.save(fc.showSaveDialog(s));
            } catch (IOException ioException) {
                (new Alert(Alert.AlertType.ERROR, "Não foi possivel gravar.")).showAndWait();
            }
        });

        MenuItem m2 = new MenuItem("Load");
        m2.setOnAction(e -> {
            try {
                modelo.load(fc.showOpenDialog(s));
            } catch (Exception f) {
                (new Alert(Alert.AlertType.ERROR, "Não foi possivel carregar.")).showAndWait();
            }
        });
        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);

        // create a menubar
        MenuBar mb = new MenuBar();

        // add menu to menubar
        mb.getMenus().add(m);
        return mb;
    }
}
