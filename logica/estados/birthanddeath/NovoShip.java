package Planet_Bound.logica.estados.birthanddeath;

import Planet_Bound.logica.dados.aux.Options;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.dados.ship.ShipType;
import Planet_Bound.logica.estados.EstadoAdapter;
import Planet_Bound.logica.estados.EstadoJogo;
import Planet_Bound.logica.estados.IEstado;
import Planet_Bound.logica.estados.noespaco.NoEspaco;

import java.util.Vector;

public class NovoShip extends EstadoAdapter {
    public NovoShip(ShipDados shipDados) {
        super(shipDados);
        ShipDados.addEvent(EventType.Debug, "Novo jogo");
        Vector<String> navios = new Vector<>();
        for (var i : ShipType.values()) {
            if (i == ShipType.placeholder) continue;
            navios.add(i.toString());
        }
        shipDados.setOptions(new Options("Qual a Ship que quer escolher?", navios));
    }

    @Override
    public IEstado setOpcao(int opcao) {
        switch (opcao) {
            case 0:
                getShipDados().reset(ShipType.military);
                ShipDados.addEvent(EventType.NovoShip, "Criado Ship do tipo military.");
                return new NoEspaco(getShipDados(), true);
            case 1:
                getShipDados().reset(ShipType.mining);
                ShipDados.addEvent(EventType.NovoShip, "Criado Ship do tipo mining.");
                return new NoEspaco(getShipDados(), true);
            default:
                return this;
        }
    }

    @Override
    public EstadoJogo getEstado() {
        return EstadoJogo.NovoShip;
    }
}
