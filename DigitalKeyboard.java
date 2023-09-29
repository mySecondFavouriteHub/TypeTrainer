package com.example.typeracer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class DigitalKeyboard extends GridPane {
    public DigitalKeyboard(){
        start();
    }
    public void start(){
        this.setAlignment(Pos.TOP_CENTER);

        //making a GridPane for each row of the keyboard

        GridPane row1= new GridPane();
        GridPane row2= new GridPane();
        GridPane row3= new GridPane();
        GridPane row4= new GridPane();
        GridPane row5= new GridPane();
        this.add(row1, 0, 1);
        this.add(row2, 0, 2);
        this.add(row3, 0, 3);
        this.add(row4, 0, 4);
        this.add(row5, 0, 5);

        //adding every key to the keyboard.

        row1.add(new Button("`"), 0, 1);
        for(int i=1; i< 10; i++){
            row1.add(new Button(""+i), i, 1);
        }
        row1.add(new Button("0"), 10, 1);
        row1.add(new Button("-"), 11, 1);
        row1.add(new Button("="), 12, 1);
        row1.add(new Button("BACK"), 13, 1);
        row2.add(new Button("TAB"), 0, 2);
        row2.add(new Button("Q"), 1, 2);
        row2.add(new Button("W"), 2, 2);
        row2.add(new Button("E"), 3, 2);
        row2.add(new Button("R"), 4, 2);
        row2.add(new Button("T"), 5, 2);
        row2.add(new Button("Y"), 6, 2);
        row2.add(new Button("U"), 7, 2);
        row2.add(new Button("I"), 8, 2);
        row2.add(new Button("O"), 9, 2);
        row2.add(new Button("P"), 10, 2);
        row2.add(new Button("["), 11, 2);
        row2.add(new Button("]"), 12, 2);
        row3.add(new Button("CAPS"), 0, 3);
        row3.add(new Button("A"), 1, 3);
        row3.add(new Button("S"), 2, 3);
        row3.add(new Button("D"), 3, 3);
        row3.add(new Button("F"), 4, 3);
        row3.add(new Button("G"), 5, 3);
        row3.add(new Button("H"), 6, 3);
        row3.add(new Button("J"), 7, 3);
        row3.add(new Button("K"), 8, 3);
        row3.add(new Button("L"), 9, 3);
        row3.add(new Button(";"), 10, 3);
        row3.add(new Button("'"), 11, 3);
        row3.add(new Button("ENTER"), 12, 3);
        row4.add(new Button("SHIFT"), 0, 4);
        row4.add(new Button("Z"), 1, 4);
        row4.add(new Button("X"), 2, 4);
        row4.add(new Button("C"), 3, 4);
        row4.add(new Button("V"), 4, 4);
        row4.add(new Button("B"), 5, 4);
        row4.add(new Button("N"), 6, 4);
        row4.add(new Button("M"), 7, 4);
        row4.add(new Button(","), 8, 4);
        row4.add(new Button("."), 9, 4);
        row4.add(new Button("/"), 10, 4);
        row4.add(new Button("SHIFT"), 11, 4);
        row5.add(new Button("CTRL"), 0, 5);
        row5.add(new Button("WIN"), 1, 5);
        row5.add(new Button("ALT"), 2, 5);
        row5.add(new Button("SPACE"), 3, 5);
        row5.add(new Button("ALT"), 4, 5);
        row5.add(new Button("CTRL"), 5, 5);


        //setting the style of each button and making every row align with each other.
        int regularPadding = 19;
        for(int i=0; i< this.getChildren().size();i++){


            if(this.getChildren().get(i).getClass()== GridPane.class){
                GridPane row= (GridPane)this.getChildren().get(i);
                row.setHgap(2.0d);
                row.setVgap(1.0d);
                for(int j=0; j< row.getChildren().size(); j++){

                    if(row.getChildren().get(j).getClass()== Button.class){
                        Button b= (Button)row.getChildren().get(j);
                        b.setStyle("-fx-background-color: rgba(255,255,255,1.0);");
                        b.setFont(new Font("Times New Roman", 16));
                        b.setPadding(new Insets(regularPadding));

                        if(b.getText().equals("TAB")) b.setPadding(new Insets(regularPadding, regularPadding*2.1, regularPadding, regularPadding*2.1));
                        else if(b.getText().equals("BACK")) b.setPadding(new Insets(regularPadding, regularPadding*1.25, regularPadding, regularPadding*1.25));
                        else if(b.getText().equals("SHIFT")) b.setPadding(new Insets(regularPadding, regularPadding*1.65, regularPadding, regularPadding*1.7));
                        else if(b.getText().equals("SPACE")) b.setPadding(new Insets(regularPadding, regularPadding*7.5, regularPadding, regularPadding*7.5));

                    }


                }

            }
        }
    }
}
