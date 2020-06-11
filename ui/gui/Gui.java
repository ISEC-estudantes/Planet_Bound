package Planet_Bound.ui.gui;

import Planet_Bound.logica.Ship;
import Planet_Bound.logica.ShipObservavel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        Ship ship = new Ship();
        ShipObservavel shipObservavel = new ShipObservavel(ship);

        Group root = new Group();
        Scene scene = new Scene(new Root(shipObservavel), 700, 450);

        window.setScene(scene);
        window.setTitle("Planet_Bound");
        window.show();
    }
}
