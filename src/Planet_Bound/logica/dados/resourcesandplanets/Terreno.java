package Planet_Bound.logica.dados.resourcesandplanets;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.events.EventType;

import java.io.Serializable;


public class Terreno  implements Serializable {

    private Drone drone;

    public Alien getAlien() {
        return alien;
    }

    private Alien alien;

    public int getCounter() {
        return counter;
    }

    public Drone getDrone() {
        return drone;
    }

    public ResourceInPlanet getResource() {
        return resource;
    }

    public final int localInicialX, localInicialY;

    public int getLocalInicialX() {
        return localInicialX;
    }

    public int getLocalInicialY() {
        return localInicialY;
    }

    final private ResourceInPlanet resource;

    //este contador e para quantos movimentos ate aparecer outro alien
    private int counter;

    public Terreno(Resource resourceType) {
        //grid = new Integer[6][6];
        counter = 0;
        drone = new Drone();
        resource = new ResourceInPlanet(resourceType);
        alien = new Alien();


        boolean samelocation;
        //verificacao das localizaoes aleatorias
        while (inTheSameSpot(getDrone(), getAlien())) {
            getAlien().newRandomLocation();
        }
        do {
            samelocation = false;
            while (inTheSameSpot(getDrone(), getResource())) {
                getResource().newRandomLocation();
                samelocation = true;
            }
            while (inTheSameSpot(getAlien(), getResource())) {
                getResource().newRandomLocation();
                samelocation = true;
            }
        } while (samelocation);

        localInicialX = getDrone().getCoordenadasX();
        localInicialY = getDrone().getCoordenadasY();
    }

    public boolean droneHasResource() {
        return getResource().apanhadoPeloDrone;
    }

    public static Direcoes getOppositeDirection(Direcoes direcoes) {
        if (direcoes == Direcoes.cima) return Direcoes.baixo;
        if (direcoes == Direcoes.baixo) return Direcoes.cima;
        if (direcoes == Direcoes.esquerda) return Direcoes.direita;
        if (direcoes == Direcoes.direita) return Direcoes.esquerda;
        throw new Error("A direçao nao existe.");
    }

    public int moveDrone(Direcoes direcao) {
        if (!moveObject(getDrone(), direcao)) {
            ShipDados.addEvent(EventType.DroneMovement, "Não podes ir por ai, isso é uma parede.");
            return -1; // nao podes mover o drone para essa direcao
        }

        if ((getDrone().getCoordenadasX() == localInicialX) && (getDrone().getCoordenadasY() == localInicialY) && getResource().apanhadoPeloDrone) {
            ShipDados.addEvent(EventType.DroneMovement, "Chegaste ao ponto inicial com o Resource!! Very Nice.");
            return 1; //conseguiu regressar ao ponto inicial com o resource
        }

        if ((getDrone().getCoordenadasX() == getResource().getCoordenadasX()) && (getDrone().getCoordenadasY() == getResource().getCoordenadasY()) && (!getResource().apanhadoPeloDrone)) {
            getResource().apanhadoPeloDrone = true;
            ShipDados.addEvent(EventType.DroneMovement, "Apanhaste o Resource "+ getResource().getResourceType() +"! Agora volta para o ponto inicial.");
        }

        if (counter > 0)
            --counter;
        else if (alien == null) {
            criaNovoAlien();
        }

        if (alien != null) {
            if (moveAlien()) {
                ShipDados.addEvent(EventType.Info, "O alien vai entrar em compate com o Drone.");
                if (!ATTACK())
                    return -2;
            }
        }
        return 0;
    }


    //the alien attack
    private boolean ATTACK() {
        int randValue;

        do {
            randValue = ShipDados.getRandom().nextInt(6000)/1000;
            if (randValue < getAlien().getAttack())
                if (!drone.loseHealth()) {
                    drone = null;
                    ShipDados.addEvent(EventType.DroneDeath, "O teu drone morreu em combate. :'(");

                    return false; // o drone morreu
                }
            //
            randValue = ShipDados.getRandom().nextInt(6000)/1000;
            if (randValue < getAlien().getDeath()) {
                alien = null;
                counter = ShipDados.getRandom().nextInt(6000)/1000;
                ShipDados.addEvent(EventType.AlienKilled, "An alien doesn't feel soo good. Um novo virá daqui a " + counter + " jogadas.");
            }
        } while (getAlien() != null);
        return true;
    }


