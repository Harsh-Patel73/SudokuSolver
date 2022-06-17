package com.example.sudokusolver;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.*;

public class HelloApplication extends Application implements EventHandler<ActionEvent> {
    Button solve;
    Button remove;
    int j = 0;
    int k = 0;
    int count = 0;
    List<Integer> listNum = new ArrayList<>(81);
    static List<Integer> listTemp = new ArrayList<>();
    static List<Integer> listTemp2 = new ArrayList<>();
    static List<List<Integer>> listSudBoard = new ArrayList<>(9);

    public static void main(String[] args) {
        launch(args);
    }
    GridPane grid = new GridPane();
    Pane layout = new Pane();

    private static boolean SolvePuzzle() {
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(listSudBoard.get(i).get(j) == 0) {
                    for (int k = 1; k <= 9; k++) {
                        listSudBoard.get(i).set(j, k);
                        if (CheckPuzzle()) {
                            if(SolvePuzzle()){
                                return true;
                            }
                            else{
                                listSudBoard.get(i).set(j,0);
                            }
                        }
                        if (!CheckPuzzle()){
                            listSudBoard.get(i).set(j, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public static Boolean CheckPuzzle(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (listSudBoard.get(i).get(j) != 0 && Collections.frequency(listSudBoard.get(i), listSudBoard.get(i).get(j)) > 1){
                    return false;
                }
            }
        }
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                for(int k = 0; k < 9; k++){
                    if(listSudBoard.get(j).get(i) != 0 && j != k){
                        if(Objects.equals(listSudBoard.get(j).get(i), listSudBoard.get(k).get(i))){
                            return false;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 9; i+=3){
            for(int j = 0; j < 9; j+=3){
                listTemp.clear();
                listTemp.addAll(listSudBoard.get(j).subList(i, i+3));
                listTemp.addAll(listSudBoard.get(j+1).subList(i, i+3));
                listTemp.addAll(listSudBoard.get(j+2).subList(i, i+3));
                for(int k = 0; k < listTemp.size(); k++){
                    if(listTemp.get(k) != 0 && Collections.frequency(listTemp, listTemp.get(k)) > 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("SudokuSolver");
        solve = new Button("Solve");
        remove = new Button("Remove");
        solve.setOnAction(this);
        remove.setOnAction(this);
        solve.relocate(5, 230);
        remove.relocate(205, 230);
        layout.getChildren().addAll(solve, remove);
        for (int i = 0; i < 81; i++) {
            grid.add(new TextField("0"), j, k, 1, 1);
            k = k + 1;
            if (k == 9) {
                k = 0;
                j = j + 1;
            }
        }
        for (int i = 0; i < 9; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(30));
        }
        layout.getChildren().add(grid);
        Scene scene = new Scene(layout, 270, 260);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == solve) {
            for (Node node : grid.getChildren()) {
                listNum.add(Integer.parseInt(((TextField) node).getText()));
            }
            for (int i = 0; i < 81; i++){
                listTemp.add(listNum.get(i));
                count += 1;
                if(i != 0 && count%9 == 0){
                    listSudBoard.add(new ArrayList<>(listTemp));
                    listTemp.clear();
                }
            }
            SolvePuzzle();

            for(int i = 0; i <9; i++){
                for(int j = 0; j < 9; j++){
                    listTemp2.add(listSudBoard.get(i).get(j));
                }
            }
            int i = 0;
            for (Node node : grid.getChildren()){
                ((TextField) node).setText((listTemp2.get(i)).toString());
                i +=1;
            }
        }
            if (event.getSource() == remove) {
                if(layout.getChildren().contains(grid)){
                    for (Node node : grid.getChildren()) {
                        ((TextField) node).setText("0");
                    }
                }
            }
        }
    }