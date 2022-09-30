package project2048;

import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class GameController {

    //*----------------------------------------
    //* FXML elementer
    //*----------------------------------------

    @FXML private GridPane grid;
    @FXML private TextArea score, highscore;
    @FXML private TextField username;
    @FXML private Text scoreField, highScoreField, overSkrift;
    @FXML private Button restart, getGame, startButton;
    @FXML private AnchorPane pane;
    @FXML private Pane frontpage;
    
    //*----------------------------------------
    //* Objekter
    //*----------------------------------------

    Board b1 = new Board();
    FilSkriving fileWriter = new FilSkriving();
    
    //*----------------------------------------
    //* Movement
    //*----------------------------------------

    //? Kaller på flyttingsmetodene i board og oppdaterer skjermen. 
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()){
            case A -> b1.left(true,true);
            case D -> b1.right(true,true);
            case W -> b1.up(true,true);
            case S -> b1.down(true,true);
            case F -> b1.createFullBoard();
            case R -> newGame();
            case P -> frontpage.setOpacity(frontpage.getOpacity() == 1 ? 0: 1);
            default -> updateBoard();
        }
        updateBoard();
        gameOverCheck();
    }

    //*----------------------------------------
    //* Start Game
    //*----------------------------------------

    //? Starter et nytt spill hvis man trykker på new game. 
    public void newGame() {
        try {
            b1.resetGame();
            updateBoard();
        } catch (Exception e) {
            startGame();
            updateBoard();
        }
        highScoreField.setText(fileWriter.readHighScoreFromFile("highScore.txt")+ "");
    }

    //? Starter et nytt spill og får det opp på skjermen
    public void startGame() {
        frontpage.setOpacity(0);
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                    Label label = new Label();
                    label.setPrefSize(140.0, 140.0);
                    label.setStyle("-fx-background-color: " + b1.getTile(j, 3-i).getTileColor() + ";" + "-fx-font-size: 20px; -fx-alignment: center;");
                    label.setText(b1.getTileValue(i, j) + "");
                    grid.add(label, j, i);
            }
        }
    }
    
    //*----------------------------------------
    //* Update board
    //*----------------------------------------

    //? Oppdatere skjermen slik at brikkene har flyttet seg, score blir oppdatert og sjekker om det er ny highscore.
    private void updateBoard() {
        int index = 0;
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                grid.getChildren().get(index).setStyle("-fx-background-color: " + b1.getTile(j, 3-i).getTileColor() + "; -fx-font-size: 20px; -fx-alignment: center;");
                Node l = grid.getChildren().get(index);
                ((Labeled) l).setText(b1.getTileValue(i, j) + "");
                index++;
            }
        }
        showScore();
        updateHighScore();
    }
    
    //? Sjekker om det er ny highscore og oppdaterer filen og skjermen hvis det er det.
    private void updateHighScore() {
        int lastHighScore = fileWriter.readHighScoreFromFile("highScore.txt");
        if (b1.getScore() > lastHighScore){
            fileWriter.saveHighScoreToFile("highScore.txt",scoreField.getText());
            highScoreField.setText(b1.getScore()+"");
        }
    }
    
    //? Hvis spillet er over vil spiller få beskjed.
    private void gameOverCheck() {
    if (!b1.getGamestatus()){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Spillet er over");
        alert.setHeaderText("Din score ble: " + b1.getScore());
        alert.setContentText("Du kan prøve å nytt ved å trykke på R eller 'Start Game'.");
        alert.showAndWait();
    }
}

//? Oppdaterer score på skjermen
    private void showScore() {
        scoreField.setText(b1.getScore() + "");
    }

    //*----------------------------------------
    //* Filhåndtering - Lagring og lesing av spill
    //*----------------------------------------

    //? Skriver spillet til fil. Bruker får bekreftelse om at spillet er lagret. 
    //? Hvis brukernavnet er i lagringsfilen vil brukeren bli spurt om han vil overskrive filen.
    public void saveGame() throws FileNotFoundException {
        if (username.getText() == ""){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasjon");
            alert.setHeaderText("Kan ikke lagre et spill på ingenting");
            alert.setContentText("Du er nødt til å skrive noe som har noe tekst");
            alert.showAndWait();
        }
        else if (!fileWriter.nameInList("lagring.txt", username.getText())){
            fileWriter.writeGameToFile("lagring.txt",b1, username.getText(), b1.getScore() + "");
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasjon");
            alert.setHeaderText("Spillet er lagret");
            alert.setContentText("Du kan finne spillet ditt ved å skrive inn: " + username.getText() + " og trykke på Get Game.");
            alert.showAndWait();
        } 
        
        else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Informasjon");
            alert.setHeaderText("OBS OBS!");
            alert.setContentText("Det finnes allerede et spill som er lagret på: " + username.getText() + "\n Trykk på OK for å overskrive filen eller CANCEL for å avbryte.");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) 
                fileWriter.overWrite("lagring.txt", b1, username.getText(),b1.getScore()+"");
        }
    }
    
    //? Laster inn et spill fra fil og oppdaterer highscore. Bruker får beskjed om det ikke finnes noe spill som er lagret på gitt brukernavn.
    public void loadGame() {
        Board b2 = new Board(b1.getBoard(),b1.getScore());
        try {
            b1 = new Board(fileWriter.readGameFromFile("lagring.txt", username.getText()), Integer.parseInt(fileWriter.getScore()));
            highScoreField.setText(fileWriter.readHighScoreFromFile("highScore.txt")+ "");

            try {
                updateBoard();
            } 
            
            catch (Exception e) {
                startGame();
                updateBoard();
            }
        } 
        
        catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informasjon");
            alert.setHeaderText("OBS OBS!");
            if (username.getText() == "") alert.setContentText("Du må skrive inn noe");
            else alert.setContentText("Det finnes ikke noe spill som er lagret på " + username.getText());
            alert.showAndWait();

            b1 = new Board(b2.getBoard(), b2.getScore());
        }
    }
}
