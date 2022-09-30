package project2048;

public class Tile {
    //*----------------------------------------
    //* Tilstander
    //*----------------------------------------

    private int tileValue;
    private String tileColor;

    //*----------------------------------------
    //* konstruktører
    //*----------------------------------------

    //? Denne setter en standar konstruktør
    public Tile(){
        this.tileValue = 0;
        this.tileColor = "#808080";    
    }

    //? Oppretter en til og setter en spesifik verdi og farge. Har satt øvre verdi til 10000
    public Tile(int tileValue){
        if (tileValue < 0 || tileValue > 10000) throw new IllegalArgumentException("Du kan ikke oprette en Tile med den verdien.");
        this.tileValue = tileValue;
        setTileColor();
    }

    //*----------------------------------------
    //* Gettere
    //*----------------------------------------

    //? Returerer verdien
    public int getTileValue() {
        return this.tileValue;
    }
    
    //? Returerer fargen
    public String getTileColor() {
        return this.tileColor;
    }

    //*----------------------------------------
    //* Endringsmetoder
    //*----------------------------------------

    //? Oppdaterer verdien til Tile og fargen.
    public void updateTileValue(int newValue) {
        if (newValue < 0) throw new IllegalArgumentException("En Tile kan ikke få negativ verdi");
        this.tileValue = newValue;
        setTileColor();
    }
      //? Setter fargen til Tile basert på hvilken verdi den har
    private void setTileColor() {                   
        switch(tileValue){  
            case 0 -> this.tileColor = "#808080";
            case 2 -> this.tileColor = "#ECE4DB";
            case 4 -> this.tileColor = "#EBE0CA";
            case 8 -> this.tileColor = "#E8B380";
            case 16 -> this.tileColor = "#E8996B";
            case 32 -> this.tileColor = "#E78365";
            case 64 -> this.tileColor = "#E56945";
            case 128 -> this.tileColor = "#E8CF7E";
            case 256 -> this.tileColor = "#E8CC70";
            case 512 -> this.tileColor = "#E7C863";
            case 1024 -> this.tileColor = "#E7C557";
            case 2024 -> this.tileColor = "#E6C24C";
            default -> this.tileColor = "#E6C24C";
        }
    }

}
