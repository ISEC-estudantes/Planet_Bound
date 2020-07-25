package Planet_Bound.logica;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.aux.Officer;
import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.Event;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.PlanetType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.resourcesandplanets.ResourceTypes;
import Planet_Bound.logica.dados.resourcesandplanets.Terreno;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.ship.ShipType;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.birthanddeath.GameOver;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noterreno.NoTerreno;
import Planet_Bound.logica.estados.birthanddeath.NovoShip;


import java.io.Serializable;
import java.util.List;
import java.util.Vector;


/** Interface para uso externo do programa
 *
 * É aqui que a interface deve se ligar, todas as interações com a logica são feitas aqui, todos os dados são obtidos aqui.
 *
 * A interface só deve tocar criar uma instancia deste objecto e utilizar os seus metodos para brincar com o jogo.
 *
 * Existe uma subclasse a \link Referência à classe Planet_Bound.logica.Ship.ITerreno ITerreno \endlink para interagir com o terreno.
 *
 *
 *
 */
public class Ship implements Serializable {
    //! referencia para o estado corrente
    private IEstado estado;
    //! referencia para a classe que manipula os dados
    private final ShipDados shipDados;

    public Ship() {
        //!shipDados = new ShipDados(shipType);

        shipDados = new ShipDados(ShipType.placeholder);
        estado = new NovoShip(shipDados);

//        if (staticEvents == null) {
//            staticEvents = new Vector<>();
//        }
    }

    ////////////////////////////////////////

    //!Event Logger
    //Uma pequena interface com o ShipDados events

    public static void addEvent(EventType eventType, String msg) {
        ShipDados.addEvent(eventType,msg);
    }

    public static List<Event> getEvents() {
        return ShipDados.getEvents();
    }

    public static List<Event> getAllUnreadEvents() {
        return ShipDados.getAllUnreadEvents();
    }

    public static List<Event> getNLastEvents(int nEvents) {
        return ShipDados.getNLastEvents(nEvents);
    }

    ////////////////////////////////////////

    //delegated methods of estado


    //
    private void setEstado(IEstado estado) {
        boolean changed = estado.getClass() != this.estado.getClass();

        //! verificar se existe razao para perder o jogo
        if ((estado.getClass() != GameOver.class) && (getShipType() != ShipType.placeholder ) && (estado.getClass() != NovoShip.class))
            if (!hasOfficer(Officer.Captain))
                this.estado = new GameOver(getShipDados(), "Não tens o teu capitão.");
            else if (getNdeYRecursos(Resource.Fuel) == 0) {
                this.estado = new GameOver(getShipDados(), "Não tens mais fuel.");
            } else if(getNdeYRecursos(Resource.Artifact) >= 5){
                ShipDados.maisUmGanhado();
                this.estado = new GameOver(getShipDados(), "Ganhaste o jogo!Já ganhaste "+ShipDados.getGanhados()+" vez(es) !");
            } else {
                this.estado = estado;
            }
        else {
            this.estado = estado;
        }
        addEvent(EventType.Debug, "No Estado: " + getEstado());
    }

    public void landOnThePlanet() {
        setEstado(estado.landOnThePlanet());
    }

    public void returnToShip() {
        setEstado(estado.returnToShip());
    }

    public void convertResources() {
        setEstado(estado.convertResources());
    }

    public void proximoPlaneta() {
        setEstado(estado.proximoPlaneta());
    }

    public void setOpcao(int opcao) {
        ShipDados.addEvent(EventType.Debug, "Em setOpcao escolheu a opcao " + getOptions().getOptionsString().get(opcao));
        setEstado(estado.setOpcao(opcao));
    }

    public void move(Direcoes direcao) {
        ShipDados.addEvent(EventType.Debug, "Em setOpcao escolheu a opcao " + direcao);
        setEstado(estado.move(direcao));
    }

    public void landOnSpaceStation() {
        setEstado(estado.landOnSpaceStation());
    }

    public void novoJogo() {
        setEstado(estado.novoJogo());
    }


////////////////////////////////////////

//delegated gets from shipDados

    /**
     * @defgroup group_getsfromShipDados Delegated gets do shipDados
     * @{
     */

    public Options getOptions() {
            return shipDados.getOptions();
    }

    public Class<?> getEstadoClass() {
        return estado.getClass();
    }

    public EstadoJogo getEstado(){
        return estado.getEstado();
    }

    public boolean hasOfficer(Officer officer) {
        return shipDados.hasOfficer(officer);
    }

    public Vector<Officer> getOfficers() {
        return shipDados.getOfficers();
    }

    public boolean hasSpaceStation() {
        return shipDados.hasSpaceStation();
    }

    public int getVezesQueSePodeVizitarOPlaneta() {
        return shipDados.getVezesQueSePodeVizitarOPlaneta();
    }

    public static void ClearEvents() {
        ShipDados.ClearEvents();
    }

    public static int getUnreadEvents() {
        return ShipDados.getUnreadEvents();
    }

    public boolean droneHasResource() {
        return shipDados.getTerreno().droneHasResource();
    }

    public boolean hasDrone() {
        return shipDados.hasDrone();
    }

