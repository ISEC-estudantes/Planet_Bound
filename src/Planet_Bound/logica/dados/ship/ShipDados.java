package Planet_Bound.logica.dados.ship;

import Planet_Bound.logica.dados.aux.Officer;
import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.Event;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.PlanetType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.resourcesandplanets.ResourceTypes;
import Planet_Bound.logica.dados.resourcesandplanets.Terreno;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;


//! Esta class tem a funcao de guardar praticamente todos os dados para o jogo funcionar
public class ShipDados implements Serializable {


    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    //mudar este valor para que os eventos mostrem ou eventos de debuging
    static public boolean debugingEvents = false;
    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    //counter of unread events
    static int unreadEvents = 0, uEnoD = 0;
    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    static private Vector<Event> staticEvents = new Vector<>();
    /**
     * @}
     */
    //////////////////////////////////////////////

    //sobre jogos ganhados

    static private int ganhados = 0;

    /////////////////////////////////////////////////////

    //sobre randomizes

    /** @defgroup randomizes
     * @{
     *     para facil acesso aos random mais bem construidos criei estes statics
     *
     *   static random values para ser usado por todos do Planet_Bound para
     *  que se tenha uma chamada mais pratica para o random
     * @}
     */


    /**
     * @addtogroup randomizes
     * @{
     */
    //ponteiros nao estaticos para debugging
    final Vector<Event> eventosnormais;
    /**
     * @}
     */

    /**
     * @addtogroup randomizes
     * @{
     */
    //resurceTypes
    final ResourceTypes resourceTypes;

    /**
     * @}
     */

    /**@addtogroup randomizes
     * @{
     *
     */
    /**
     * @}
     */

    ////////////////////////////////////////////////////////

    //sobre o managemento de recursos
    private final Vector<Resource> resources;

    /**
     * @}
     */
    /////////////////////////////////////////////////////

    /**
     * @defgroup secops Secção das opções
     * @{
     *  Esta seção é para haver uma interface para aprensentar as opções que podem ser tomadas em menus
     *
     * @}
     *
     */
    /**
     * @addtogroup secops Secção das opções
     * @{
     */
    //! esta variavel contem as opcoes actuais de um estado que tenha setEstado como o estado ChooseResourceToGo
    Options options;

    /**
     * @}
     */
    //sobre os officers
    Vector<Officer> officers;

    /**
     * @}
     */
    //sobre o Terreno
    Terreno terreno;
    private int shield_max;
    private int weapon_max;
    private int fuel_max;
    private int cargo_multiplyer;
    private int cargo_multiplyer_max;
    private ShipType shipType;
    //sobre o planeta
    private boolean spaceStation;
    private PlanetType planetType;
    private int vezesQueSePodeVizitarOPlaneta;
    private boolean artfactoFoiMinado;
    private boolean cargo_upgraded_here;
    //sobre o drone
    private boolean drone;

    //! Constructor
    public ShipDados(ShipType shiptype) {
        eventosnormais = staticEvents;
        resources = new Vector<>();
        resourceTypes = new ResourceTypes();

        reset(shiptype);

        if (staticEvents == null) {
            staticEvents = new Vector<>();
        }

    }

    /**
     * metodo mais chamado deste grupo
     */
    static public Random getRandom() {
        Random random = new Random(System.currentTimeMillis() / 1000L);
        return random;
    }

    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */

    static public void addEvent(EventType eventType, String msg) {
        if (staticEvents == null) {
            return;
        }


        staticEvents.add(new Event(eventType, msg));
        ++unreadEvents;
    }

    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */

    static public List<Event> getEvents() {
        unreadEvents = 0;
        if (!debugingEvents) {
            Vector<Event> tempV = new Vector<>(staticEvents);
            tempV.removeIf(i -> i.getEventType() == EventType.Debug);
            return Collections.unmodifiableList(tempV);
        }
        return Collections.unmodifiableList(staticEvents);
    }

    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    static public List<Event> getAllUnreadEvents() {
        return getNLastEvents(unreadEvents);
    }

    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    static public void ClearEvents() {
        staticEvents.clear();
        unreadEvents = 0;
    }

    ////////////////////////////////////////////////////////

    //sobre o tipo de ship

    /**
     * @}
     */
    public static int getUnreadEvents() {
        return unreadEvents;
    }

    /**
     * @addtogroup group_shipDadosEvents
     * @{
     */
    static public List<Event> getNLastEvents(int nEvents) {
        //if (staticEvents.size() == 0) return null;
        if (nEvents > staticEvents.size()) return getEvents();
        if (nEvents > unreadEvents) unreadEvents = 0;
        else unreadEvents -= nEvents;
        Vector<Event> tempV = new Vector<>();
        for (int i = staticEvents.size() - nEvents; i < staticEvents.size(); ++i)
            tempV.add(staticEvents.elementAt(i));
        if (!debugingEvents)
            tempV.removeIf(i -> i.getEventType() == EventType.Debug);
        return tempV;
    }


    ////////////////////////////////////////////////////////

    static public void maisUmGanhado() {
        ++ganhados;
    }