    //! move o alien em direcao do drone, caso esteja numa posicao para atacar o drone ele returna true
    private boolean moveAlien() {



        if(nearBy())
            return true;

        //differencas
        int differencaX =
                getDrone().getCoordenadasX() - getAlien().getCoordenadasX();
        int differencaY = getDrone().getCoordenadasY() - getAlien().getCoordenadasY();

        if (Math.abs(differencaX) > Math.abs(differencaY)) {
            if (differencaX > 0) {
                moveObject(getAlien(), Direcoes.direita);
            } else {
                moveObject(getAlien(), Direcoes.esquerda);
            }
        } else {
            if (differencaY > 0) {
                moveObject(getAlien(), Direcoes.cima);
            } else {
                moveObject(getAlien(), Direcoes.baixo);
            }
        }

        return nearBy();
    }
    private boolean nearBy(){
        int differencaX =
                getDrone().getCoordenadasX() - getAlien().getCoordenadasX();
        int differencaY = getDrone().getCoordenadasY() - getAlien().getCoordenadasY();

        if (differencaX == 0 && differencaY == 0)
            return true;

        return (Math.abs(differencaX) == 1 && differencaY == 0) ||
                (differencaX == 0 && Math.abs(differencaY) == 1);
    }
    private static Direcoes randomDirection() {
        int direcao = ShipDados.getRandom().nextInt(4000)/1000;
        switch (direcao) {
            case 1:
                return Direcoes.esquerda;
            case 2:
                return Direcoes.direita;
            case 3:
                return Direcoes.baixo;
            default:
                return Direcoes.cima;
        }
    }

    public boolean moveObject(Entidade entidade, Direcoes direcao) {
        switch (direcao) {
            case baixo:
                return entidade.movedown();
            case cima:
                return entidade.moveup();
            case direita:
                return entidade.moverigh();
            case esquerda:
                return entidade.moveleft();
            default:
                throw new Error("Entidade ou Direcao nao Existe");
        }
    }

    private boolean inTheSameSpot(
            Entidade entidade1,
            Entidade entidade2
    ) {
        return (entidade1.getCoordenadasX() == entidade2.getCoordenadasX())
                &&
                (entidade1.getCoordenadasY() == entidade2.getCoordenadasY());
    }


/////////////////////////////////////////////////


    //sobre as entidades e a entidade
    public enum Entidades {
        drone, alien, resource
    }

    static class Entidade {
        final Integer[] coordenadas;
        /*

        y
        ^
        |
       6+ - - - - - +
        |           |
        |
        |           |
        |
        +-----------+--->x
                    6

        */
        //final private Entidades entidade;

        public Entidade() {
            coordenadas = new Integer[2];
            newRandomLocation();
            //this.entidade = entidade;
        }

        public void newRandomLocation() {
            for (int i = 0; i < 2; ++i)
                coordenadas[i] = ShipDados.getRandom().nextInt(5000)/1000;
        }

        public int getCoordenadasX() {
            return coordenadas[0];
        }

        public int getCoordenadasY() {
            return coordenadas[1];
        }

        public boolean moveup() {
            if (coordenadas[1] < 5) {
                ++coordenadas[1];
                return true;
            } else {
                return false;
            }
        }

        public boolean movedown() {
            if (coordenadas[1] > 0) {
                --coordenadas[1];
                return true;
            } else {
                return false;
            }
        }

        public boolean moverigh() {
            if (coordenadas[0] < 5) {
                ++coordenadas[0];
                return true;
            } else {
                return false;
            }
        }

        public boolean moveleft() {
            if (coordenadas[0] > 0) {
                --coordenadas[0];
                return true;
            } else {
                return false;
            }
        }
    }

    public static class Alien extends Entidade {
        //como estes sao so probablidades eu decidi simplificar o sistema;entao
        private final int attack;
        private final int death;
        private final String type;

        public Alien() {
            int value = ShipDados.getRandom().nextInt(4000)/1000;
            switch (value) {
                case 0:
                    type = "Black";
                    attack = 1;
                    death = 2;
                    break;
                case 1:
                    type = "Green";
                    attack = 2;
                    death = 3;
                    break;
                case 2:
                    type = "Blue";
                    attack = 3;
                    death = 3;
                    break;
                case 3:
                    type = "Red";
                    attack = 2;
                    death = 2;
                    break;
                default:
                    throw new Error("Esse Alien nao existe.");
            }
        }

        public String getType() {
            return type;
        }

        public int getAttack() {
            return attack;
        }

        public int getDeath() {
            return death;
        }
    }

    private void criaNovoAlien() {

        alien = new Alien();

        boolean samelocation;
        //verificacao das localizaoes aleatorias
        do {
            samelocation = false;
            while (inTheSameSpot(getDrone(), getAlien())) {
                getAlien().newRandomLocation();
            }

            while (inTheSameSpot(getAlien(), getResource())) {
                getAlien().newRandomLocation();
                samelocation = true;
            }
        } while (samelocation);
    }

    public static class ResourceInPlanet extends Entidade {
        final Resource resourceType;
        boolean apanhadoPeloDrone;

        ResourceInPlanet(Resource resourceType) {
            this.resourceType = resourceType;
            apanhadoPeloDrone = false;
        }

        public Resource getResourceType() {
            return resourceType;
        }

    }


    public static class Drone extends Entidade {
        private int health;

        Drone() {
            health = 6;
        }

        public int getHealth() {
            return health;
        }

        public boolean loseHealth() {//se ele returnar false e' porque morreu
            if (health > 1) {
                --this.health;
                return true;
            } else {
                return false;
            }
        }
    }
}
