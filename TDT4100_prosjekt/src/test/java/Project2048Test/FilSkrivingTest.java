package Project2048Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project2048.Board;
import project2048.FilSkriving;
import project2048.Tile;

public class FilSkrivingTest {
    Board b1 = new Board();
    FilSkriving f1;
    
    @BeforeEach
    public void name() {

        f1 = new FilSkriving();
        b1.createCustomizeBoard(
            1, 2, 3, 4, 
            5, 6, 7, 8, 
            9, 10, 11, 12, 
            13, 14, 15, 16);
    }

    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når player ikke er skrevet inn")
    public void writeGameToFileTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("testfil.txt", b1, "", b1.getScore()+"");
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når player er null")
    public void writeGameToFileTest2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("testfil.txt", b1, null, b1.getScore()+"");
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når score ikke er skrevet inn")
    public void writeGameToFileTest3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("testfil.txt", b1, "Oskar", "");
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker score ikke er tall")
    public void writeGameToFileTest4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("testfil.txt", b1, "Thomas", "hei");
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når score er null")
    public void writeGameToFileTest5() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("testfil.txt", b1, "Fredrik", null);
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når filnavn ikke er skrevet inn")
    public void writeGameToFileTest6() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile("", b1, "Tore", b1.getScore()+"");
        }, "forventet IllegalArgumentException");
    }
    @Test
    @DisplayName("sjekker at feilhåndtering i writeGameToFile funker når filnavn er null")
    public void writeGameToFileTest7() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.writeGameToFile(null, b1, "Jan", b1.getScore()+"");
        }, "forventet IllegalArgumentException");
    }






    @Test
    @DisplayName("Sjekker at den finner en spiller i en fil.")
    public void nameInListTest() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "JanBanan", b1.getScore()+"");
        Assertions.assertTrue(f1.nameInList("testfil.txt", "JanBanan"), "forventet true, men var false");
    }
    
    @Test
    @DisplayName("Sjekker at hvis en fil ikke finnes blir exceptions kalt")
    public void nameInListTest2() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Arne", b1.getScore()+"");
        Assertions.assertThrows(FileNotFoundException.class, ()->{
            f1.nameInList("foo", "Arne");
        }, "Her skulle FileNotFountEcxeption blitt kastet");
    }
    @Test
    @DisplayName("Sjekker at hvis navnet er null blir exceptions kastet")
    public void nameInListTest3() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Johanne", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            f1.nameInList("testfil.txt", null);
        }, "Her skulle IllegalArgumentException blitt kastet");
    }
    @Test
    @DisplayName("Sjekker at hvis navnet ikke er gyldig blir exceptions kastet")
    public void nameInListTest4() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Frida", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            f1.nameInList("testfil.txt", "");
        }, "Her skulle IllegalArgumentException blitt kastet");
    }






    @Test
    @DisplayName("Sjekker at riktig informasjon blir skrevet til fil og den samme informasjonen kommer ut igjen.")
    public void readFromFileTest() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Elin",b1.getScore()+"");
        ArrayList<ArrayList<Tile>> temp = f1.readGameFromFile("testfil.txt", "Elin");
        Board b2 = new Board(temp, Integer.parseInt(f1.getScore()));
        Assertions.assertEquals(b1.getBoardValues(), b2.getBoardValues(), "Du fikk ikke rikitg informasjon");
    }

    @Test
    @DisplayName("Sjekker at feilhåndtering blir utført riktig når player er ikke skrevet")
    public void readFromFileTest2() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Martine", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.readGameFromFile("testfil", "");
        });
    }
    @Test
    @DisplayName("Sjekker at feilhåndtering blir utført riktig når player er null")
    public void readFromFileTest3() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Tale", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.readGameFromFile("testfil", null);
        });
    }
    @Test
    @DisplayName("Sjekker at feilhåndtering blir utført når filnavn ikke er spesifisert")
    public void readFromFileTest4() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Thale", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.readGameFromFile("", "Thale");
        });
    }
    @Test
    @DisplayName("Sjekker at feilhåndtering blir utført når filnavn er null")
    public void readFromFileTest5() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Lykke", b1.getScore()+"");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            f1.readGameFromFile(null, "Lykke");
        });
    }
    @Test
    @DisplayName("Sjekker at funksjoner returnerer null hvis feil filnavn blir lagt inn")
    public void readFromFileTest6() throws FileNotFoundException {
        f1.writeGameToFile("testfil.txt", b1, "Ida", b1.getScore()+"");
        Assertions.assertEquals(null, f1.readGameFromFile("testfil", "Ida"));
    }



    @Test
    @DisplayName("Sjekker at overskriving av lagring funker")
    public void overWriteTest() {
        f1.writeGameToFile("overwriteTest.txt", b1, "Max", "400");
        Board b2 = new Board();
        b2.createCustomizeBoard(
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1);

        f1.overWrite("overwriteTest.txt", b2, "Max", "500");
        Board output = new Board(f1.readGameFromFile("overwriteTest.txt", "Max"), Integer.parseInt(f1.getScore()));
        Assertions.assertEquals(b2.getBoardValues(), output.getBoardValues());
    }


    @Test
    @DisplayName("Sjekker at lagring av highscore funker som forventet.")
    public void highScoreTest() {
        f1.saveHighScoreToFile("highScoreTest.txt", "100");
        int expectedValue = 100;
        Assertions.assertEquals(expectedValue, f1.readHighScoreFromFile("highScoreTest.txt"));
    }
}

