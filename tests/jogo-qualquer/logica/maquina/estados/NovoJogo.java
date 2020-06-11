package logica.maquina.estados;

import Planet_Bound.ui.gui.logica.dados.JogoDados;
import logica.maquina.EstadoAdaptor;
import logica.maquina.IEstador;

public class NovoJogo extends EstadoAdaptor {

    public NovoJogo(JogoDados jogoDados) {
        super(jogoDados);
    }

    @Override
    public IEstador setNome(String nome){
            getJogoDados().setName(nome);
            return new AguardaResposta(getJogoDados());
    }


}
