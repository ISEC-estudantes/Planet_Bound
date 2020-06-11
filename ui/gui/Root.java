package Planet_Bound.ui.gui;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.ui.gui.estados.GEscolha;
import Planet_Bound.ui.gui.estados.GNoEspaco;
import Planet_Bound.ui.gui.estados.GNovoShip;
import Planet_Bound.ui.gui.info.GLog;
import Planet_Bound.ui.gui.info.GRecursos;
import Planet_Bound.ui.gui.info.GResumo;
import javafx.css.converter.PaintConverter;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

class Root extends BorderPane{
    private final ShipObservavel modelo;

    public Root (ShipObservavel modelo){
        this.modelo = modelo;

        this.setStyle("-fx-text-color: green;-fx-text-inner-color: red; -fx-font-size: 16px;");

        organizaComponentes();
    }

    public enum RootBackground{
        Space,
        Store,
        Terreno
    }

    public void setBackground(){

    }

    private void setUpBackground(){
        Image imagem = new Image(String.valueOf(this.getClass().getResource("./imagens/stars.png")));
    }


    private void organizaComponentes(){
        //BackgroundSize backgroundSize = new BackgroundSize( , 260, false, false, false, false);
        //BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(this.getClass().getResource("./imagens/stars.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //setBackground(new Background(myBI));

        //this.setStyle("-fx-text-color: green;-fx-text-inner-color: red; -fx-font-size: 16px;");

        GNovoShip gNovoShip = new GNovoShip(modelo);
        GNoEspaco gNoEspaco = new GNoEspaco(modelo);
        GEscolha gEscolha = new GEscolha(modelo);
        StackPane center = new StackPane(gNoEspaco,gEscolha);
        center.setBorder(new Border(new BorderStroke(Color.DARKGREY,BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        GResumo gResumo = new GResumo(modelo);
        gResumo.setBorder(new Border(new BorderStroke(Color.DARKGREY,BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));
        //gRecursos.setBackground(new Background(new Back).isOpaque(), )));

        GRecursos gRecursos = new GRecursos(modelo);
        gRecursos.setBorder(new Border(new BorderStroke(Color.DARKGREY,BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        GLog gLog = new GLog(modelo);

        setTop(gResumo);
        setRight(gRecursos);
        setCenter(center);
        setBottom(gLog);

    }
}