    public static int getGanhados() {
        return ganhados;
    }

    ////////////////////////////////////////////////////////

    public void reset(ShipType shiptype) {
        clear();
        //!defaults
        drone = true;
        officers = new Vector<>();
        officers.add(Officer.Captain);
        officers.add(Officer.Navigation);
        officers.add(Officer.Landing);
        officers.add(Officer.Shilds);
        officers.add(Officer.Weapons);
        officers.add(Officer.Cargo);
        //!cargo inicial para ambos e 6
        cargo_multiplyer = 1;
        ResourceTypes.fillVector(resources, Resource.Ammo, 9);
        weapon_max = 9;
        //!specifics
        switch (shiptype) {
            case mining:
                cargo_multiplyer_max = 4;
                ResourceTypes.fillVector(resources, Resource.Fuel, 53);
                fuel_max = 53;
                ResourceTypes.fillVector(resources, Resource.Shield, 18);
                shield_max = 18;
                break;
            case military:
            case placeholder:
                cargo_multiplyer_max = 2;
                ResourceTypes.fillVector(resources, Resource.Fuel, 35);
                fuel_max = 35;
                ResourceTypes.fillVector(resources, Resource.Shield, 9);
                shield_max = 9;
                break;
            default:
                throw new Error("This ship type doesn't exist.");
        }
        shipType = shiptype;
    }

    public void clear() {
        if (officers == null || resources == null)
            return;
        officers.clear();
        resources.clear();
    }

    /**
     * @return um recurse do grupo de recurses
     */
    public Resource randomResource() {
        return Resource.values()[getRandom().nextInt(Resource.values().length * 1000) / 1000];
    }

    /**
     * @return um resource que faça parte da colecao resourcestypes
     */

    public Resource randomResource(List<Resource> resourcetypes) {
        return resourcetypes.get(getRandom().nextInt(resourcetypes.size() * 1000) / 1000);
    }

    /**
     * @addtogroup secops Secção das opções
     * @{
     */
    public Options getOptions() {
        return options;
    }

    ////////////////////////////////////////////////////////

    /**
     * @param option que o utilizador tomou
     * @addtogroup secops Secção das opções
     * @{
     */

    public void setOptions(Options option) {
        this.options = option;
    }

    public int getCargoHold() {
        return 6 * cargo_multiplyer;
    }

    public void upgradeWeaponSystem() {
        if (getShipType() == ShipType.military) {
            weapon_max = 18;
        }
    }

