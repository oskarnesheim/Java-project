package Project2048Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project2048.RowComparator;
import project2048.Tile;

public class RowComparatorTest {
    RowComparator r1 = new RowComparator();
    ArrayList<Tile> row ;
    ArrayList<Integer> expectedRow;
    
    @Test
    @DisplayName("Sjekker at to like brikker som står sammen blir flyttet sammen")
    public void RowComparatorTester1() {
        row = new ArrayList<>(List.of(
            new Tile(4),new Tile(4), new Tile(), new Tile()
            ));
            row.sort(r1);
            
        expectedRow = new ArrayList<>(List.of(0,0,4,4));
        for (int i = 0;i<3;i++) 
            Assertions.assertEquals(expectedRow.get(i), row.get(i).getTileValue());
    }

    @Test
    @DisplayName("Sjekker at to brikker fra hverandre blir plassert sammen")
    public void RowComparatorTester2() {
        row = new ArrayList<>(List.of(
            new Tile(4),new Tile(), new Tile(), new Tile(4)
            ));
        row.sort(r1);

        expectedRow = new ArrayList<>(List.of(0,0,4,4));
        for (int i = 0;i<3;i++) 
            Assertions.assertEquals(expectedRow.get(i), row.get(i).getTileValue());
    }

    @Test
    @DisplayName("Sjekker at ulike brikker ikke påvirker hverandre")
    public void RowComparatorTester3() {
        row = new ArrayList<>(List.of(
            new Tile(4),new Tile(2), new Tile(8), new Tile(4)
            ));
        row.sort(r1);

        expectedRow = new ArrayList<>(List.of(4,2,8,4));
        for (int i = 0;i<3;i++) 
            Assertions.assertEquals(expectedRow.get(i), row.get(i).getTileValue());
    }
}
