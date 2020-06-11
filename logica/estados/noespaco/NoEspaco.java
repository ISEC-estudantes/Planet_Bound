package Planet_Bound.logica.estados.noespaco;

import Planet_Bound.logica.Ship;
import Planet_Bound.logica.dados.aux.Officer;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.birthanddeath.GameOver;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noterreno.NoTerreno;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.conversion.ConversionP1;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.OnSpaceStation;

import java.util.Random;

/**
 * @brief Estado base, onde começa o jogo e o que tem acesso a maior parte dos estados do jogo.
 *
 */

public class NoEspaco extends EstadoAdapter {


    public NoEspaco(ShipDados shipDados, boolean novoPlanet) {
        super(shipDados);
        getShipDados().deleteTerreno();
        if (novoPlanet) getShipDados().novoPlanet();

    }

    /**
     * @brief Move para o proximo planeta
     *
     * Do que percebi do enunciado, mover no espaço gasta sempre um de fuel, e depois existe o gasto extra de cada operacao especial
     *
     *
     * @return
     */
    @Override
    public IEstado proximoPlaneta() {
        Random random = ShipDados.getRandom();

        ShipDados.addEvent(EventType.Info, "perda obrigatoria de fuel por navegar entre planetas");
        getShipDados().deleteXResourcesOfTypeY(1, Resource.Fuel);

        //calcular a probablidade apanhar um worm hole
        if (random.nextInt(8) < 1) {
            //going throu a wormhole
            ShipDados.addEvent(EventType.WormHole, "A entrar num wormhole.");
            if (!getShipDados().hasOfficer(Officer.Shilds)) {
                if (getShipDados().getNdeYRecursos(Resource.Fuel) < 4) {
                    return new GameOver(getShipDados(), "Sem Fuel. O suficiente para passar o Worm Hole, ficaste perdido no espaço.");
                }
                if (getShipDados().getNdeYRecursos(Resource.Shield) < 4) {
                    if (getShipDados().removeOfficer())
                        return new GameOver(getShipDados(), "Não tens mais membros da tripulação.");
                }
                getShipDados().deleteXResourcesOfTypeY(4, Resource.Fuel);
                getShipDados().deleteXResourcesOfTypeY(4, Resource.Shield);
            } else {
                if (getShipDados().getNdeYRecursos(Resource.Shield) < 2) {
                    if (getShipDados().removeOfficer())
                        return new GameOver(getShipDados(), "Não tens mais membros da tripulação.");
                    if (3 > getShipDados().getNdeYRecursos(Resource.Fuel)) {
                        return new GameOver(getShipDados(), "Sem Fuel. O suficiente para passar o Worm Hole, ficaste perdido no espaço.");
                    }
                }
                getShipDados().deleteXResourcesOfTypeY(3, Resource.Fuel);
                getShipDados().deleteXResourcesOfTypeY(2, Resource.Shield);
            }
            return new NoEspaco(getShipDados(), true);
        } else {
            int idevento = random.nextInt(5);
            return aplicaEvento(idevento);
        }
    }

    @Override
    public IEstado landOnSpaceStation(){
        if(getShipDados().hasSpaceStation())
            return new OnSpaceStation(getShipDados());
        ShipDados.addEvent(EventType.Warning, "Este planeta não tem uma space station.");
        return this;
    }

