package com.example.typeracer;


/* Typeracer app */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;


public class HelloApplication extends Application {
    //the text which the user has to type display
    public static String toTypeStr;
    @FXML public static TextArea toType;

    //Words Per Minute (typing speed) display
    //calculated using totalKeyStrokes and correctKeyStrokes
    @FXML public static Label wpmCounter;
    public static int totalKeyStrokes;
    public static int correctKeyStrokes;
    //The current text #display
    public static int textCounter;
    @FXML public static Label nowAtTextNo;
    //displaying key value
    @FXML public static Label keyValue;
    //TextField to allow the user to type
    @FXML public static TextInputBox textInputBox;
    //root stack pane
    @FXML public static StackPane root;
    //keyboard display (DigitalKeyboard class extends GridPane)
    @FXML public static DigitalKeyboard digitalKeyboard;
    @FXML public static StackPane topLabels;
    @FXML public static VBox menu;
    //button to go to the next text
    @FXML public static Button nextText;
    //button to clear what's currently written (restart current text)
    @FXML public static Button reset;

    public static boolean hasStartedTyping;
    public static boolean hasFinishedTyping;
    public static double startTime;
    public static ArrayList<String> allTexts;
    public static int textCount;
    public static ArrayList<String> unhandledKeys;

    private static ArrayList<String> readTextsFile(String fileName) throws IOException{

        //Reads the Texts.txt file
        //and copies every line as a String into the allTexts ArrayList

        ArrayList<String> texts= new ArrayList<>();
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            texts.add(line);
        }
        scanner.close();
        textCount= texts.size();
        return texts;
    }
    public static String getRandomText() throws IOException {

        //returns a random text from allTexts (except line 1)
        //then, removes text that's been typed
        //if only 1 text left, returns line 1

        int textsLeft= allTexts.size();
        if(textsLeft< 2){
            return allTexts.get(0);
        }
        Random rand = new Random();
        int selectedInt= rand.nextInt(1, allTexts.size());
        String selected= allTexts.get(selectedInt);
        allTexts.remove(selectedInt);
        return selected;
    }

    public static void setNewText() throws IOException {

        //resets everything to allow to type new text

        totalKeyStrokes = 0;
        correctKeyStrokes= 0;
        hasStartedTyping= false;
        hasFinishedTyping= false;

        toTypeStr= getRandomText();
        toType.setText(toTypeStr);
        textInputBox.setText("");
        textInputBox.setEditable(true);
        textInputBox.setStyle("-fx-background-color: rgba(255,255,255,1.0);");
        nextText.setDisable(true);
        reset.setDisable(true);
    }
    public static void setKeyboard(){
        digitalKeyboard= new DigitalKeyboard();

    }
    public void setUnhandledKeys(){
        unhandledKeys= new ArrayList<>();
        for(int i=1; i< 13; i++){
            unhandledKeys.add("F"+i);

        }
        unhandledKeys.add("TAB");unhandledKeys.add("PRINT_SC");unhandledKeys.add("PAGE_UP");unhandledKeys.add("PAGE_DOWN");
        unhandledKeys.add("HOME");unhandledKeys.add("END");unhandledKeys.add("INSERT");unhandledKeys.add("UP");
        unhandledKeys.add("DOWN");unhandledKeys.add("LEFT");unhandledKeys.add("RIGHT");unhandledKeys.add("ESCAPE");
        unhandledKeys.add("PRINTSCREEN");unhandledKeys.add("SCROLL_LOCK");unhandledKeys.add("PAUSE");unhandledKeys.add("CONTROL");

    }

    public static void updateTextCountLabel(){
        nowAtTextNo.setText("Now at text #"+ textCounter+" of "+textCount);

    }
    public static void updateKeyValueLabel(String kV){
        keyValue.setText("Key value: "+kV);
        if(kV.equals("Not handled")) keyValue.setStyle("-fx-background-color:#f07070");
        else keyValue.setStyle("-fx-background-color: #f4f4f4");
    }
    public static void updateWpm(int wpm, int accuracy){
        wpmCounter.setText(wpm+" @ "+accuracy);
    }

    @Override
    public void start(Stage stage) throws IOException {
        //setting allTexts to the Strings from Texts.txt
        //System.out.println(new File("").getAbsolutePath());
        setUnhandledKeys();
        allTexts= readTextsFile(new File("").getAbsolutePath()+"\\target\\classes\\com\\example\\typeracer\\Texts.txt");
        toTypeStr= getRandomText();
        textCounter= 1;
        setKeyboard();
        //initializing every component
        nowAtTextNo= new Label();
        toType= new TextArea(toTypeStr);
        toType.setWrapText(true);
        toType.setMaxHeight(200);
        toType.setPadding(new Insets(5));
        toType.setFont(new Font("Times New Roman", 30));
        toType.setEditable(false);
        updateTextCountLabel();
        nowAtTextNo.setFont(new Font("Times New Roman", 20));
        keyValue= new Label();
        updateKeyValueLabel(-1+"");
        keyValue.setFont(new Font("Times New Roman", 20));
        reset= new Button("RESET");
        reset.setDisable(true);
        reset.setFont(new Font("Times New Roman", 20));
        nextText = new Button("NEW TEXT");
        nextText.setDisable(true);
        nextText.setFont(new Font("Times New Roman", 20));
        textInputBox = new TextInputBox();

        root= new StackPane();
        topLabels= new StackPane();
        topLabels.getChildren().add(toType);
        wpmCounter= new Label("0 @ 100");
        wpmCounter.setAlignment(Pos.CENTER);
        wpmCounter.setFont(new Font("Times New Roman", 100));
        //adding elements to root pane
        menu= new VBox();
        menu.setAlignment(Pos.BOTTOM_LEFT);
        menu.setSpacing(10);
        menu.getChildren().add(nowAtTextNo);
        menu.getChildren().add(keyValue);
        menu.getChildren().add(reset);
        menu.getChildren().add(nextText);
        menu.getChildren().add(topLabels);
        menu.getChildren().add(textInputBox);
        root.getChildren().add(wpmCounter);
        root.getChildren().add(digitalKeyboard);
        root.getChildren().add(menu);
        root.setPadding(new Insets(20, 50, 50, 50));
        //finally, setting the scene
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Typeracer");
        stage.setScene(scene);
        stage.show();
        //setting the logic for the reset button
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //clears textField if user has not finished typing
                if(!hasFinishedTyping) {
                    textInputBox.setText("");
                    hasStartedTyping= false;
                    totalKeyStrokes=0;
                    correctKeyStrokes=0;
                    textInputBox.setStyle("-fx-background-color: rgba(255,255,255,1.0);");
                    updateWpm(0, 100);
                }
            }
        });
        //setting logic for nextText button
        nextText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //loads the next text if user had finished typing
                if(hasFinishedTyping){
                    try {
                        setNewText();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    textCounter++;
                    updateTextCountLabel();

                }
            }
        });

    }

    public static boolean checkHandled(String code){
        return !unhandledKeys.contains(code);
    }
    public static int unHighlightKeys(String eventCode){

        //this takes the input code from the key Event handler and
        //uses it to find the correct button (key)
        //un-highlights it.

        String key= transformCodeToKey(eventCode);
        for(int i=0; i< digitalKeyboard.getChildren().size();i++){
            Node node= digitalKeyboard.getChildren().get(i);
            if(node.getClass()!= GridPane.class){
                return -1;
            }
            GridPane row= (GridPane)digitalKeyboard.getChildren().get(i);
            for(int j=0; j< row.getChildren().size(); j++){
                if(row.getChildren().get(j).getClass()== Button.class){
                    Button button= (Button)row.getChildren().get(j);
                    if(key.equals(button.getText())) button.setStyle("-fx-background-color: rgba(255,255,255,1.0);");
                }

            }
        }

        return 1;

    }
    public static int highlightKeys(String eventCode){

        //this takes the input code from the key Event handler and
        //uses it to find the correct button (key)
        //highlights it.


        String key= transformCodeToKey(eventCode);
        for(int i=0; i< digitalKeyboard.getChildren().size();i++){
            Node node= digitalKeyboard.getChildren().get(i);
            if(node.getClass()!= GridPane.class){
                return -1;
            }
            GridPane row= (GridPane)digitalKeyboard.getChildren().get(i);
            for(int j=0; j< row.getChildren().size(); j++){
                if(row.getChildren().get(j).getClass()== Button.class){
                    Button button= (Button)row.getChildren().get(j);
                    if(key.equals(button.getText())) button.setStyle("-fx-background-color: rgba(233,91,255,0.44);");
                }

            }
        }

        return 1;

    }
    public static int toInt(String s){
        //this is used to turn the keyEvent to an integer value
        //to display a keyValue
        int code= 0;
        for(int i=0; i< s.length(); i++){
            code+= s.charAt(i);
        }
        return code;
    }
    public static String transformCodeToKey(String code){

        //some keyEvent codes don't match the keys on the digital keyboard (like 1 becomes DIGIT1)
        //purpose of this function is to convert form one to the other

        for(int i=0; i< 10; i++) {
            String str= "DIGIT"+i;
            if (str.equals(code)) return i+"";

        }
        if(code.equals("BACK_SPACE"))return "BACK";
        if(code.equals("EQUALS"))return "=";
        if(code.equals("BACK_QUOTE"))return "`";
        if(code.equals("MINUS"))return "-";
        if(code.equals("ESCAPE"))return "ESC";
        if(code.equals("QUOTE")) return "'";
        if(code.equals("CONTROL")) return "CTRL";
        if(code.equals("WINDOWS")) return "WIN";
        if(code.equals("SEMICOLON")) return ";";
        if(code.equals("ALT_GRAPH")) return "ALT";
        if(code.equals("OPEN_BRACKET")) return "[";
        if(code.equals("CLOSE_BRACKET")) return "]";
        if(code.equals("SLASH")) return "/";
        if(code.equals("COMMA"))return",";
        if(code.equals("PERIOD"))return ".";
        return code;
    }



    public static void main(String[] args) {
        launch();
    }
}
