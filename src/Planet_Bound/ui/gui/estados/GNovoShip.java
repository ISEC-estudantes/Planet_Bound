package Planet_Bound.ui.gui.estados;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.estados.EstadoJogo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GNovoShip extends VBox {
    private final ShipObservavel modelo;

    public GNovoShip(ShipObservavel modelo) {
        this.modelo = modelo;
        this.modelo.addPropertyChangeListener(e -> atualizaVista());

        organizaComponentes();
        atualizaVista();
    }

    private void organizaComponentes() {
        Button military = new Button("Nave Militar");



        Button mining = new Button("Nave Exploradora");
        mining.setOnAction(e -> {
                    //modelo.setShipType(ShipType.mining);
                    System.out.println("mining");
        });

        HBox escolhas = new HBox();
        escolhas.getChildren().addAll(mining, military);
        escolhas.setAlignment(Pos.CENTER);
        escolhas.setSpacing(10);
        Label questao = new Label("Que tipo de ship(nave) queres?");
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(questao, escolhas);
        atualizaVista();
        military.setOnAction(e -> {
            //modelo.setShipType(ShipType.military);
            System.out.println("military");
        });
    }

    private void atualizaVista() {
        setVisible(modelo.getEstadoJogo() == EstadoJogo.NovoShip);
    }
}
