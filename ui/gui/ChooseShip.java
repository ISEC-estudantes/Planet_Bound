package Planet_Bound.ui.gui;

import Planet_Bound.logica.ShipObservavel;
import javafx.scene.layout.HBox;

public class ChooseShip extends HBox {
    private final ShipObservavel model;

    public ChooseShip(ShipObservavel model){
        this.model = model;
        this.model.addPropertyChangeListener(
                (env) -> {
                    actualizaVista();
        });
    }

    private void actualizaVista(){

    }
}
