package project2048;

import java.util.ArrayList;
import java.util.Collections;
//TODO teste hver enkelt klasse for seg
//TODO Exception in thread "InvokeLaterDispatcher" java.lang.RuntimeException: Main Java thread is detached. 
//TODO Sjekke hva denne feilen kommer av
import java.util.List;
import java.util.stream.Collectors;

public class Board{
    //*----------------------------------------
    //* Tilstanden til spillet
    //*----------------------------------------

    private ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
    private RowComparator rowComparator = new RowComparator();
    private boolean gameStatus = true;
    private int score = 0;

    //*----------------------------------------
    //* Konstruktører 
    //*----------------------------------------
    
    //? Standar konstruktør 
    public Board(){
        for (int i = 0;i<4;i++){
            ArrayList<Tile> a = new ArrayList<Tile>();
            for (int j = 0;j<4;j++){
                a.add(new Tile());
            }
            board.add(a);
        }
        setPiece(((int)(Math.random()*(4))), ((int)(Math.random()*(4))));
    }
    
    //? Konstruktør for å sjekke om spillet er over
    private Board(Board brett){
        for (int i = 0;i<4;i++){
            ArrayList<Tile> a = new ArrayList<Tile>();
            for (int j = 0; j<4;j++){
                a.add(GenerateSetTile(brett.getTileValue(i, j)));
            }
            board.add(a);
        }
    }
    
    //? Kontruktør for å laste inn brett fra fil
    public Board(ArrayList<ArrayList<Tile>> board, int score) {
        if (score < 0) throw new IllegalArgumentException("Kan ikke ta inn en score som er negativ");
        this.board = board;
        this.score = score;
    }
    
    //*----------------------------------------
    //* Gettere
    //*----------------------------------------
    
    //? Returnerer gamestatus
    public boolean getGamestatus() {
        return this.gameStatus;
    }

    //? Returnerer verdien til en Tile
    public int getTileValue(int x, int y) { 
        if (!coordinateValidation(x, y)) 
            throw new IllegalArgumentException("Kan ikke se en brikke som ikke er på brettet.");
        return board.get(x).get(y).getTileValue();
    }

    //? Returnerer en enkelt tile
    public Tile getTile(int x, int y) { //? Returnere en Tile basert på input.
        if (!coordinateValidation(x, y)) 
            throw new IllegalArgumentException("Kan ikke se en brikke som ikke er på brettet.");
        return board.get(3-y).get(x);
    }

    //? Returnerer hele brettet.
    public ArrayList<ArrayList<Tile>> getBoard() { 
        return this.board;
    }

