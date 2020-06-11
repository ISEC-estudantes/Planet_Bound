package Planet_Bound.ui.cmd;

import Planet_Bound.logica.Ship;
import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.dados.resourcesandplanets.Terreno;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.ship.ShipType;
import Planet_Bound.logica.estados.birthanddeath.GameOver;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.ConvertResources;
import Planet_Bound.logica.estados.noespaco.menusdospacestation.OnSpaceStation;
import Planet_Bound.logica.estados.noterreno.NoTerreno;
import Planet_Bound.logica.estados.noespaco.NoEspaco;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Esta classe serve para fazer testes e fazer debugging ao projecto
 *
 */
public class InterfaceCMD {
    //create ship

    Ship ship;
    Scanner scanner;
    boolean sair;

    private void inicializacaoDasVariaveis(Ship dados, boolean debugging){
        ShipDados.debugingEvents = debugging;
        ship = dados;
        scanner = new Scanner(System.in);
        sair = false;
    }

    public InterfaceCMD(Ship ship){
        inicializacaoDasVariaveis(ship, false);
    }

    public InterfaceCMD(Ship ship, boolean debugging){
        inicializacaoDasVariaveis(ship, debugging);
    }

    public InterfaceCMD() {
        inicializacaoDasVariaveis(new Ship(), false);
    }



    public void testnavegacao(){
        ship.setShipType(ShipType.mining);
        ship.landOnThePlanet();
        while(ship.getEstadoClass() == NoTerreno.class)
            mover();

    }
    public void debug() {

        ShipDados.debugingEvents = true;



//        ship.setShipType(0);
//        showStatus();
//        printunreadevents();
//        ship.aplicaEvento(2);
//        ship.setOpcao(2);
//        showStatus();
//        printunreadevents();
//        showStatus();
//        while(ship.hasOfficer(Officer.Captain)){
//            ship.getShipDados().removeOfficer();
//            showStatus();
//        }
//        ship.landOnThePlanet();
//        printunreadevents();
    }

    public void start() {

        //cria ship
        ship.setShipType(ShipType.mining);
        printunreadevents();

        while (!sair) {

            showStatus();
            if (ship.getEstadoClass() == NoTerreno.class) {
                mover();
            } else if (ship.getEstadoClass() == NoEspaco.class) {
                menuEspaco();
            } else if (inAChooseState()) {
                choseState();
            } else if (GameOver.class == ship.getEstadoClass())
                novoJogo();
            printunreadevents();

        }
        showStatus();
        printunreadevents();
    }


    private void novoJogo() {
        System.out.println("\n");
        System.out.println("0 - sair");
        System.out.println("1 - novo jogo?");
        int choice = choicer(2);
        switch (choice) {
            case 0:
                sair = true;
                ShipDados.addEvent(EventType.Debug, "bye bye");
                break;
            case 1:
                ship.novoJogo();
                break;
        }
    }

    private void menuEspaco() {
        System.out.println("\n");
        System.out.println("0 - sair");
        System.out.println("1 - landOnThePlanet");
        System.out.println("2 - convertResources");
        System.out.println("3 - landOnSpaceStation");
        System.out.println("4 - proximo planeta");
        System.out.println("5 - encher tudo excepto artfactos");
        System.out.println("6 - encher tudo excepto incluindo artfactos");

        int choice = choicer(6);
        switch (choice) {
            case 0:
                sair = true;
                ShipDados.addEvent(EventType.Debug, "bye bye");
                break;
            case 1:
                ship.landOnThePlanet();
                break;
            case 2:
                ship.convertResources();
                break;
            case 3:
                ship.landOnSpaceStation();
                break;
            case 4:
                ship.proximoPlaneta();
                break;
            case 5:
                //for (var i : Resource.values())
                    //if (Resource.Artifact != i)
                    //    ship.getShipDados().addXResourcesOfTypeY(100, i);
                System.out.println("This options is not available.");
                break;
            case 6:
                //for (var i : Resource.values())
                //    ship.getShipDados().addXResourcesOfTypeY(100, i);
                System.out.println("This options is not available.");
                break;

        }
    }


