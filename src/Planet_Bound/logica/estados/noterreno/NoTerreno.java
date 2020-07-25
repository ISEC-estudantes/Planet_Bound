package Planet_Bound.logica.estados.noterreno;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noespaco.NoEspaco;

//!


/**
 * \brief Este estado é para quando nos encontramos no terreno
 * <p>
 * <p>
 * Quando se entra no estado NoTerreno o construtor do terreno retira uma fuel cell e cria um novo terreno.
 * <p>
 * Neste pode só movimentar para algum lado ou voltar para a ship
 * Quando o drone é destruido ou volta ao ponto inicial com o recurso volta se para a ship a diferença entre um ou outro é que quando volta leva com ele 1-6 do recurso que apanhou
 */
public class NoTerreno extends EstadoAdapter {


    public NoTerreno(ShipDados shipDados) {
        super(shipDados);
        getShipDados().newTerreno();
    }

    @Override
    public IEstado move(Direcoes direcao) {
        int resultado = getShipDados().getTerreno().moveDrone(direcao);
        switch (resultado) {
            //se ficar sem drone volta para o espaço
            case -2:
                getShipDados().morteDoDrone();
                return new NoEspaco(getShipDados(), false);
            case 1:
                getShipDados().deVoltaAShipComRecurso();
                return new NoEspaco(getShipDados(), false);
            case -1:
            default:
                return this;
        }
    }


    @Override
    public IEstado returnToShip() {
        return new NoEspaco(getShipDados(), false);
    }

    @Override
    public EstadoJogo getEstado() {
        return EstadoJogo.NoTerreno;
    }
}