    public List<List<Integer>> getBoardValues() {
        return board.stream()
            .map(row -> row.stream()
                .map(tile -> tile.getTileValue())
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    //? Returnerer true dersom brettet er fult. False ellers.
    private boolean isBoardFull() { 
        for (ArrayList<Tile> arrayList : board) {
            for (Tile tile : arrayList) {
                if (tile.getTileValue() == 0)return false;
            }
        }
        return true;
    }

    //? Returerer score
    public int getScore() {
        return this.score;
    }

    //*----------------------------------------
    //* Validering
    //*----------------------------------------

    private boolean coordinateValidation(int x, int y) {
        if (x < 0 || y < 0 || x >= board.size() || y >= board.get(0).size()) return false;
        return true;
    }

    private void boardValidation(ArrayList<ArrayList<Tile>> brett) {
        if (brett.size() != 4) throw new IllegalArgumentException("Feil størrelse på brettet.");
        for (int i = 0; i<3;i++){
            if (brett.get(i).size() != 4) throw new IllegalArgumentException("Feil størrelse på brettet.");
            for (int j = 0; j<3; j++) if (brett.get(i).get(j).getTileValue() < 0) throw new IllegalArgumentException("Feil størrelse på brettet.");
        }
    }
    
    //*----------------------------------------
    //* Manipulering av brettet og brikker
    //*----------------------------------------

    //? Restarter spillet og setter score til 0
    public void resetGame() {
        this.gameStatus = true;
        this.score = 0;
        board.stream().forEach(arrayList -> arrayList
            .stream().forEach(tile -> tile.updateTileValue(0)));
        
        setPiece(((int)(Math.random()*(4))), ((int)(Math.random()*(4))));
    }

    //? Lager et fullt brett som ikke funker for å sjekke hvordan et tapt spill håndteres.
    public void createFullBoard() {
        createCustomizeBoard(
            2, 4, 8, 16, 
            4, 8, 16, 32, 
            8, 16, 32, 64, 
            16, 32, 64, 128);
    }

    //? Generer en ny tile med en gitt verdi
    private Tile GenerateSetTile(int value) {
        if (value < 0) throw new IllegalArgumentException("Kan ikke sette en negativ verdi til en brikke.");
        return new Tile(value);
    }

    //? Generer en ny tile med en radom verdi - 10% sjanse for 4 og 90% sjanse for 2
    private Tile generatePiece() {
        if ((int)(Math.random()*(10)+1)==1)return new Tile(4);
        return new Tile(2);
    }
    
    //? Plasserer en brikke et gitt sted på brettet.
    private void setPiece(int x, int y) { //? Setter en Tile på brettet på et valgt sted.
        if (!coordinateValidation(x, y)) throw new IllegalArgumentException("Kan ikke plassere en brikke utenfor brettet");
        this.board.get(3-y).set(x, generatePiece());
    }

    //? Legger til en brikke et tilfeldig sted på brettet hvis det er ledig. 
    //? Denne blir bare kalt hvis det er ledig plass på brettet.
    private void spawnRandomPiece() { 
        boolean a = true;
        while (a == true){
            int y = (int)(Math.random()*(board.size()));  
            int x = (int)(Math.random()*(board.get(0).size()));  
            if (getTile(x, y).getTileValue() == 0){
                setPiece(x,y);
                a = false;
            }
        }
    }

    //? Lager et egendefinert brett.
    public void createCustomizeBoard(
        int x0y0, int x1y0, int x2y0, int x3y0, 
        int x0y1, int x1y1, int x2y1, int x3y1, 
        int x0y2, int x1y2, int x2y2, int x3y2, 
        int x0y3, int x1y3, int x2y3, int x3y3){

        List<Integer> random = new ArrayList<>(List.of(
        x0y0, x1y0, x2y0, x3y0, 
        x0y1, x1y1, x2y1, x3y1, 
        x0y2, x1y2, x2y2, x3y2, 
        x0y3, x1y3, x2y3, x3y3));

        int counter = 0;
        for (int i = 0;i<4;i++){
            for (int j = 0;j<4;j++){
                this.board.get(i).set(j, new Tile(random.get(counter)));
                counter++;
            }
        }
    }

    //*----------------------------------------
    //* Endringmetoder
    //*----------------------------------------

    //? Sjekker om spillet er over og setter gamestatus til false om det er det 
    private void gameOverCheck() {
        Board temp = new Board(this);
        temp.right(false, true);
        temp.left(false, true);
        temp.up(false, true);
        temp.down(false, true);

        int differences = 0;
        for (int i = 0; i<4;i++) {
            for (int j = 0; j<4;j++) {
                if(this.getTileValue(i, j) != temp.getTileValue(i, j)) differences++;
            }
        }
        if (differences == 0) this.gameStatus = false;
    }

    //? Oppdaterer score til spillet
    private void updateScore(int n) {
        if (n < 0)throw new IllegalArgumentException("Det går ikke å tape poeng.");
        score += n;
    }

    //*----------------------------------------
    //* Endringsmetoder - Flytting av brikker
    //*----------------------------------------

    //? Sender alt til høyre. Legger sammen det som skal legges sammen. 
    //? Ber om å spawne ny brikke hvis det har skjedd en bevegelse på brettet.
    //? Sjekker om spillet er over hvis brettet er fullt
    public void right(boolean gameOverCheck, boolean spawnNewPiece) {
        int moves = 0;
        for (ArrayList<Tile> arrayList : board) {
            ArrayList<Tile> tempRow = new ArrayList<Tile>(arrayList);
            Collections.sort(arrayList, rowComparator);
            for (int i = arrayList.size()-1;i>0;i--){
                if (arrayList.get(i).getTileValue() != 0 & arrayList.get(i).getTileValue() == arrayList.get(i-1).getTileValue()){
                    arrayList.get(i).updateTileValue(arrayList.get(i).getTileValue()*2);
                    arrayList.get(i-1).updateTileValue(0);
                    updateScore(arrayList.get(i).getTileValue());
                    moves++;
                }
            }
            Collections.sort(arrayList, rowComparator);
            if (!tempRow.equals(arrayList)) moves++;
        }
        if (moves > 0)if (spawnNewPiece)spawnRandomPiece();
        if (moves == 0) 
            if (gameOverCheck)
                if (isBoardFull()) gameOverCheck();
    }
    
    //? Reverserer brettet og bruker right for å flytte brikker. Revereserer tilbake.
    public void left(boolean gameOverCheck, boolean spawnNewPiece) {
        this.board = reverseRows(board);
        right(gameOverCheck, spawnNewPiece);
        this.board = reverseRows(board);
    }

    //? Roterer brettet og bruker right for å flytte brikker. Roterer tilbake
    public void up(boolean gameOverCheck, boolean spawnNewPiece) {
        this.board = rotateClockwise(board);
        right(gameOverCheck, spawnNewPiece);
        this.board = rotateAntiClockwise(board);
    }
    
    //? Roterer brettet og bruker right for å flytte brikker. Roterer tilbake
    public void down(boolean gameOverCheck, boolean spawnNewPiece) {
        this.board = rotateAntiClockwise(board);
        right(gameOverCheck, spawnNewPiece);
        this.board = rotateClockwise(board);
    }

    //*----------------------------------------
    //* Rotering klokkerettning - Interne metoder
    //*----------------------------------------

    //? Reverserer radene
    private ArrayList<ArrayList<Tile>> reverseRows(ArrayList<ArrayList<Tile>> brett) {
        boardValidation(brett);
        brett.stream().forEach(arrayList -> Collections.reverse(arrayList));
        return brett;
    }

    //? Transponerer 2d array
    private ArrayList<ArrayList<Tile>> transposeList(ArrayList<ArrayList<Tile>> brett) {
        boardValidation(brett);
        ArrayList<ArrayList<Tile>> tempBoard = brett;
        for (int i = 0; i<board.size();i++){
            for (int j = i; j < board.size();j++){
                Tile temp = brett.get(i).get(j);
                tempBoard.get(i).set(j, board.get(j).get(i));
                tempBoard.get(j).set(i, temp);
            }
        }
        return tempBoard;
    }

//? Roterer brett med klokka
    private ArrayList<ArrayList<Tile>> rotateClockwise(ArrayList<ArrayList<Tile>> brett) {
        boardValidation(brett);
        return reverseRows(transposeList(brett));
    }

    //*----------------------------------------
    //* Rotering mot klokka - Interne metoder
    //*----------------------------------------

    //? Reverserer kolonnene
    private ArrayList<ArrayList<Tile>> reverse_columns(ArrayList<ArrayList<Tile>> brett){
        boardValidation(brett);
        ArrayList<ArrayList<Tile>> tempBorad = brett;
        int k;
        for (int i = 0; i < brett.size(); i++){
            k = tempBorad.size() - 1;
            for (int j = 0; j < k; j++){
                Tile temp = tempBorad.get(j).get(i);
                tempBorad.get(j).set(i, tempBorad.get(k).get(i));
                tempBorad.get(j).set(i, tempBorad.get(k).get(i));
                tempBorad.get(k).set(i, temp);
                k--;
            }
        }
        return tempBorad;
    }

    //? Roterer brettet mot klokka.
    private ArrayList<ArrayList<Tile>> rotateAntiClockwise(ArrayList<ArrayList<Tile>> brett) {
        boardValidation(brett);
        return reverse_columns(transposeList(brett));
    }
    
    //*----------------------------------------
    //* Visualisering
    //*----------------------------------------

    @Override
    public String toString() {
        String s = "";
        for (ArrayList<Tile> arrayList : board) {
            ArrayList<Integer> b = new ArrayList<Integer>();
            for (Tile tile : arrayList) {
                b.add(tile.getTileValue());
            }
            s+=b;
            s += "\n";
        }
        s += "\n" + score;
        return "------------ \n" + s;
    }

    public String toString2(ArrayList<ArrayList<Tile>> brett) {
        boardValidation(brett);
        String s = "";
        for (ArrayList<Tile> arrayList : brett) {
            ArrayList<Integer> b = new ArrayList<Integer>();
            for (Tile tile : arrayList) {
                b.add(tile.getTileValue());
            }
            s+=b;
            s += "\n";
        }
        s += "\n" + score;
        return "------------ \n" + s;
    }
}