    private int choicer(int max) {
        int choice = -1;
        String line;
        char c;
        int safetyconter = 100;

        while ((max < choice) || (choice < 0)) {
            while (!scanner.hasNextInt()) ;
            choice = scanner.nextInt();
            if ((max < choice) || (choice < 0)) {
                System.out.println("Escolhe entre " + max + " e 0");
            }
            --safetyconter;
            if (safetyconter <= 0) {
                throw new Error("um loop foi repetido 100 vezes");
            }
        }

        return choice;
    }

    private boolean inAChooseState() {
        return ( (ship.getEstadoClass() == OnSpaceStation.class) || (ship.getEstadoClass() == ConvertResources.class));
    }

    private void choseState() {
        while (inAChooseState()) {
            showStatus();
            printoptions();
        }
    }

//    private void desenharTabela(){
//        if(ship.getITerreno() != null)
//        if(null == ship.getITerreno().getCoordenadas(ship.getITerreno().getAlienType()))
//            ship.getITerreno().getCoordenadas()
//    }

    private void mover() {
        printCoordenadas();
        while (ship.getEstadoClass() == NoTerreno.class) {
            System.out.println("Direcoes: w=cima  s=baixo  a=esquerda  d=direta  q=voltar a ship");
            //low level option
            //byte[] rawinput = System.in.readAllBytes();
            //char charinput = (char) rawinput[rawinput.length - 1];

            //better option because of multybyte characters
            //while(!scanner.hasNextLine());
            //String line = scanner.nextLine();
            //alternativa
            String line = scanner.next();
            switch (line.toLowerCase().charAt(line.length() - 1)) {
                case 'w':
                    ship.move(Direcoes.cima);
                    break;
                case 's':
                    ship.move(Direcoes.baixo);
                    break;
                case 'a':
                    ship.move(Direcoes.esquerda);
                    break;
                case 'd':
                    ship.move(Direcoes.direita);
                    break;
                case 'q':
                    ship.returnToShip();
                    break;
                default:
                    System.out.println("w=cima  s=baixo  a=esquerda  d=direta  q=voltar a ship");
            }
            printunreadevents();
            printCoordenadas();

        }
    }


    //! printa o tipo da ship e os officers nela
    private void printBasicShipInformation() {
        System.out.print("Nave do tipo " + ship.getShipType() + " ;officers: ");
        if (ship.getOfficers().size() == 0) {
            System.out.print("Sem Officers\n");
        } else {
            for (var i : ship.getOfficers())
                System.out.print(i + "; ");
            System.out.print("\n");
        }
        System.out.flush();
    }

    //! printa os recursos na ship
    private void printRecursos() {
        System.out.print("\nRecursos:\n");
        for (var i : Resource.values()) {
            System.out.print("\t" + i + " : " + ship.getNdeYRecursos(i) + "/" + ship.getMaxOfResourceY(i) + "\n");
        }
    }

    //! printa todas as informaçoes possiveis
    private void showStatus() {
        printBasicShipInformation();
        printRecursos();
        printCoordenadas();
    }

    //! printa as coordenadas do alien, drone e resource
    private void printCoordenadas() {
        var terreno = ship.getITerreno();
        if (ship.getEstadoClass() == NoTerreno.class) {
            System.out.print("Coordenadas do Drone[♥ " + terreno.getDroneHealth() + "]:" + Arrays.toString(terreno.getCoordenadas(Terreno.Drone.class)) + ":init" + Arrays.toString(terreno.getCoordenadas(null)) + ";");
            if (ship.droneHasResource())
                System.out.print("O drone tem o Resource[" + terreno.getResourceType() + "]; ");
            else
                System.out.print("Coordenadas do Resource[" + terreno.getResourceType() + "]: " + Arrays.toString(terreno.getCoordenadas(Terreno.ResourceInPlanet.class)) + "; ");
            if (terreno.getCounter() > 0)
                System.out.print("O Alien estao morto, vota daqui a " + terreno.getCounter() + "rounds; ");
            else
                System.out.print("Coordenadas do Alien[" + terreno.getAlienType() + "]:" + Arrays.toString(terreno.getCoordenadas(Terreno.Alien.class)));
            System.out.print("\n");
        }
    }

