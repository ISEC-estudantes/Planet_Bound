package logica.maquina;

public interface IEstador {

    IEstador setNome(String nome);

    IEstador giveResposta(int resposta);

    IEstador novoJogo();

    IEstador recomecar();

}
