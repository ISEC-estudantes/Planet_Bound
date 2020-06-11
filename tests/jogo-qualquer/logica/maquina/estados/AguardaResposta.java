package logica.maquina.estados;

import Planet_Bound.ui.gui.logica.dados.JogoDados;
import logica.maquina.EstadoAdaptor;
import logica.maquina.IEstador;

public class AguardaResposta extends EstadoAdaptor {

    AguardaResposta(JogoDados jogoDados) {
        super(jogoDados);
    }

    @Override
    public IEstador giveResposta(int resposta) {
        int erros = getJogoDados().verificar(resposta);
        if(erros >=3 || erros <= 0)
            return new Recomecar(getJogoDados());

        return this;
    }
}
