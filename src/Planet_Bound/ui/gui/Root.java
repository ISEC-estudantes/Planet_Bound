package Planet_Bound.ui.gui;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.ui.gui.estados.GEscolha;
import Planet_Bound.ui.gui.estados.GGameOver;
import Planet_Bound.ui.gui.estados.GNoEspaco;
import Planet_Bound.ui.gui.estados.GTerreno;
import Planet_Bound.ui.gui.info.GLog;
import Planet_Bound.ui.gui.info.GRecursos;
import Planet_Bound.ui.gui.info.GTop;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


class Root extends BorderPane {
    private final ShipObservavel modelo;

    GLog gLog;
    GRecursos gRecursos;
    GTop gTop;
    StackPane center;


    public Root(ShipObservavel modelo) {
        this.modelo = modelo;

        this.setStyle("-fx-text-color: black;-fx-text-inner-color: black; -fx-font-size: 16px;");
        organizaComponentes();
    }

    private void organizaComponentes() {
        //BackgroundSize backgroundSize = new BackgroundSize( , 260, false, false, false, false);
        //BackgroundImage myBI = new BackgroundImage(new Image(String.valueOf(this.getClass().getResource("./imagens/stars.png"))), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //setBackground(new Background(myBI));
        //this.setStyle("-fx-text-color: green;-fx-text-inner-color: red; -fx-font-size: 16px;");
        //menuBar.setUseSystemMenuBar(true);
        //menuBar.getMenus().add(file);g

        //centro
        GNoEspaco gNoEspaco = new GNoEspaco(modelo);
        GEscolha gEscolha = new GEscolha(modelo);
        GTerreno gTerreno = new GTerreno(modelo);
        GGameOver gGameOver = new GGameOver(modelo);
        center = new StackPane(gNoEspaco, gEscolha, gTerreno, gGameOver);
        center.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        //topo
        gTop = new GTop(modelo);
        gTop.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        //right
        gRecursos = new GRecursos(modelo);
        gRecursos.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        //baixo
        gLog = new GLog(modelo);
        gRecursos.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(2))));

        //esquerda
        //StackPane gEsquerda = new StackPane(new InfoEspaco(modelo), new InfoPlaneta(modelo));


        setTop(gTop);
        setRight(gRecursos);
        setCenter(center);

        //setRight(extraStuff);
        setBottom(gLog);


    }

    public void updateSize(double maxW , double maxHeight) {
//        System.out.println("this:"+maxHeight);
//        System.out.println("gResumo:"+gResumo.getHeight());
//        System.out.println("center:"+Math.max(gRecursos.getHeight(), center.getHeight()));
//        System.out.println("glog:"+gLog.getHeight());
        gLog.setMaxWidth(maxW);
        gLog.setMaxHeight(maxHeight - gTop.getHeight() - (Math.max(gRecursos.getHeight(), center.getHeight())));
//        System.out.println("glog after:"+gLog.getHeight());
//        System.out.println("");
    }

    public enum RootBackground {
        Space,
        Store,
        Terreno
    }
}