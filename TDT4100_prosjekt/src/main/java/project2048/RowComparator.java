package project2048;

import java.util.Comparator;

public class RowComparator implements Comparator<Tile>{

    //? Sjekker om en av brikkene har verdi 0 og returner differansen mellom de.
    @Override
    public int compare(Tile o1, Tile o2) {
        if (o1.getTileValue() == 0 || o2.getTileValue() == 0){
            return o1.getTileValue() - o2.getTileValue();
        }
        return 0;
    }
    
}
