package project2048;

import java.util.Comparator;

public class BoardComparator implements Comparator<Board> {
    //TODO Vurdere å slette den klassen.
    //? Sjekker om brettene som tas inn er like etter det ene har blitt gjort fire operasjoner på.
    @Override
    public int compare(Board o1, Board o2) {
        o2.right(false,true);
        o2.up(false,true);
        o2.down(false,true);
        o2.left(false,true);
        for (int i = 0; i<4;i++) {
            for (int j = 0; j<4;j++) {
                if(o1.getTileValue(i, j) != o2.getTileValue(i, j)) return 1;
            }
        }
        return -1;
    }

}