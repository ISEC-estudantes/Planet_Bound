package Planet_Bound.logica;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.aux.Officer;
import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.Event;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.PlanetType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.resourcesandplanets.ResourceTypes;
import Planet_Bound.logica.dados.ship.ShipType;
import Planet_Bound.logica.estados.EstadoJogo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Vector;

public class ShipObservavel {
    private final PropertyChangeSupport propertyChangeSupport;
    private final Ship ship;

    public ShipObservavel(Ship ship) {
        this.ship = ship;
        propertyChangeSupport = new PropertyChangeSupport(ship);
    }


    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    ////////////////////////////////////////////////////////
    //DELEGACOES

    // mudancas de estados delegadas
    public void landOnThePlanet() {
        ship.landOnThePlanet();
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void returnToShip() {
        ship.returnToShip();
        propertyChangeSupport.firePropertyChange(null,false,true);

    }

    public void convertResources() {
        ship.convertResources();
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void proximoPlaneta() {
        ship.proximoPlaneta();
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void setOpcao(int opcao) {
        ship.setOpcao(opcao);
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void move(Direcoes direcao) {
        ship.move(direcao);
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void landOnSpaceStation() {
        ship.landOnSpaceStation();
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    public void novoJogo() {
        ship.novoJogo();
        propertyChangeSupport.firePropertyChange(null,false,true);
    }

    // gets delegados
    public EstadoJogo getEstadoJogo(){
        return ship.getEstado();
    }

    public static List<Event> getEvents() {
        return Ship.getEvents();
    }

    public static List<Event> getAllUnreadEvents() {
        return Ship.getAllUnreadEvents();
    }

    public static List<Event> getNLastEvents(int nEvents) {
        return Ship.getNLastEvents(nEvents);
    }

    public Options getOptions() {
        return ship.getOptions();
    }

    public Class<?> getEstadoClass() {
        return ship.getEstadoClass();
    }

    public EstadoJogo getEstado() {
        return ship.getEstado();
    }

    public boolean hasOfficer(Officer officer) {
        return ship.hasOfficer(officer);
    }

    public Vector<Officer> getOfficers() {
        return ship.getOfficers();
    }

    public boolean hasSpaceStation() {
        return ship.hasSpaceStation();
    }

    public int getVezesQueSePodeVizitarOPlaneta() {
        return ship.getVezesQueSePodeVizitarOPlaneta();
    }

    public static void addEvent(EventType eventType, String msg) {
        Ship.addEvent(eventType, msg);
    }

    public PlanetType getPlanetType() {
        return ship.getPlanetType();
    }

    public boolean droneHasResource() {
        return ship.droneHasResource();
    }

    public boolean hasDrone() {
        return ship.hasDrone();
    }

    public int getCargoHold() {
        return ship.getCargoHold();
    }

    public int getMaxOfResourceY(Resource Y) {
        return ship.getMaxOfResourceY(Y);
    }

    public int getNdeYRecursos(Resource Y) {
        return ship.getNdeYRecursos(Y);
    }

    public ShipType getShipType() {
        return ship.getShipType();
    }

    public ResourceTypes getResourceTypes() {
        return ship.getResourceTypes();
    }

    public Ship.ITerreno getITerreno() {
        return ship.getITerreno();
    }
}


