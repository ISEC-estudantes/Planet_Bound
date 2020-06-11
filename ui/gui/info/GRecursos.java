package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Vector;

public class GRecursos extends VBox {
    private final Vector<Label> recursos;
    private final Label title;
    private final ShipObservavel modelo;

    public GRecursos(ShipObservavel model) {
        modelo = model;
        title = new Label("  Recursos:");
        recursos = new Vector<>();
        for (var i : Resource.values()) {
            recursos.add(new Label(""));
        }
        this.modelo.addPropertyChangeListener(e -> setValues());
        organizaComponentes();
    }

    private void setValues() {
        int f = 0;
        for (var i : Resource.values())
            recursos.get(f++).setText(i.toString() + " : " + modelo.getNdeYRecursos(i) + "/" + modelo.getMaxOfResourceY(i));
    }

    private void organizaComponentes(){
        setValues();
        getChildren().add(title);
        for (var n = 0; n < Resource.values().length; ++n)
            getChildren().add(recursos.get(n));
        setSpacing(2);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);
    }

}
