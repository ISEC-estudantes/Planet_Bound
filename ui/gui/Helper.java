package Planet_Bound.ui.gui;

import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import javafx.scene.paint.Color;

public class Helper {
    static public Color Resource2Color(Resource resource){
        switch(resource){
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
}
