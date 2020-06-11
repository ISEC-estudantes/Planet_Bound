package Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion;

import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noespaco.NoEspaco;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.OnSpaceStation;

import java.util.Vector;

//Conversao parte 1
public class ConversionP1 extends EstadoAdapter {

    private final boolean onSpaceStation;

    public ConversionP1(ShipDados shipDados, boolean onSpaceStation) {
        super(shipDados);

        this.onSpaceStation = onSpaceStation;

        Vector<String> listaopcoes = new Vector<>();
        String titulo = "Que conversão queres fazer?";

        listaopcoes.add("Sair do modo conversao.");//0

        listaopcoes.add("Red -> Black");//1
        listaopcoes.add("Red -> Green");//2
        listaopcoes.add("Red -> Blue");//3

        listaopcoes.add("Green -> Black");//4
        listaopcoes.add("Green -> Blue");//5
        listaopcoes.add("Green -> Red");//6

        listaopcoes.add("Blue -> Black");//7
        listaopcoes.add("Blue -> Green");//8
        listaopcoes.add("Blue -> Red");//9

        listaopcoes.add("Black -> Green");//10
        listaopcoes.add("Black -> Blue");//11
        listaopcoes.add("Black -> Red");//12

        listaopcoes.add("Black, Green e Blue -> Shield");//13
        listaopcoes.add("Black e Blue -> Ammo");//14
        listaopcoes.add("Black, Red e Green -> Fuel");//15

        getShipDados().setOptions(new Options(titulo, listaopcoes));
    }

    //! verifica se existe este recurso, se nao existir ejeta um evento do tipo Warning
    private boolean resourceExiste(Resource resource) {
        if (getShipDados().getNdeYRecursos(resource) == 0) {
            ShipDados.addEvent(EventType.Warning, "Não tens recursos " + resource + ".");
            return false;
        }
        return true;
    }

    //! Addiciona X aos recursos em troca da perda do grupo de recursos Y.
    //! Ele internamente chama o resourceExiste para verificar se os resources existem e cancela automaticamente se receber fase.
    //  Ele tambem verifica se X esta cheio, caso esteja tambem ira cancelar antes de converter alguma coisa e ejetar um evento do tipo warning.
    private IEstado converterXfromY(Resource X, Resource... Y) {

        //verifcar se existe
        for (var i:Y)
            if(!resourceExiste(i)) return this;

        //verificar se o resultado esta cheio
        if(getShipDados().getNdeYRecursos(X) == getShipDados().getMaxOfResourceY(X)){
            ShipDados.addEvent(EventType.Warning, "Tens o recurso " + X + "cheio, não consegues obter mais.");
            return this;
        }

        StringBuilder msg = new StringBuilder("Em troca de ");
        for (var i : Y) {
            getShipDados().deleteXResourcesOfTypeY(1, i);
            msg.append(i).append(", ");
        }
        msg.append(" recebeste ").append(X).append(".");
        ShipDados.addEvent(EventType.ConversorDeItens, msg.toString());
        getShipDados().addXResourcesOfTypeY(1, X);
        return this;
    }



    @Override
    public IEstado setOpcao(int opcao) {

        switch (opcao) {
            case 0:
                if (onSpaceStation)
                    return new OnSpaceStation(getShipDados());
                else return new NoEspaco(getShipDados(), false);
            case 1:
                return converterXfromY(Resource.Black, Resource.Red);
            case 2:
                return converterXfromY(Resource.Green, Resource.Red);
            case 3:
                return converterXfromY(Resource.Blue, Resource.Red);
            case 4:
                return converterXfromY(Resource.Black, Resource.Green);
            case 5:
                return converterXfromY(Resource.Blue, Resource.Green);
            case 6:
                return converterXfromY(Resource.Red, Resource.Green);
            case 7:
                return converterXfromY(Resource.Black, Resource.Blue);
            case 8:
                return converterXfromY(Resource.Green, Resource.Blue);
            case 9:
                return converterXfromY(Resource.Red, Resource.Blue);
            case 10:
                return converterXfromY(Resource.Blue, Resource.Black);
            case 11:
                return converterXfromY(Resource.Green, Resource.Black);
            case 12:
                return converterXfromY(Resource.Red, Resource.Black);
            case 13:
                return converterXfromY(Resource.Shield, Resource.Black, Resource.Green, Resource.Blue);
            case 14:
                return converterXfromY(Resource.Ammo, Resource.Blue, Resource.Black);
            case 15:
                return converterXfromY(Resource.Fuel, Resource.Black,Resource.Red, Resource.Green);
            default:
                return this;
        }
    }

    @Override
    public EstadoJogo getEstado(){
        return EstadoJogo.ConvertResources;
    }
}