    /**
     * @brief Para aplicacao dos eventos aleatorios
     *
     * Este é o metodo pedido pelo enunciado universitario para aplicação manual dos eventos, é utilizado automaticamente pelo proximoPlaneta
     *
     * @param idEvento
     * @return
     */
    @Override
    public IEstado aplicaEvento(int idEvento) {
        //apanhar um evento

        ShipDados.addEvent(EventType.Debug, "Evento: "+idEvento);
        //os eventos possiveis sao o  3(0), 4(1), 5
        // (2), 6(3), 7(4) e 9(5)

            /*
            0 – Crew Death = “A crew member is injured due to a system malfunction, move the ship crew die to the
            right one space”
            */
        if (idEvento == 0) {
            ShipDados.addEvent(EventType.RandomEvent, "Morte de um membro da tripulação.");
            if (getShipDados().removeOfficer())
                return new GameOver(getShipDados(), "Não tens mais membros da tripulação.");
        }
            /*
            1 – Salvage Ship = “You ship comes across an abandoned ship and you find a random resource. Place all four of the resource cubes in a bag and draw one. Roll the d6 for that resource and add it to your cargo”
            */
        else if (idEvento == 1) {
            Resource randomresource = getShipDados().randomResource();
            int howMuch = ShipDados.getRandom().nextInt(6000)/1000;
            getShipDados().addXResourcesOfTypeY(howMuch, randomresource);
            ShipDados.addEvent(EventType.RandomEvent, "Ganhaste "+howMuch+" de " + randomresource.name() + "!");
        }
            /*
            2 – Cargo Loss = “A cargo mishap causes you to lose some of your resources. Place the colored cubes of
            the resources you have in the cargo hold and draw one. Roll a d3 [1-3] to see how much of that
            resource you lose”
            */
        else if (idEvento == 2) {
            //ShipDados.addEvent(EventType.RandomEvent, "Vais ter de escolher o que te vale menos para ir....");
            //return new ChooseResourceToGo(getShipDados());
            Resource index = Resource.values()[ShipDados.getRandom().nextInt(Resource.values().length*1000)/1000];

            ShipDados.addEvent(EventType.RandomEvent, "Foi removido " +
                    getShipDados().deleteXResourcesOfTypeY(
                            ShipDados.getRandom().nextInt(3000)/1000 , index
                    )
                    + " do tipo " + index+".");

        }
            /*
            3 – Fuel Loss = “You accidentally use too much fuel in a test run. Remove [1] fuel cell”
            */
        else if (idEvento == 3) {
            if (getShipDados().getNdeYRecursos(Resource.Fuel) <= 1) {
                return new GameOver(getShipDados(), "Ficaste sem Fuel para viajar.");
            }
            getShipDados().deleteXResourcesOfTypeY(1, Resource.Fuel);
            ShipDados.addEvent(EventType.RandomEvent, "Perdeste 1 de Fuel, yaks!");
        }
            /*
            4 – No Event = “nothing happens”
            */
        else if (idEvento == 4) {
            ShipDados.addEvent(EventType.RandomEvent, "Nada aconteceu, menos mal.");

        }
            /*
            5 – Crew Rescue = “You find a ship in destress with a lone crew member. This crew member joins your
            crew and you move your white crew die to the left one space if you have less than six crew”
            */
        else if (idEvento == 5) {
            if (getShipDados().addOfficer()) {
                Ship.addEvent(EventType.RandomEvent, "Ganhamos outro crew member, yay!");
            } else {

                Ship.addEvent(EventType.RandomEvent, "Resgataste um gajo, muito bem mano!");
            }
        }

        return new NoEspaco(getShipDados(), true);
    }

    @Override
    public IEstado landOnThePlanet() {
        if (!getShipDados().hasOfficer(Officer.Landing)) {
            Ship.addEvent(EventType.Warning, "Nao tens um Landing Office para aterrares. :(");
            return this;
        }
        if (getShipDados().getVezesQueSePodeVizitarOPlaneta() == 0) {
            Ship.addEvent(EventType.Warning, "Já limpaste todos os recursos. :(");
            return this;
        }
        if(!getShipDados().hasDrone()){
            Ship.addEvent(EventType.Warning, "Não tens um drone. Vai a uma space station para obteres um.");
            return this;
        }
        if(getShipDados().getNdeYRecursos(Resource.Fuel) <= 1)
            return new GameOver(getShipDados(), "Perdeste o teu ultimo Fuel a aterrar no planeta.");
        getShipDados().deleteXResourcesOfTypeY(1, Resource.Fuel);
        ShipDados.addEvent(EventType.Info, "Aterragem com sucesso.");
        return new NoTerreno(getShipDados());
    }

    @Override
    public IEstado convertResources() {
        if (getShipDados().hasOfficer(Officer.Cargo))
            return new ConversionP1(getShipDados(),false);
        return this;
    }

    @Override
    public EstadoJogo getEstado(){
        return EstadoJogo.NoEspaco;
    }
}