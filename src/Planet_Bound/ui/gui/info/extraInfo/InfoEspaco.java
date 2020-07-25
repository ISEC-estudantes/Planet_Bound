package Planet_Bound.ui.gui.info.extraInfo;

import Planet_Bound.logica.ShipObservavel;
import javafx.scene.layout.*;

public class InfoEspaco extends VBox {
    ShipObservavel modelo;
    public InfoEspaco(ShipObservavel model){
        modelo  = model;
        modelo.addPropertyChangeListener(e -> update());
        setUp();
    }
    private void setUp(){

    }

    private void update(){

    }

}
