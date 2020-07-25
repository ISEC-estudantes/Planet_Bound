package Planet_Bound.logica.dados.resourcesandplanets;

import Planet_Bound.logica.dados.ship.ShipDados;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class ResourceTypes   implements Serializable {
    //!planets
    private final Vector<Resource> planetBlack;

    public List<Resource> getPlanetBlack() {
        return Collections.unmodifiableList(planetBlack);
    }


    private final Vector<Resource> planetRed;

    public List<Resource> getPlanetRed() {
        return Collections.unmodifiableList(planetRed);
    }


    private final Vector<Resource> planetGreen;

    public List<Resource> getPlanetGreen() {
        return Collections.unmodifiableList(planetGreen);
    }


    private final Vector<Resource> planetBlue;

    public List<Resource> getPlanetBlue() {
        return Collections.unmodifiableList(planetBlue);
    }


    private final Vector<Resource> recursos;

    public List<Resource> getNormalResources() {
        return Collections.unmodifiableList(recursos);
    }

    private Vector<Resource>[] planets;

    public ResourceTypes() {
        planetBlack = new Vector<>();
        planetRed = new Vector<>();
        planetGreen = new Vector<>();
        planetBlue = new Vector<>();
        recursos = new Vector<>();
        ResourceTypes.fillVector(planetBlack, Resource.Black, Resource.Blue);
        ResourceTypes.fillVector(planetGreen, Resource.Red, Resource.Green);
        ResourceTypes.fillVector(planetRed, Resource.Red, Resource.Blue);
        ResourceTypes.fillVector(planetBlue, Resource.Black, Resource.Blue, Resource.Green, Resource.Artifact);
        ResourceTypes.fillVector(recursos, Resource.Black, Resource.Blue, Resource.Green, Resource.Red);
    }

    static public void fillVector(Vector<Resource> vector, Resource... resources) {
        Collections.addAll(vector, resources);
    }

    static public void fillVector(Vector<Resource> vector, Resource resource, int ntimes) {
        for (var i = 0; i < ntimes; ++i)
            vector.add(resource);
    }

    public List<Resource> getPlanetTypes(PlanetType planetType) {
        switch (planetType) {
            case Red:
                return getPlanetRed();
            case Blue:
                return getPlanetBlue();
            case Black:
                return getPlanetBlack();
            case Green:
                return getPlanetGreen();
            default:
                return null;
        }
    }

    public PlanetType randomPlanet() {
        int randomvalue = ShipDados.getRandom().nextInt(4000)/1000;
        return PlanetType.values()[randomvalue];
    }
}