    //! printa as opcoes disponiveis numa escolha
    private void printoptions() {
        Options options = ship.getOptions();
        System.out.println(options.getHeader());
        var f = 0;
        for (var i : options.getOptions())
            System.out.println("opcao" + f++ + ": " + i);
        int option = choicer(--f);
        ship.setOpcao(option);
        printunreadevents();
    }

    //! printa as os eventos ainda nao lidos
    private void printunreadevents() {
        System.out.println("\nEventos ainda nao lidos:");
        boolean existemeventos;
        var eventos = Ship.getAllUnreadEvents();
        if (eventos.size() == 0)
            System.out.println("\tNao existem novos eventos");
        else
            for (var i : eventos)
                System.out.println("\t" + i);
    }


    private void interacao() {
        if (ship.getEstadoClass() != null) {
            if (ship.getEstadoClass() == NoEspaco.class) {
                System.out.println("estou no espaco");

            }
            System.out.println("Before: " + ship.getNdeYRecursos(Resource.Blue));
            System.out.println("After: " + ship.getNdeYRecursos(Resource.Blue));
            //cmd.nextLine();
            //ship.addXResourcesOfTypeY(100, Resource.Blue);
            System.out.println("After: " + ship.getNdeYRecursos(Resource.Blue));
        }
    }


    //a começar depois depois de criar o ship
//    private void startpoint() {
//        System.out.println(ship.hasOfficer(Officer.Navigation));
//        System.out.println(ship.getEstado().getClass());
//        ship.getEstado().aplicaEvento(2);
//        System.out.println(ship.getShipDados().getOptions());
//        ship.setOpcao(2);
//        ShipDados.getEvents().forEach(i -> System.out.println(i.toString()));
//    }


    //##############################################
    //##############################################
    //##############################################

    //##############################################

    //##############################################

    //##############################################

    //####### FUNCOES DE GERAIS ####################

    //##############################################

    //##############################################

    //##############################################

    //##############################################
    //##############################################


    private boolean yesno(String ask) {
        return yesno(ask, '0');
    }

    private boolean yesno(String ask, char respostadefault) {

        //Scanner start

        //string com o sim e nao corretamente capitalizados
        //cache para cachar a linha
        String sn, cache;

        //vezes pedidas informação, necessario para não se ter um loop infinito
        int times = 0;

        //defaultreturn define que default tera num enter e o bit para saber se tem um defaut ou não
        boolean defaultreturn = false, defaultreturnbit;

        //input character
        char input = '0';

        switch (respostadefault) {
            case 'S':
            case 's':
            case 'y':
            case 'Y':
                sn = "[S/n]";
                defaultreturn = true;
                defaultreturnbit = true;
                break;

            case 'N':
            case 'n':
                defaultreturn = false;
                defaultreturnbit = true;
                sn = "[s/N]";
                break;

            default:
                defaultreturnbit = false;
                sn = "[s/n]";
        }


        do {
            ++times;
            //limpa tudo anteriormente escrito
            System.out.println(ask + sn);
            try {
                cache = scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Porfavor insira s ou n");
                continue;
            } catch (IllegalStateException e) {
                System.out.println("Output fechado.");
                break;
            }
            if (cache.length() != 0)
                try {
                    input = cache.charAt(0);
                } catch (Exception e) {
                    System.out.println("Não consigo buscar o primeiro caracter do input.");
                    break;
                }
            try {
                input = Character.toLowerCase(input);
            } catch (Exception e) {
                System.out.println("Não consigo transformar em minuscula. Talvez o caracter não tenha versão de minuscula no toLowerCase?");
                break;
            }
            switch (input) {
                case 's':
                case 'y':
                    return true;
                case 'n':
                    return false;
                case '0':
                    if (defaultreturnbit)
                        return defaultreturn;
                    break;
                default:
                    System.out.println("[input =" + input + "]S ou N please.");
            }

        } while (times < 20);
        System.out.println("Demasiadas tentativas. A enviar excepção...");
        throw new Error("Demasiadas tentativas.");

    }

}
