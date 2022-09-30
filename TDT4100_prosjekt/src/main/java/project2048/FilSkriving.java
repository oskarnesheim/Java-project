package project2048;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FilSkriving implements FilLagringInterface{
    //*----------------------------------------
    //* Tilstand
    //*----------------------------------------

    private String score;
    private String stringBoard;
    private HashMap<String, List<String>> map = new HashMap<>();

    //*----------------------------------------
    //* Gettere
    //*----------------------------------------

    //? Returerer score som en streng
    public String getScore() {
        return score;
    }    

    //? Forteller om navnet til en gitt spiller finnes i filen.
    public boolean nameInList(String filename, String player) throws FileNotFoundException {
        validation(player, filename, "100");

        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()){
                String[] gameList = scanner.nextLine().split(",");
                if(gameList[0].equals(player)) {
                    scanner.close();
                    return true;
                }
            }     
            } catch (Exception e) {
                throw new FileNotFoundException();
            }
        return false;
    }

    //*----------------------------------------
    //* Validering
    //*----------------------------------------

    private void validation(String player, String filename, String score) {
        if (player == null || score == null ||
            filename == null || filename.length() < 1 ||
            player.length() <1 || score.length() < 1||
            Integer.parseInt(score) < 0
            ) throw new IllegalArgumentException("Kan ikke bruke disse verdiene");
    }


    //*----------------------------------------
    //* Endringmetoder
    //*----------------------------------------




    //? Lager en streng som er verdien til alle 16 tiles med "-" mellom dem
    private void makeStringBoard(Board brett) {
        String temp = "";
        for (ArrayList<Tile> row : brett.getBoard()) {
            for (Tile tile : row) {
                temp += tile.getTileValue() + "-";
            }
        }
        this.stringBoard = temp;
    }

    //? Lager en Integer list basert på hva som kommer fra en fil.
    private List<Integer> makeIntegerList() {        
        String[] stringList = stringBoard.split("-",17);
        ArrayList<Integer> intList = new ArrayList<Integer>();
        int counter = 0;
        for (int i = 0;i<16;i++){
                intList.add(Integer.parseInt(stringList[counter]));
                counter++;
            }
        return intList;
        } 
    
    //? Lager en ny 2d array basert på string fra fil
    private ArrayList<ArrayList<Tile>> createBoard() {
        ArrayList<ArrayList<Tile>> new2dArray = new ArrayList<ArrayList<Tile>>();
        List<Integer> listOfIntegers = makeIntegerList();
        int counter = 0;
        for (int i = 0;i<4;i++){
            ArrayList<Tile> temp = new ArrayList<>();
            for (int j = 0;j<4;j++){
                Tile t = new Tile(listOfIntegers.get(counter));
                temp.add(t);
                counter++;
                }
            new2dArray.add(temp);
        }    
        return new2dArray;
    }

     //? Lager et Hashmap hvor navnet til en spiller er lagret med score og brettstatus
    private void createMap(String name, String score, String tiles) {
        validation(name, tiles, score);
        map.put(name, List.of(score, tiles));
    }

    //*----------------------------------------
    //* Skriving til fil
    //*----------------------------------------

    //? Skriver tilstanden til et spill til en fil.
    public void writeGameToFile(String filename, Board inBoard, String player, String score) {
        validation(player, filename, score);
        try {
            makeStringBoard(inBoard);
            PrintWriter writer = new PrintWriter(new FileWriter(new File(filename),true));
            writer.append(player + "," + score + "," + stringBoard + "\n");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new IllegalArgumentException("Filen finnes ikke");
        }
    }

    //? Denne metoden brukes til å overskrive en save som en koblet til en spiller/brukernavn
    public void overWrite(String filename,Board board, String name, String score) {
        validation(name, filename, score);
        makeStringBoard(board);
        scanFile(filename, name);
        clearFile(filename);
        updateGameAndWriteToFile(filename, name, score, this.stringBoard);
    }

    //? Sletter alt fra en fil
    private void clearFile(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.write("");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //? Scanner en fil og skriver alt inn i java
    private void scanFile(String filename, String player) {
        map.clear();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                createMap(lineInfo[0], lineInfo[1], lineInfo[2]);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Fant ikke filen " + filename);
        }
    }
    
    //? Sjekker om hashmap innholder spiller og skriver inn ny lagring til fil.
    private void updateGameAndWriteToFile(String filename,String name, String score, String tiles) {
        if (map.containsKey(name)){
            map.replace(name, map.get(name), List.of(score,tiles));
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(new File(filename),true));
            for (String names : map.keySet()) {
                writer.append(names + "," + map.get(names).get(0) + "," + map.get(names).get(1) + "\n");
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //*----------------------------------------
    //* Lesing fra fil
    //*----------------------------------------

    //? Returnerer en 2d liste basert på hva som leses fra filen. 
    public ArrayList<ArrayList<Tile>> readGameFromFile(String filename, String player) {
        if (player == null || player.length() < 1 || filename == null || filename.length() < 1) throw new IllegalArgumentException("Funker ikke med null som spiller.");
        map.clear();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] lineInfo = line.split(",");
                if (lineInfo[0].equals(player)){
                    this.score = lineInfo[1];
                    this.stringBoard = lineInfo[2];
                    scanner.close();
                    return createBoard();
                }    
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Fant ikke filen " + filename);
        }
        return null;
    }

    //*----------------------------------------
    //* Lagring av Highscore
    //*----------------------------------------

    //? Lagrer ny highscore til en fil
    public void saveHighScoreToFile(String filename, String score) {
        if (filename == null || filename.length() < 1 || Integer.parseInt(score) < 0) throw new IllegalArgumentException("Kan ikke ta inn en negativ score.");
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.write(score);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //? Leser highscore fra fil
    public int readHighScoreFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            int highScore = 0;
            if (scanner.hasNext()){
                highScore = Integer.parseInt(scanner.nextLine());
                scanner.close();
            }
            return highScore;
        } catch (Exception e) {
            throw new IllegalStateException("Fant ikke filen som du lette etter");
        }
    }
}