    public PlanetType getPlanetType(){return shipDados.getPlanetType();}

    public int getCargoHold() {
        return shipDados.getCargoHold();
    }

    public int getMaxOfResourceY(Resource Y) {
        return shipDados.getMaxOfResourceY(Y);
    }

    public int getNdeYRecursos(Resource Y) {
        return shipDados.getNdeYRecursos(Y);
    }

    public ShipType getShipType() {
        return shipDados.getShipType();
    }

    public ResourceTypes getResourceTypes() {
        return shipDados.getResourceTypes();
    }
    /**
     * @}
     */

    ////////////////////////////////////////

    //sobre o terreno

    /** ITerreno
     *
     * @return uma instancia de ITerreno se estiver no estado NoTerreno, se não returna null
     * @brief Para mais detanhes
     * @see ITerreno
     */
    public ITerreno getITerreno() {
        if (getEstadoClass() == NoTerreno.class)
            return new ITerreno(getTerreno());
        return null;
    }

    /**
     * Interface para ter informações do Terreno
     * <p>
     * Quando se é entrado no estado NoTerreno, é se criado um objecto Terreno para gerenciar internamente coisas como o alien, drone e recurso, as suas localizações e as suas propriedades, o ITerreno, nao interage com nada propriamente, o que ele faz é dar informação do estado actual do terreno.
     * <p>
     * Para obter uma instancia de ITerreno veja o metodo getITerreno
     * <p>
     * Aviso: se nao tiver no estado NoTerreno o getITerreno vai returnar null
     */
    public static class ITerreno {
        final Terreno terreno;

        private ITerreno(Terreno terreno) {
            this.terreno = terreno;
        }

        private Terreno getTerreno() {
            return terreno;
        }

        /**
         * vai buscar as coordenadas de uma entidado no terreno
         *
         * @param type tipo do objecto
         * @return returna um array[X,Y] das coordenadas do objecto, se nao existir returna null
         */
        public Integer[] getCoordenadas(types type){
            switch (type){
                case Drone:
                    return getCoordenadas(Terreno.Drone.class);
                case Alien:
                    return getCoordenadas(Terreno.Alien.class);
                case Resource:
                    return getCoordenadas(Terreno.ResourceInPlanet.class);
                case InicialSpot:
                    return getCoordenadas(this.getClass());
                default:
                    throw new Error("Esse tipo nao foi implementado.");
            }
        }
        public enum types{
            Drone,
            Alien,
            Resource,
            InicialSpot
        }


        /**
         * vai buscar as coordenadas de uma entidado no terreno
         *
         * @param classtype da o typo da class que se quer analizar, se for null ou outra que nao exista ira returnar as coordenadas iniciais do drone
         * @return returna um array[X,Y] das coordenadas do objecto ou caso null, da
         */
        public Integer[] getCoordenadas(Class<?> classtype) throws Error {
            Integer[] array = new Integer[2];

            if (getTerreno() == null) {
                throw new Error("nao existe terreno");
            }


            if (classtype == Terreno.Drone.class) {
                if (terreno.getDrone() == null) {
                    return null;
                }
                array[0] = getTerreno().getDrone().getCoordenadasX();
                array[1] = getTerreno().getDrone().getCoordenadasY();
            } else if (classtype == Terreno.Alien.class) {
                if (getTerreno().getAlien() == null) {
                    return null;
                }
                array[0] = getTerreno().getAlien().getCoordenadasX();
                array[1] = getTerreno().getAlien().getCoordenadasY();
            } else if (classtype == Terreno.ResourceInPlanet.class) {
                if (getTerreno().getResource() == null) {
                    return null;
                }
                array[0] = getTerreno().getResource().getCoordenadasX();
                array[1] = getTerreno().getResource().getCoordenadasY();
            }else{
                array[0] = getTerreno().getLocalInicialX();
                array[1] = getTerreno().getLocalInicialY();
            }

            return array;
        }

        /**
         * @return returna o numero do alien, se for zero que dizer que existe um alien no terreno
         * @brief obtem o contador do alien
         */
        public int getCounter() {
            return terreno.getCounter();
        }

        public Resource getResourceType() {
            return terreno.getResource().getResourceType();
        }

        public boolean droneHasResource() {
            return terreno.droneHasResource();
        }

        public int getDroneHealth() {
            return terreno.getDrone().getHealth();
        }

        /**
         * @return O typo de alien
         * Se nao fizeres a verificacao do counter e o alien estiver morto ele ira returnar a string "Morto"
         * @brief Recurta o tipo de alien
         */
        public String getAlienType() {
            if (terreno.getAlien() == null) {
                return "Morto";
            }
            return terreno.getAlien().getType();
        }
    }

    ////////////////////////////////////////

    //direct acess; these should be always private except when debugging

    private void aplicaEvento(int idEvento) {
        setEstado(estado.aplicaEvento(idEvento));
    }

    private IEstado getIEstado() {
        return estado;
    }

    private ShipDados getShipDados() {
        return shipDados;
    }

    private PlanetType getPlanet() {
        return shipDados.getPlanetType();
    }

    private Terreno getTerreno() {
        return shipDados.getTerreno();
    }


    // debugging




}
