package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.ship.ShipDados;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GLog extends ScrollPane {

    final ShipObservavel modelo;
    VBox log;

    public GLog(ShipObservavel model) {
        log = new VBox();
        log.setFillWidth(true);
        modelo = model;
        ShipDados.debugingEvents = false;
        this.modelo.addPropertyChangeListener(e -> setValues());
    }

    private void setValues() {
        for (var i : ShipDados.getAllUnreadEvents()) {
            getChildren().add(new AnchorPane(new Text(i.toString())) );
        }
    }
}

