package Project2048Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project2048.Board;

public class BoardTest {
    Board b1;

    @BeforeEach
    public void setBoard() {
        b1 = new Board();
    }

    @Test
    @DisplayName("Sjekker at konstruktører funker som forventet.")
    public void constructorTest() {
        //? Sjekker at konstruktør lager et 4x4 brett
        Assertions.assertEquals(4, b1.getBoard().size());
        for (int i = 0;i<3;i++)
            Assertions.assertEquals(4, b1.getBoard().get(i).size());
        
        int numberOfTiles = 0;
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                if (b1.getTile(i, j).getTileValue() != 0){
                    numberOfTiles++;
                } 
            }
        }
        assertEquals(1, numberOfTiles, "Det skulle kun spawne en brikke på brettet.");
    }

    @Test
    @DisplayName("Sjekker at brikker flytter og slår seg sammen til venstre")
    public void leftMoveTest() {
        b1.createCustomizeBoard(
            2,4,4,2,
            2,0,0,2,
            2,2,4,2,
            0,0,0,2);

        b1.left(true, false);
            
        Board expectedBoard = new Board();
        expectedBoard.createCustomizeBoard(
            2,8,2,0,
            4,0,0,0,
            4,4,2,0,
            2,0,0,0);
            
        Assertions.assertEquals(expectedBoard.getBoardValues(), b1.getBoardValues(), "Brettet skulle ikke sett sånn ut");
    }
    @Test
    @DisplayName("Sjekker at brikker flytter og slår seg sammen til høyre")
    public void rightMoveTest() {
        b1.createCustomizeBoard(
            2,4,4,2,
            2,0,0,2,
            2,2,4,2,
            0,0,0,2);

    b1.right(true, false);

        Board expectedBoard = new Board();
        expectedBoard.createCustomizeBoard(
            0,2,8,2,
            0,0,0,4,
            0,4,4,2,
            0,0,0,2);
            Assertions.assertEquals(expectedBoard.getBoardValues(), b1.getBoardValues(), "Brettet skulle ikke sett sånn ut");
    }

    @Test
    @DisplayName("Sjekker at brikker flytter og slår seg sammen oppover")
    public void upMoveTest() {
        b1.createCustomizeBoard(
            2,4,4,2,
            2,0,0,2,
            2,2,4,2,
            0,0,0,2);

    b1.up(true, false);

        Board expectedBoard = new Board();
        expectedBoard.createCustomizeBoard(
            4,4,8,4,
            2,2,0,4,
            0,0,0,0,
            0,0,0,0);

            Assertions.assertEquals(expectedBoard.getBoardValues(), b1.getBoardValues(), "Brettet skulle ikke sett sånn ut");
    }
    @Test
    @DisplayName("Sjekker at brikker flytter og slår seg sammen nedover")
    public void downMoveTest() {
        b1.createCustomizeBoard(
            2,4,4,2,
            2,0,0,2,
            2,2,4,2,
            0,0,0,2);

    b1.down(true, false);

        Board expectedBoard = new Board();
        expectedBoard.createCustomizeBoard(
            0,0,0,0,
            0,0,0,0,
            2,4,0,4,
            4,2,8,4);

            Assertions.assertEquals(expectedBoard.getBoardValues(), b1.getBoardValues(), "Brettet skulle ikke sett sånn ut");
    }

    @Test
    @DisplayName("Sjekker at poeng blir oppdatert når brikker smelter sammen")
    public void pointTester() {
        b1.createCustomizeBoard(
            2,2,2,2,
            0,2,0,2,
            2,0,0,2,
            4,4,2,2);

    b1.right(true, true);
    Assertions.assertEquals(28, b1.getScore(), "Scoren skulle vært 28");
    }
    @Test
    @DisplayName("Sjekker at man ikke får poeng når ingen brikker smelter sammen.")
    public void pointTester2() {
        b1.createCustomizeBoard(
            0,4,0,2,
            0,0,0,0,
            32,0,0,16,
            0,0,4,2);

    b1.down(true,true);
    Assertions.assertEquals(0, b1.getScore(), "Her skulle du ikke fått noen poeng");
    }

    @Test
    @DisplayName("Sjekker at ny brikke spawner når det er bevegelse på brettet.")
    public void pieceSpawnTest() {
        b1.createCustomizeBoard(
            0,0,2,2,
            0,2,0,2,
            2,0,0,2,
            4,4,2,2);

        b1.right(true, true);
        
        int numberOfTiles = 0;
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                if (b1.getTile(i, j).getTileValue() != 0){
                    numberOfTiles++;
                } 
            }
        }
        Assertions.assertEquals(6, numberOfTiles, "Det skulle vært 6 brikker på brettet");
    }

    @Test
    @DisplayName("Sjekker at det ikke spawner en brikke når det ikke skjer bevegelse på brettet.")
    public void pieceSpawnTest2() {
        b1.createCustomizeBoard(
            0,0,0,4,
            0,0,0,8,
            2,2,16,16,
            2048,8,4,2);
        b1.down(true,true);
        
        int numberOfTiles = 0;
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                if (b1.getTile(i, j).getTileValue() != 0){
                    numberOfTiles++;
                } 
            }
        }
        Assertions.assertEquals(10, numberOfTiles);
    }

    @Test 
    @DisplayName("Sjekker at spillet er over hvis det ikke er flere lovlige trekk")
    public void gameOverCheckTest() {
        b1.createFullBoard();
        b1.right(true,true);
        Assertions.assertEquals(false, b1.getGamestatus(), "Spillet skulle vært over.");
    }

    @Test
    @DisplayName("Sjekker at spillet ikke er over når brettet er fullt, men det er flere lovlige trekk")
    public void gameOverCheckTest2() {
        b1.createCustomizeBoard(
            4, 4, 4, 4, 
            4, 4, 4, 4, 
            4, 4, 4, 4, 
            4, 4, 4, 4);
        b1.down(true, true);
        Assertions.assertEquals(true, b1.getGamestatus(), "Her var brettet fullt, men det var flere lovlige trekk igjen.");
    }
    }
