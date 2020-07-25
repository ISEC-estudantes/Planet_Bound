package Planet_Bound.ui.gui;

import Planet_Bound.logica.dados.resourcesandplanets.PlanetType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Helper {
    static public int RETANGLE_SIZE = 10;

    static public Color Resource2Color(Resource resource) {
        switch (resource) {
            case Blue:
                return Color.BLUE;
            case Red:
                return Color.RED;
            case Green:
                return Color.GREEN;
            case Black:
                return Color.BLACK;
            case Ammo:
                return Color.ORANGE;
            case Fuel:
                return Color.YELLOW;
            case Shield:
                return Color.PURPLE;
            case Artifact:
                return Color.PINK;
            default:
                return Color.WHITE;
        }
    }
    //public Color Alien2Color(Terreno.Alien.class)

    public static HBox ResouceLabel(Resource resource) {
        var hb = new HBox();
        var label = new Label(resource.toString());
        var tempSquare = new Rectangle();
        tempSquare.setWidth(RETANGLE_SIZE);
        tempSquare.setHeight(RETANGLE_SIZE);
        tempSquare.setFill(Type2Color(resource));
        hb.getChildren().add(tempSquare);
        hb.getChildren().add(label);
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        return hb;
    }

    public static Color Type2Color(PlanetType planetType){
        return PlanetType2Color(planetType);
    }

    public static Color Type2Color(Resource resource){
        return Resource2Color(resource);
    }

    public static Color Type2Color(String color){
        return Color.web(color.toLowerCase());
    }

    public static Color PlanetType2Color(PlanetType planetType){
        switch(planetType){
            case Red:
                return Color.RED;
            case Blue:
                return Color.BLUE;
            case Black:
                return Color.BLACK;
            case Green:
                return Color.GREEN;
            default:
                throw new Error("Tipo nao implementado.");
        }
    }
}

