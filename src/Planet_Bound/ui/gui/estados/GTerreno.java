package Planet_Bound.ui.gui.estados;

import Planet_Bound.logica.Ship;
import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.ui.gui.Helper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.Objects;

public class GTerreno extends GridPane {
    final ShipObservavel modelo;
    Circle planeta;
    //Button planeta;
    Button next;
    StackPane spaceStation;
    Button conversor;

    Polygon drone;
    Ellipse alien;
    Rectangle resource;

    public GTerreno(ShipObservavel model) {
        modelo = model;
        organiza();
        modelo.addPropertyChangeListener(e -> atualizaVista());
        atualizaVista();
    }

    private void organiza() {
        drone = new Polygon(25.0, 0.0,
                0.0, 50.0,
                50.0, 50.0);
        alien = new Ellipse(15.0, 25.0);
        resource = new Rectangle(30.0, 30.0);
        setPadding(new Insets(5.0));
        setHeight(getMaxHeight());
        setWidth(getMaxWidth());
        setVgap(5);
        setHgap(5);
        setGridLinesVisible(true);
        setAlignment(Pos.CENTER);

        for (int i = 0; i < 6; ++i)
            for (int j = 0; j < 6; ++j) {
                add(new Rectangle(50, 50, Color.WHITE), i, j);
            }
    }

    //quadrado que ao clicar vai para a direcao certa
    private Rectangle direcao(int x, int y) {
        int xdrone = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Drone)[0];
        int ydrone = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Drone)[1];
        var alien = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Drone);
        int xalien = alien[0];
        int yalien = alien[1];
        //----
        var rect = new Rectangle(50, 50, Color.WHITE);

        //esta em cima
        if (ydrone - 1 == y && xdrone == x) {
            rect.setFill(Color.ORANGE);
            rect.setOnMouseClicked(e -> modelo.move(Direcoes.baixo));//faz andar para cima
        }
        //esta em baixo
        if (ydrone + 1 == y && xdrone == x)
            if (!(x == xalien && y == yalien)) {
                rect.setFill(Color.ORANGE);
                rect.setOnMouseClicked(e -> modelo.move(Direcoes.cima));//faz andar para baixo
            }
        //esta na esquerda
        if (ydrone == y && xdrone - 1 == x)
            if (!(x == xalien && y == yalien)) {
                rect.setFill(Color.ORANGE);
                rect.setOnMouseClicked(e -> modelo.move(Direcoes.esquerda));//faz andar para a esquerda
            }
        //esta na direita
        if (ydrone == y && xdrone + 1 == x)
            if (!(x == xalien && y == yalien)) {
                rect.setFill(Color.ORANGE);
                rect.setOnMouseClicked(e -> modelo.move(Direcoes.direita));//faz andar para a direita
            }
        return rect;
    }

    private boolean compareVector(Integer[] a, Integer[] b) {

        return compareVector(a, b, 0);
    }

    private boolean compareVector(Integer[] a, Integer[] b, int n) {
        if (a.length != b.length) return false;
        if (a.length == n) return true;
        if (!a[n].equals(b[n])) return false;
        return compareVector(a, b, n + 1);
    }


    private void atualizaVista() {
        if (modelo.getEstadoJogo() == EstadoJogo.NoTerreno) {
            getChildren().clear();
            Integer[] aliencoord = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Alien);
            Integer[] dronecoord = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Drone);
            Integer[] resourcecoord = null;
            Integer[] dronecoordinit = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.InicialSpot);
            if (!modelo.getITerreno().droneHasResource())
                resourcecoord = modelo.getITerreno().getCoordenadas(Ship.ITerreno.types.Resource);
            StackPane base = null;
            for (int i = 0; i < 6; ++i)
                for (int j = 0; j < 6; ++j) {
                    base = new StackPane(direcao(i, j));
                    base.setAlignment(Pos.CENTER);
                    if (dronecoord[0] == i && dronecoord[1] == j) {
                        if (((Rectangle) base.getChildren().get(0)).getFill() == Color.ORANGE)
                            drone.setOnMouseClicked(((Rectangle) base.getChildren().get(0)).getOnMouseClicked());
                        base.getChildren().add(drone);
                        drone.setFill(Color.GREY);
                    } else if (resourcecoord != null && resourcecoord[0] == i && resourcecoord[1] == j) {
                        if (((Rectangle) base.getChildren().get(0)).getFill() == Color.ORANGE)
                            resource.setOnMouseClicked(((Rectangle) base.getChildren().get(0)).getOnMouseClicked());
                        base.getChildren().add(resource);
                        resource.setFill(Helper.Type2Color(modelo.getITerreno().getResourceType()));
                    } else if (aliencoord != null && aliencoord[0] == i && aliencoord[1] == j) {
                        if (((Rectangle) base.getChildren().get(0)).getFill() == Color.ORANGE)
                            alien.setOnMouseClicked(((Rectangle) base.getChildren().get(0)).getOnMouseClicked());
                        base.getChildren().add(alien);
                        alien.setFill(Helper.Type2Color(modelo.getITerreno().getAlienType()));
                    }
                    if (modelo.getITerreno().droneHasResource())
                        if (dronecoordinit[0] == i && dronecoordinit[1] == j)
                            if (!(Objects.equals(dronecoordinit[0], dronecoord[0]) && Objects.equals(dronecoordinit[1], dronecoord[1]))) {

                                var line1 = new Line(0.0, 0.0, 50.0, 50.0);
                                line1.setFill(Color.GREEN);
                                var line2 =new Line(0.0, 50.0, 50.0, 0.0);
                                line2.setFill(Color.GREEN);
                                base.getChildren().addAll(line1, line2);
                            }

                    add(base, i, j);
                    //add(new Label(i + "," + j), i, j);

                }
            setVisible(true);
        } else
            setVisible(false);
    }
}
