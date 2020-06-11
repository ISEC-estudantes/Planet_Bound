package Planet_Bound.logica.estados;

import Planet_Bound.logica.dados.aux.Direcoes;
import Planet_Bound.logica.dados.ship.ShipDados;
import Planet_Bound.logica.estados.birthanddeath.NovoShip;

abstract public class EstadoAdapter implements IEstado {

    private final ShipDados shipDados;

    public EstadoAdapter(ShipDados shipDados) {
        this.shipDados = shipDados;
    }

    protected ShipDados getShipDados() {
        return shipDados;
    }

    @Override
    public IEstado landOnThePlanet() {
        return this;
    }

    @Override
    public IEstado returnToShip() {
        return this;
    }

    @Override
    public IEstado convertResources() {
        return this;
    }

    @Override
    public IEstado proximoPlaneta() {
        return this;
    }

    @Override
    public IEstado setOpcao(int opcao) {
        return this;
    }

    @Override
    public IEstado landOnSpaceStation() {
        return this;
    }

    @Override
    public IEstado novoJogo() {
        return this;
    }


    @Override
    public IEstado aplicaEvento(int idEvento) {
        return this;
    }

    @Override
    public IEstado move(Direcoes direcao) {
        return this;
    }
}
