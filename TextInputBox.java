package com.example.typeracer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

import java.io.IOException;

import static com.example.typeracer.HelloApplication.*;

public class TextInputBox extends TextField {
    public TextInputBox(){
        start();
    }
    public void start(){
        this.setPadding(new Insets(5,5,5,5));
        this.setFont(new Font("Times New Roman", 30));

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //if user hasn't started typing, starts timer on first button press
                if(!hasStartedTyping){
                    startTime= System.currentTimeMillis()/1000.0d;
                    hasStartedTyping= true;
                }
                //if user finished typing, allows to load next text by pressing ENTER
                else if(hasFinishedTyping){
                    if(event.getCode().toString().equals("ENTER")) {
                        try {
                            setNewText();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        textCounter++;
                        updateTextCountLabel();
                    }
                }
                //if user hasn't finished typing, resets the text on ENTER press
                else{
                    if(event.getCode().toString().equals("ENTER")) {
                        setText("");
                        hasStartedTyping=false;
                        //startTime= System.currentTimeMillis()/1000.0d;
                        totalKeyStrokes=0;
                        correctKeyStrokes=0;
                        setStyle("-fx-background-color: rgba(255,255,255,1.0);");
                        updateWpm(0, 100);
                    }
                }
                //for every key press, highlights the corresponding key on the digital keyboard
                highlightKeys(event.getCode().toString());
                //and updates keyValue label if keyValue is Handled
                if(checkHandled(event.getCode().toString())) updateKeyValueLabel(toInt(event.getCode().toString())+"");
                else updateKeyValueLabel("Not handled");


            }
        });

        this.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String s="";
                int len= getText().length();
                if(len> 0)reset.setDisable(false);
                else reset.setDisable(true);
                String str= getText();
                if(len<= toTypeStr.length()) s= toTypeStr.substring(0, len);
                totalKeyStrokes++;

                //if the text typed by user doesn't match the given text
                //highlight the text field in red to indicate error

                if(!s.equals(str)){
                    setStyle("-fx-background-color: rgb(223,91,91);");
                }
                //if user finished typing, and the input matches the given text
                //highlight the field green to indicate sueccess
                else if(len == toTypeStr.length()){
                    setStyle("-fx-background-color: rgb(47,173,4);");
                    setEditable(false);
                    reset.setDisable(true);
                    hasFinishedTyping=true;
                    nextText.setDisable(false);
                    correctKeyStrokes++;

                }
                //otherwise, just keep the textField white
                else {
                    setStyle("-fx-background-color: rgba(255,255,255,1.0);");
                    correctKeyStrokes++;
                }
                //with every key typed, keep track of the WPM and the accuracy using the following lines of code:
                float timeElapsedSeconds=(float)(System.currentTimeMillis()/1000.0-startTime);
                float timeElapsedMinutes= (float)(timeElapsedSeconds/60.0);
                if(!hasFinishedTyping){
                    updateWpm((int)((len/5.0)/timeElapsedMinutes), (int)(100*((float) correctKeyStrokes/ totalKeyStrokes)));
                }

            }
        });

        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //when a key is released, remove highlighting from it
                unHighlightKeys(event.getCode().toString());
            }
        });

    }
}
