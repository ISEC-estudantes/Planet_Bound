package Planet_Bound.logica.estados;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.estados.birthanddeath.NovoShip;

public interface IEstado {

    IEstado landOnThePlanet();

    IEstado returnToShip();

    IEstado convertResources();

    IEstado aplicaEvento(int idEvento);

    //! avançar para um proximo planeta
    IEstado proximoPlaneta();

    //! dar receber feedback do user
    IEstado setOpcao(int opcao);

    //! aterra na space station
    IEstado landOnSpaceStation();

    IEstado novoJogo();

    //! move um objecto
    IEstado move(Direcoes direcao);

    EstadoJogo getEstado();


}
