package logica;

import Planet_Bound.ui.gui.logica.dados.JogoDados;
import Planet_Bound.ui.gui.logica.maquina.IEstador;
import Planet_Bound.ui.gui.logica.maquina.estados.NovoJogo;

public class Jogo {
    JogoDados jogoDados;
    IEstador iEstador;

    public Jogo(){
        jogoDados = new JogoDados();
        iEstador = new NovoJogo(jogoDados);
    }

    //delegados do JogoDados

    public String getName() {
        return jogoDados.getName();
    }

    public int getA() {
        return jogoDados.getA();
    }

    public int getB() {
        return jogoDados.getB();
    }

    public int getErros() {
        return jogoDados.getErros();
    }

    public int verificar(int resposta) {
        return jogoDados.verificar(resposta);
    }


    //delegados do Estado

    private void setEstado(IEstador iEstador){
        this.iEstador = iEstador;
    }

    public void setNome(String nome) {
        setEstado(iEstador.setNome(nome));
    }

    public void giveResposta(int resposta) {
        setEstado(iEstador.giveResposta(resposta));
    }

    public void novoJogo() {
        setEstado(iEstador.novoJogo());
    }

    public void recomecar() {
        setEstado(iEstador.recomecar());
    }
}
