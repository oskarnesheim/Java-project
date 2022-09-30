package project2048;

import java.util.ArrayList;

public interface FilLagringInterface {
    public void writeGameToFile(String filename, Board inBoard, String player, String score);
    public ArrayList<ArrayList<Tile>> readGameFromFile(String filename, String player);

    public void overWrite(String filename, Board board, String name, String score);

    public void saveHighScoreToFile(String filename, String score);
    public int readHighScoreFromFile(String filename);
}
