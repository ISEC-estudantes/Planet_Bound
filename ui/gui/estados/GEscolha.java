package Planet_Bound.ui.gui.estados;

import Planet_Bound.logica.ShipObservavel;
import Planet_Bound.logica.dados.resourcesandplanets.Resource;
import Planet_Bound.logica.estados.EstadoJogo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import static Planet_Bound.ui.gui.Helper.Resource2Color;

public class GEscolha extends VBox {

    final Label questao;
    final HBox escolhas;
    final EstadoJogo lastState;
    private final ShipObservavel modelo;
    HBox questaoBox;
    Rectangle questaoSquare;

    public GEscolha(ShipObservavel model) {
        modelo = model;
        modelo.addPropertyChangeListener(e -> atualizaVista());
        setAlignment(Pos.CENTER);
        setSpacing(10);
        questaoSquare = new Rectangle();
        questaoSquare.setHeight(10);
        questaoSquare.setWidth(10);
        questao = new Label();
        questaoBox = new HBox();
        questaoBox.getChildren().addAll(questaoSquare, questao);
        escolhas = new HBox();
        escolhas.setAlignment(Pos.CENTER);
        escolhas.setPadding(new Insets(10));
        escolhas.setSpacing(10);
        getChildren().addAll(questao, escolhas);
        lastState = null;
        atualizaVista();
    }

    private void addTitle() {
        if (modelo.getOptions().getHeaderResource() != null) {
            questaoSquare.setFill(Resource2Color(modelo.getOptions().getHeaderResource()));
            questaoSquare.setVisible(true);
        } else
            questaoSquare.setVisible(false);
        questao.setText(modelo.getOptions().getHeaderString());
    }

    private void setUpButtons() {
        // limpeza da selecao de opcoes
        escolhas.getChildren().clear();
        var optionsString = modelo.getOptions().getOptionsString();
        var optionsResources = modelo.getOptions().getOptionsResources();
        var nOptions = modelo.getOptions().getNOptions();
        var estado = modelo.getEstadoJogo();

        //rondar todas as opcoes
        //o novo jogo [militar e explorador]
        if (estado == EstadoJogo.NovoShip) {
            var shipBox = new HBox();
            setSpacing(10);
            for (int i = 0; i < nOptions; ++i) {
                bSOButtons(shipBox, optionsString.get(i), i);
            }
            escolhas.getChildren().add(shipBox);
        } else {//buttao de voltar para os estados que o tem
            var sair = new Button(optionsString.get(0));
            sair.setAlignment(Pos.BOTTOM_RIGHT);

            //a vertical box em que todos os menus vao estar
            var opcoesDeMenu = new VBox();
            opcoesDeMenu.setAlignment(Pos.CENTER);
            opcoesDeMenu.setSpacing(5);
            for (int i = 1; i < nOptions; ++i)

                //Coversao de Recursos
                if (optionsResources != null) {

                    //conjunto de recursos
                    if (optionsResources.size() > optionsString.size()) {
                        var tempBox = new HBox();
                        if (i == 1)
                            for (var ii : modelo.getOptions().getOptionsResources()) {
                                if (ii == null) continue;
                                bSOSquares(tempBox, ii);
                            }
                        bSOButtons(tempBox, optionsString.get(i), i);
                        opcoesDeMenu.getChildren().add(tempBox);
                    }

                    //recursos individuais
                    var tempBox = new HBox();
                    bSOSquares(tempBox, optionsResources.get(i));
                    bSOButtons(tempBox, optionsString.get(i), i);
                    opcoesDeMenu.getChildren().add(tempBox);

                    //Sem os recursos nas opcoes ou space station
                } else {
                    bSOButtons(opcoesDeMenu, optionsString.get(i), i);
                }
            escolhas.getChildren().add(opcoesDeMenu);
            escolhas.getChildren().add(sair);
        }
    }

    //basicSetOf****
    private void bSOButtons(Pane pane, String string, int i) {
        var button = new Button(string);
        button.setOnAction(e -> modelo.setOpcao(i));
        pane.getChildren().add(button);
    }

    private void bSOSquares(Pane pane, Resource resource) {
        var tempSquare = new Rectangle();
        tempSquare.setWidth(10);
        tempSquare.setHeight(10);
        tempSquare.setFill(Resource2Color(resource));
        pane.getChildren().add(tempSquare);

    }


    private void atualizaVista() {
        if ((modelo.getEstadoJogo() == EstadoJogo.NovoShip) || (modelo.getEstadoJogo() == EstadoJogo.ConvertResources) || (modelo.getEstadoJogo() == EstadoJogo.OnSpaceStation)) {
            addTitle();
            setUpButtons();
            setVisible(true);
        } else {
            setVisible(false);
        }
    }

}