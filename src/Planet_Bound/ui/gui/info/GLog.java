package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.ship.ShipDados;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GLog extends ScrollPane {

    final ShipObservavel modelo;
    final VBox log;

    public GLog(ShipObservavel model) {
        modelo = model;
        //setHeight(40);
        //setMaxHeight(100);
        //getHmax()
        //setVvalue();

        log = new VBox();
        log.setSpacing(10);
        log.setPadding(new Insets(5));
        setPannable(true);
        //setMaxHeight(20);
        // log.setFillWidth(true);
        //log.setPannable(true);
        setContent(log);
        ShipDados.debugingEvents = false;
        this.modelo.addPropertyChangeListener(e -> setValues());
    }

    private void setValues() {
        if (ShipDados.getUnreadEvents() != 0)
            for (var i : ShipDados.getAllUnreadEvents()) {
                log.getChildren().add(new AnchorPane(new Label(i.toString())));
            }
       setVvalue(getVmax()+1);
    }
}

