package Planet_Bound;

import Planet_Bound.ui.gui.Gui;


public class Main {


    static public float versao() {
        return (float) 0.5;
    }

    /**
     * <h1>Planet_Bound</h1>
     *
     * @version 0.5
     * @author João Gonçalves 2018014306
     * @mainpage Trabalho de PA 1920
     * @section intro_sec Introdução
     * O relatorio existe em formato html e pdf, ambas as sources estao na pasta docs mas aconcenho a versão em html que é a mais facil de se navegar e a que foi testado toda a documentação.
     *
     * <p>
     * O trabalho foi feito com a tentativa de seguir o melhor possivel o do que era pedido nos enunciados do jogo e escolar mas tem algumas alteracoes para lo tornar um pouco diferente.
     * <p>
     * É aconsenhado que se olhe para o codigo ao mesmo tempo que se le este relatorio, já que o codigo tem uma explicação muito mais profunda.
     * Para mais informações sobre cada class e metodo abra as suas respectivas paginas.
     * <p>
     * <h2>Diagrama de Estados</h2>
     * \image html "diagramaDeEstados.png"
     *\image latex  "diagramaDeEstados.png"
     *
     * A logica do jogo começa com um new Ship, este irá criar um novo shipdados(generico) e o estado NovoJogo
     * @section sec_noespaco NoEspaço
     * <p>
     * O jogo começa no Estado  \link Planet_Bound.logica.estados.birthanddeath.NovoShip NovoShip \endlink , siga os estados a partir do diagrama para ver cada class.
     * <p>
     * Para vencer o jogo é preciso ganhar ter 5 artifactos, se conseguires obter num planet existe uma probablidade de ganhares 2, essa probablidade é de 1/5
     *

     *
     *
     */


    //! Esta é a função main
    public static void main(String[] args) {
        //System.out.println("size of args = "+ args.length +(args.length > 0 ? ("\nContiudo:"+Arrays.toString(args)) : ""));

        try {
            switch (args[0]) {
                //case "-c":
                //case "--cli":
                    //var cmd = new InterfaceCMD();
                    //cmd.start();
                   // System.out.println("A interface de texto nao esta disponivel.");
                    //break;
                case "-h":
                case "--help":
                    printhelp();
                    break;
                case "-v":
                case "--version":
                    System.out.println("Versão v" + versao());

                    break;
                case "-g":
                case "--gui":
                default:
                    Gui.main2(args);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Gui.main2(args);
        }
    }


    private static void printhelp() {
        System.out.println(
                "Planet_Bound - v" + versao() + "\n" +
                        "Usage: Planet_Bound [OPTIONS]\n" +
                        "Opções:\n\t" +
                        "-h, --help\tPrinta esta help e sai.\n\t" +
                        "-v, --version\tPrinta a verão deste programa e sai.\n\t" +
                        //"-c, --cli\tExecuta o programa em cli mode.\n\t" +
                        "-g, --gui\t[Default] Executa o programa em GUI mode."
        );

    }
}
