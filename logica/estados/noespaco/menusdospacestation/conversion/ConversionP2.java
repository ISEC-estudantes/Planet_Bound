package Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion;

import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;

import java.util.Vector;

//Conversao parte 2
public class ConversionP2 extends EstadoAdapter {
    private final boolean onSpaceStation;

    public ConversionP2(ShipDados shipDados, boolean onSpaceStation, Resource resource) {
        super(shipDados);

        this.onSpaceStation = onSpaceStation;
        Vector<Resource> listaresources = new Vector<>();
        Vector<String> listaopcoes = new Vector<>();
        String titulo;

        listaopcoes.add("Voltar");//0
        listaresources.add(null);

        if (getShipDados().getResourceTypes().getNormalResources().contains(resource)) {
            int listed = 0;
            for (var i : getShipDados().getResourceTypes().getNormalResources()) {
                if (i == resource) continue;
                if (getShipDados().getNdeYRecursos(i) == 0) continue;
                listaopcoes.add(i.toString());
                listaresources.add(i);
            }
            if (listaopcoes.size() == 1)
                titulo = "Lamento mas não tens recursos para poderes converter.";
            else
                titulo = "Que material queres usar para obter " + resource + "?";
        } else {
            switch (resource) {
                case Shield:
                    listaresources.add(Resource.Black);
                    listaresources.add(Resource.Green);
                    listaresources.add(Resource.Blue);

                    listaopcoes.add("Black, Green e Blue");
                    break;
                case Ammo:
                    if ((getShipDados().getNdeYRecursos(Resource.Blue) == 0)
                            || getShipDados().getNdeYRecursos(Resource.Black) == 0)
                        break;
                    listaresources.add(Resource.Black);
                    listaresources.add(Resource.Blue);
                    listaopcoes.add("Black e Blue");
                    break;
                case Fuel:
                    if ((getShipDados().getNdeYRecursos(Resource.Red) == 0)
                            || (getShipDados().getNdeYRecursos(Resource.Black) == 0)
                            || (getShipDados().getNdeYRecursos(Resource.Green) == 0))
                    break;
                listaresources.add(Resource.Black);
                listaresources.add(Resource.Red);
                listaresources.add(Resource.Green);
                listaopcoes.add("Black, Red e Green");
                break;
                default:
                    throw new Error("Este recurso não existe.");
            }
            if (listaopcoes.size() == 1)
                titulo = "Lamento mas não tens recursos para poderes converter.";
            else
                titulo = "Que materiais queres usar para poderes obter " + resource + "?";
        }
        getShipDados().setOptions(new Options(titulo, listaopcoes));
    }

    @Override
    public EstadoJogo getEstado() {
        return EstadoJogo.ConvertResources;
    }

}
