package Planet_Bound.logica.estados.noespaco.menusdospacestation;

import Planet_Bound.logica.dados.aux.Officer;
import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.ship.ShipType;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noespaco.NoEspaco;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion.ConversionP1;

import java.util.Vector;

public class OnSpaceStation extends EstadoAdapter {

    public OnSpaceStation(ShipDados shipDados) {
        super(shipDados);
        Vector<String> listaopcoesString = new Vector<>();
        String titulo = "O que queres fazer na nave?";

        listaopcoesString.add("Voltar para o espaço");//! 0 - sair
        listaopcoesString.add("Converter recursos");//! 1
        listaopcoesString.add("Comprar um novo drone");//! 2
        listaopcoesString.add("Upgrade ao sistema de armas");//! 3
        listaopcoesString.add("Contratar um novo membro da tripulaçao");//! 4
        listaopcoesString.add("Upgrade de um cargo hold");//! 5
        listaopcoesString.add("Recharge shield"); //! 6

        shipDados.setOptions(new Options(titulo, listaopcoesString));
        ShipDados.addEvent(EventType.Info, "Entraste na SpaceStation.");
    }

    @Override
    public IEstado setOpcao(int opcao) {
        switch (opcao) {
            case 0:
                return new NoEspaco(getShipDados(), false);

            case 1:
                return new ConversionP1(getShipDados(), true);
            case 2:
                if (getShipDados().hasDrone()) {
                    ShipDados.addEvent(EventType.Warning, "Tu ja tens um drone.");
                    return this;
                }

                if (!verificaeretiraNrecursosdotipoYparaobterX("drone", 3, getShipDados().getResourceTypes().getNormalResources().toArray(new Resource[0])))
                    return this;

                getShipDados().comprarDrone();
                return this;

            case 3:
                // se nao for military com o maximo de 9 nao pode ser upgrade
                if (!(getShipDados().getShipType() == ShipType.military)) {
                    ShipDados.addEvent(EventType.Warning, "Tu não tens uma ship do tipo military.");
                    return this;
                }
                if (!(getShipDados().getMaxOfResourceY(Resource.Ammo) == 9)) {
                    ShipDados.addEvent(EventType.Warning, "Tu já tens as weapons upgraded.");
                    return this;
                }

                if (!verificaeretiraNrecursosdotipoYparaobterX("weapon system upgrade", 2, getShipDados().getResourceTypes().getNormalResources().toArray(new Resource[0])))
                    return this;

                getShipDados().upgradeWeaponSystem();
                return this;

            case 4:
                if (getShipDados().getOfficers().size() == Officer.values().length) {
                    ShipDados.addEvent(EventType.Warning, "Tu já tens a tripulação completa.");
                    return this;
                }
                if (!verificaeretiraNrecursosdotipoYparaobterX("outro membro da tripulação", 1, getShipDados().getResourceTypes().getNormalResources().toArray(new Resource[0])))
                    return this;


                getShipDados().addOfficer();
                return this;

            case 5:
                 getShipDados().upgradeCargoHold();
                 return this;
            case 6:
                getShipDados().rechargeShields();
                return this;
            default:
                ShipDados.addEvent(EventType.Debug, "Numero outofrange em" + this);
                return this;
        }
    }


    //! verifica se existe N deste recurso, se nao existir ejeta um evento do tipo Warning
    private boolean resourceExiste(Resource resource, int N) {
        if (getShipDados().getNdeYRecursos(resource) < N) {
            ShipDados.addEvent(EventType.Warning, "Não tens recursos " + resource + " suficientes.");
            return false;
        }
        return true;
    }

    /**
     * Verifica se existe N recursos do tipo Y para obeter o recurso X, se existirem ele vai retirar los e vai returnar true para dizer que se pode adicionar o novo recurso
     * Ele internamente chama o resourceExiste para verificar se os resources existem e cancela automaticamente se receber fase.
     * Ele tambem verifica se X esta cheio, caso esteja tambem ira cancelar antes de converter alguma coisa e ejetar um evento do tipo warning.
     */
    private boolean verificaeretiraNrecursosdotipoYparaobterX(String X, int N, Resource... Y) {

        //verifcar se existe
        for (var i : Y)
            if (!resourceExiste(i, N))
                return false;


        StringBuilder msg = new StringBuilder("Em troca de " + N + "de cada ");

        for (var i : Y) {
            getShipDados().deleteXResourcesOfTypeY(1, i);
            msg.append(i).append(", ");
        }

        msg.append(" recebeste ").append(X).append(".");
        ShipDados.addEvent(EventType.ConversorDeItens, msg.toString());
        return true;
    }

    @Override
    public EstadoJogo getEstado(){
        return EstadoJogo.OnSpaceStation;
    }
}
