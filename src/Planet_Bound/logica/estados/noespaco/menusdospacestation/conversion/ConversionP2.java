package Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion;

import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

//Conversao parte 2
public class ConversionP2 extends EstadoAdapter {
    private final boolean onSpaceStation;
    final Vector<Resource> listaresources;
    final Resource localresource;

    public ConversionP2(ShipDados shipDados, boolean onSpaceStation, Resource resource) {
        super(shipDados);

        localresource = resource;

        this.onSpaceStation = onSpaceStation;
        listaresources = new Vector<>();

        Vector<String> listaopcoes = new Vector<>();
        String titulo;

        listaopcoes.add("Voltar");//0
        listaresources.add(null);

        if (getShipDados().getResourceTypes().getNormalResources().contains(resource)) {
            for (var i : getShipDados().getResourceTypes().getNormalResources()) {
                if (i == resource) continue;
                if (getShipDados().getNdeYRecursos(i) == 0) continue;
                listaopcoes.add(i.toString());
                listaresources.add(i);
            }
            if (listaopcoes.size() == 1)
                titulo = "Lamento mas não tens recursos para poderes obter "+resource+".";
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
                    ShipDados.addEvent(EventType.Debug, getShipDados().getNdeYRecursos(Resource.Green) + " "+
                            getShipDados().getNdeYRecursos(Resource.Black)+" "+
                            getShipDados().getNdeYRecursos(Resource.Red));
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
                titulo = "Lamento mas não tens recursos para poderes obter "+resource+".";
            else
                titulo = "Que materiais queres usar para poderes obter " + resource + "?";
        }
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
    private IEstado converterXfromY(Resource X, List<Resource> Y) {

        //verifcar se existe
        for (var i : Y)
            if (!resourceExiste(i)) return this;

        //verificar se o resultado esta cheio
        if (getShipDados().getNdeYRecursos(X) == getShipDados().getMaxOfResourceY(X)) {
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
        if (opcao == 0)
            return new ConversionP1(getShipDados(), onSpaceStation);
        else if ((opcao > listaresources.size()) || (opcao < 0))
            return this;
        else if (getShipDados().getResourceTypes().getNormalResources().contains(localresource))
            return converterXfromY(
                    localresource,
                    Collections.singletonList(listaresources.get(opcao))
            );
        else
            return converterXfromY(
                    localresource,
                    listaresources.subList(1,listaresources.size()-1)
            );
    }

    @Override
    public EstadoJogo getEstado() {
        return EstadoJogo.ConvertResources;
    }

}