    private boolean resourceExiste(Resource resource, int N) {
        if (getNdeYRecursos(resource) < N) {
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
    private boolean verificaeRetiraNrecursosdotipoYparaobterX(String X, int N, Resource... Y) {

        //verifcar se existe
        for (var i : Y)
            if (!resourceExiste(i, N))
                return false;


        StringBuilder msg = new StringBuilder("Em troca de " + N + "de cada ");

        for (var i : Y) {
            deleteXResourcesOfTypeY(1, i);
            msg.append(i).append(", ");
        }

        msg.append(" recebeste ").append(X).append(".");
        ShipDados.addEvent(EventType.ConversorDeItens, msg.toString());
        return true;
    }

    public void rechargeShields() {
        if (shield_max == getNdeYRecursos(Resource.Shield)) {
            addEvent(EventType.Warning, "Ja tens os shields carregados.");
            return;
        }
        if (!verificaeRetiraNrecursosdotipoYparaobterX("energia para os shields", 1, getResourceTypes().getNormalResources().toArray(new Resource[0])))
            return;

        setXResourcesOfTypeY(shield_max, Resource.Shield);
    }

    public void upgradeCargoHold() {

        if (cargo_multiplyer == cargo_multiplyer_max) {
            ShipDados.addEvent(EventType.Warning, "Já tens o cargo hold no maximo.");
            return;
        }

        if (cargo_upgraded_here) {
            ShipDados.addEvent(EventType.Warning, "Já fizeste upgrade do cargo hold aqui.");
        }

        if (!verificaeRetiraNrecursosdotipoYparaobterX("cargo hold", 2, getResourceTypes().getNormalResources().toArray(new Resource[0])))
            return;

        ++cargo_multiplyer;
    }

    public int getMaxOfResourceY(Resource Y) {
        switch (Y) {
            case Fuel:
                return fuel_max;
            case Shield:
                return shield_max;
            case Ammo:
                return weapon_max;
            case Artifact:
                return 5;
            default:
                return getCargoHold();
        }
    }

    public int getNdeYRecursos(Resource Y) {
        int n = 0;
        if (resources.contains(Y))
            for (var i : resources)
                if (i == Y)
                    ++n;
        return n;
    }

    public void setXResourcesOfTypeY(int X, Resource Y) {
        X -= getNdeYRecursos(Y);
        if (X > 0)
            addXResourcesOfTypeY(X, Y);
        else
            deleteXResourcesOfTypeY(X, Y);
    }

    @SuppressWarnings("UnusedReturnValue")
    public int addXResourcesOfTypeY(int X, Resource Y) {
        int n = getNdeYRecursos(Y);
        int max_n = getMaxOfResourceY(Y);
        if (n + X > max_n) {
            X = max_n - n;
        }

        ResourceTypes.fillVector(resources, Y, X);

        return X;
    }

    ////////////////////////////////////////////////////////

    /**
     * @param X
     * @param Y
     * @return removed items
     */
    public int deleteXResourcesOfTypeY(int X, Resource Y) {
        int removed = 0;
        int ndeYRecursos = getNdeYRecursos(Y);
        if (ndeYRecursos < X) {
            deleteAllResourcesOfTypeY(Y);
        } else {
            int i = 0;
            while ((X > 0) && (resources.size() - 1 != i))
                if (resources.get(i) == Y) {
                    resources.remove(i);
                    --X;
                    ++removed;
                } else ++i;
        }
        return removed;
        //resources.removeIf(i -> ((i == Y)&& ((X-> X--)> 0)));
    }

    public void deleteAllResourcesOfTypeY(Resource Y) {
        resources.removeIf(i -> i == Y);
    }

    public ShipType getShipType() {
        return shipType;
    }

    public ResourceTypes getResourceTypes() {
        return resourceTypes;
    }

    //offices verification and management
    public boolean hasOfficer(Officer officer) {
        return officers.contains(officer);
    }


    ////////////////////////////////////////////////////////

    //returna false quando nao tem mais crew para retirar isto e,
    // ele removeu o capitao e nao existe mais elementos logo ,
    // gameover
    public boolean removeOfficer() {
        officers.removeElementAt(officers.size() - 1);
        return officers.isEmpty();
    }

    public Vector<Officer> getOfficers() {
        return officers;
    }

    public boolean addOfficer() {

        //ja esta cheio
        if (officers.size() == Officer.values().length) {
            return false;
        }
        return officers.add(Officer.values()[officers.size()]);
    }

    public void novoPlanet() {
        setSpaceStation(3 > ShipDados.getRandom().nextInt(10000) / 1000);
        if (hasSpaceStation()) {
            cargo_upgraded_here = false;
        }
        this.planetType = getResourceTypes().randomPlanet();
        addEvent(EventType.NovoPlaneta, "Num novo planeta " + getPlanetType().name() + (hasSpaceStation() ? " com " : " sem ") + "SpaceStation.");
        vezesQueSePodeVizitarOPlaneta = getResourceTypes().getPlanetTypes(getPlanetType()).size();
        artfactoFoiMinado = false;
    }

    ////////////////////////////////////////////////////////

    // sobre a gestao de eventos

    /**
     * @defgroup group_shipDadosEvents Gestão de eventos
     * @{ Gestão de eventos num modo estatico, para mais sobre os eventos leia \link Event Event \endlink
     * @}
     */

    public boolean hasSpaceStation() {
        return spaceStation;
    }

    /**
     * @}
     */

    public int getVezesQueSePodeVizitarOPlaneta() {
        return vezesQueSePodeVizitarOPlaneta;
    }

    /**
     * @}
     */

    public void setSpaceStation(boolean spaceStation) {
        this.spaceStation = spaceStation;
    }

    /**
     * @}
     */

    public PlanetType getPlanetType() {
        return planetType;
    }

    /**
     * @}
     */

    public void setPlanetType(PlanetType planetType) {
        this.planetType = planetType;
    }

    /**
     * @}
     */

    public void newTerreno() {
        Resource aEnviar;

        do {
            aEnviar = randomResource(resourceTypes.getPlanetTypes(getPlanetType()));
        } while (aEnviar == Resource.Artifact && artfactoFoiMinado);

        terreno = new Terreno(aEnviar);
    }

    /**
     * @}
     */

    public Terreno getTerreno() {
        return terreno;
    }

    public void deleteTerreno() {
        terreno = null;
    }

    //devolta a ship com sucesso
    public void deVoltaAShipComRecurso() {
        Resource resource = getTerreno().getResource().getResourceType();
        int ranum;

        //caso for um artefacto
        if (resource == Resource.Artifact) {
            artfactoFoiMinado = true;
            int ranum2 = getRandom().nextInt(5000) / 1000;
            if (ranum2 > 0) ranum = 2 + getNdeYRecursos(resource);
            else ranum = 1 + getNdeYRecursos(resource);
        } else
            ranum = getRandom().nextInt(6 * 1000) / 1000 + 1 + getNdeYRecursos(resource);
        if (getNdeYRecursos(resource) >= getCargoHold()) {
            addEvent(EventType.MoreResources, "Nao consegues ter mais deste " + resource + ". :(");
        } else {
            ShipDados.addEvent(EventType.MoreResources, "Ganhaste " + (ranum - getNdeYRecursos(resource)) + " de " + resource + "!");
            setXResourcesOfTypeY(ranum, resource);
        }
    }

    public boolean hasDrone() {
        return drone;
    }

    public void comprarDrone() {
        if (hasSpaceStation() && !hasDrone())
            drone = true;
    }

    public void morteDoDrone() {
        drone = false;
    }


}
