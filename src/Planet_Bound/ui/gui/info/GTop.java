package Planet_Bound.ui.gui.info;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.estados.EstadoJogo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GTop extends HBox {
    ShipObservavel modelo;
    Button bt;
    public GTop (ShipObservavel model){
        modelo = model;
        organiza();
        modelo.addPropertyChangeListener(e -> update());
        update();
    }

    private void organiza(){
        setSpacing(20);
        setAlignment(Pos.CENTER);

        getChildren().add(new GResumo(modelo));
        ((GResumo)getChildren().get(0)).setAlignment(Pos.CENTER);
        bt = new Button("Voltar");
        bt.setAlignment(Pos.CENTER_RIGHT);

        bt.setOnAction(e -> setOption());
        getChildren().add(bt);
    }

    private void setOption(){
        if ((modelo.getEstadoJogo() == EstadoJogo.ConvertResources) || (modelo.getEstadoJogo() == EstadoJogo.OnSpaceStation)){
            modelo.setOpcao(0);
        }if(modelo.getEstadoJogo() == EstadoJogo.NoTerreno)
            modelo.returnToShip();

    }

    private void update(){
        bt.setVisible((modelo.getEstadoJogo() == EstadoJogo.ConvertResources)
                || (modelo.getEstadoJogo() == EstadoJogo.NoTerreno)
                || (modelo.getEstadoJogo() == EstadoJogo.OnSpaceStation));

    }
}

