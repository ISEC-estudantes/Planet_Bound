package Planet_Bound.logica.estados.birthanddeath;

import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;

public class GameOver extends EstadoAdapter {
    private final String mensagem;

    public GameOver(ShipDados shipDados, String msg) {
        super(shipDados);
        mensagem = msg;
        ShipDados.addEvent(EventType.GameOver, msg);
    }

    @Override
    public IEstado novoJogo(){
        return new NovoShip(getShipDados());
    }

    @Override
    public String toString() {
        return mensagem;
    }

    @Override
    public EstadoJogo getEstado(){
        return EstadoJogo.GameOver;
    }
}
