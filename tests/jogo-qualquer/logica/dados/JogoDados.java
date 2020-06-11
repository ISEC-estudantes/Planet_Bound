package logica.dados;

import java.util.Random;

public class JogoDados {

    private String Gamer;
    private int nErros;
    private int pontuacao;
    private int A, B, Resposta;

    public String getName(){
        return Gamer;
    }

    public void setName(String gamer){
        pontuacao =0;
        nErros = 0;
        Gamer = gamer;
    }

    public void setNewAB(){
        nErros =0;
        Random random = new Random(System.currentTimeMillis()/1000L);
        A = random.nextInt(100*1000)/1000 + 1;
        B = random.nextInt(100*1000)/1000 + 1;
        Resposta = A+B;
    }
    public int getA(){return A;}
    public int getB(){return B;}
    public int getErros(){ return nErros; }

    public int verificar(int resposta){
        if(resposta == Resposta){
            ++pontuacao;
            nErros = 0;
            return 0;
        }
        ++nErros;
        return nErros;

    }

}

