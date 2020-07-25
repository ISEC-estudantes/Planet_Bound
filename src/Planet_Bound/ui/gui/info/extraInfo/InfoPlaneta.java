package Planet_Bound.ui.gui.info.extraInfo;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.ui.gui.Helper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoPlaneta extends VBox {
    ShipObservavel modelo;
    Label droneStats;

    public InfoPlaneta(ShipObservavel model) {
        modelo = model;

        setUp();
    }

    private void setUp() {
        setAlignment(Pos.CENTER_LEFT);

        modelo.addPropertyChangeListener(e -> update());

    }


    private void update() {
        if (modelo.getEstadoJogo() == EstadoJogo.NoEspaco) {
            getChildren().add(new Label("Drone:"));
            for (var i : modelo.getResourceTypes().getPlanetTypes(modelo.getPlanetType()))
                getChildren().add(Helper.ResouceLabel(i));
            setVisible(true);
        } else
            setVisible(false);
    }

}
