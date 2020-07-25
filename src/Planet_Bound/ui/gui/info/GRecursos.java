package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.ui.gui.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Vector;

public class GRecursos extends VBox {
    private final Vector<Label> recursos;
    private final Label title;
    private final ShipObservavel modelo;
    Label droneStats;
    HBox DroneBox;
    VBox infoEspaco;
    public GRecursos(ShipObservavel model) {
        modelo = model;
        title = new Label("  Recursos:");
        recursos = new Vector<>();
        for (var i : Resource.values()) {
            recursos.add(new Label(""));
        }
        organizaComponentes();
        this.modelo.addPropertyChangeListener(e -> setValues());
        setValues();
    }

    private void setValues() {
        int f = 0;
        for (var i : Resource.values())
            recursos.get(f++).setText(": " + modelo.getNdeYRecursos(i) + "/" + modelo.getMaxOfResourceY(i));

        //para o drone
        if(modelo.getEstadoJogo() == EstadoJogo.NoTerreno){
            droneStats.setText(String.valueOf(modelo.getITerreno().getDroneHealth()));
            DroneBox.setVisible(true);
        }else
            DroneBox.setVisible(false);

        //para a info no espaco
        if(modelo.getEstadoJogo() == EstadoJogo.NoEspaco) {
            infoEspaco.getChildren().clear();
            infoEspaco.getChildren().add(new Label("No planeta pode se obter:"));
            for (var i : modelo.getResourceTypes().getPlanetTypes(modelo.getPlanetType()))
                infoEspaco.getChildren().add(Helper.ResouceLabel(i));
            infoEspaco.setVisible(true);
        }else {
            infoEspaco.getChildren().clear();
            infoEspaco.setVisible(false);
        }
        Label label = new Label();
        label.
    }

    private void organizaComponentes() {
        getChildren().add(title);
        //getChildren()
        HBox hb = null;
        for (var n = 0; n < Resource.values().length; ++n) {
            hb = new HBox(Helper.ResouceLabel(Resource.values()[n]), recursos.get(n));
            hb.setSpacing(10);
            hb.setAlignment(Pos.CENTER_LEFT);
            getChildren().add(hb);
        }
        setSpacing(2);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);

        //para o drone
        Label dronetitle = new Label ("Drone :");
        droneStats = new Label("");
        Label droneHeart = new Label("♥");
        DroneBox = new HBox();
        DroneBox.getChildren().addAll(dronetitle, droneStats, droneHeart);
        DroneBox.setSpacing(5);
        DroneBox.setAlignment(Pos.CENTER_LEFT);
        DroneBox.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));


        //para o info do planeta no espaco
         infoEspaco = new VBox();
        infoEspaco.setAlignment(Pos.CENTER_LEFT);
        infoEspaco.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        getChildren().add(new StackPane(DroneBox, infoEspaco));
    }

}
