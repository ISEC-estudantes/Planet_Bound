package Planet_Bound.ui.gui.estados;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.events.Event;
import Planet_Bound.logica.dados.events.EventType;
import Planet_Bound.logica.estados.EstadoJogo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class GGameOver extends VBox {
    final ShipObservavel modelo;
    Label questao;
    public GGameOver(ShipObservavel model) {
        modelo = model;
        organiza();
        modelo.addPropertyChangeListener(e -> actulizaVista());
        actulizaVista();

    }

    private void organiza() {
        Button novojogo = new Button("Novo Jogo");
        novojogo.setOnAction(e -> modelo.novoJogo());
        questao = new Label("");
        Button desisto = new Button("Desisto >:^(");
        desisto.setOnAction(e -> ((Stage) getScene().getWindow()).close());
        HBox escolhas = new HBox();
        escolhas.getChildren().addAll(novojogo, desisto);
        escolhas.setAlignment(Pos.CENTER);
        escolhas.setSpacing(10);
        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(questao, escolhas);
    }

    private void actulizaVista(){
        if(modelo.getEstadoJogo() == EstadoJogo.GameOver){
            List<Event> eventos = modelo.getEvents();
            int i;
            for (i = (eventos.size() <= 0) ? 0 :eventos.size() -1; i >= 0; --i)
                if(eventos.get(i).getEventType() == EventType.GameOver)
                    break;
            questao.setText("GAME OVER - "+ eventos.get(i).getMsg());
            setVisible(true);
        }else
            setVisible(false);
    }
}
