package Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion;

import Planet_Bound.logica.dados.aux.Options;
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
    final Vector<Resource> listaresources;

    public ConversionP1(ShipDados shipDados, boolean onSpaceStation) {
        super(shipDados);

        this.onSpaceStation = onSpaceStation;

        Vector<String> listaopcoes = new Vector<>();
        listaresources = new Vector<>();

        String titulo = "Que conversão queres fazer?";

        listaopcoes.add("Sair do modo conversao.");//0
        listaresources.add(null);

        listaopcoes.add("Red");//1
        listaresources.add(Resource.Red);

        listaopcoes.add("Green");//2
        listaresources.add(Resource.Green);

        listaopcoes.add("Blue");//3
        listaresources.add(Resource.Blue);

        listaopcoes.add("Black");//4
        listaresources.add(Resource.Black);

        listaopcoes.add("Shield");//5
        listaresources.add(Resource.Shield);

        listaopcoes.add("Ammo");//6
        listaresources.add(Resource.Ammo);

        listaopcoes.add("Fuel");//7
        listaresources.add(Resource.Fuel);

        getShipDados().setOptions(new Options(titulo, listaopcoes, listaresources));
    }


    @Override
    public IEstado setOpcao(int opcao) {

        if (opcao == 0)
            if (onSpaceStation)
                return new OnSpaceStation(getShipDados());
            else return new NoEspaco(getShipDados(), false);
        else if ((opcao > 7)  || (opcao < 0))
            return this;
        else
            return new ConversionP2(getShipDados(), onSpaceStation, listaresources.get(opcao));
    }

    @Override
    public EstadoJogo getEstado() {
        return EstadoJogo.ConvertResources;
    }
}
