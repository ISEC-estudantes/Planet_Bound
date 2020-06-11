package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GResumo extends VBox {

    private final Label labelNome;
    private final Label labelComandantes;
    private final ShipObservavel modelo;

    public GResumo(ShipObservavel modelo) {
        this.modelo = modelo;
        labelNome = new Label("");
        labelComandantes = new Label("");
        this.modelo.addPropertyChangeListener(e -> setValues());
        organizaComponentes();
    }

    private void setValues() {
        switch (modelo.getShipType()) {
            case military:
                labelNome.setText("Militar");
                break;
            case mining:
                labelNome.setText("Explorador");
                break;
            case placeholder:
                labelNome.setText("Por Escolhar");
                break;
        }
        labelComandantes.setText("Comandantes: " + modelo.getOfficers().toString());
    }

    private void organizaComponentes() {
        setValues();
        getChildren().addAll(labelNome, labelComandantes);
        setSpacing(5);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
    }

}
