package logica.maquina;



public abstract class EstadoAdaptor implements IEstador {
    private final JogoDados jogoDados;

    protected EstadoAdaptor(JogoDados jogoDados){
        this.jogoDados = jogoDados;
    }

    protected JogoDados getJogoDados() {
        return jogoDados;
    }

    @Override
public IEstador recomecar(){
        return this;
}

    @Override
    public IEstador setNome(String nome) {
        return this;
    }

    @Override
    public IEstador giveResposta(int resposta) {
        return this;
    }

    @Override
    public IEstador novoJogo() {
        return new NovoJogo(jogoDados);
    }
}
