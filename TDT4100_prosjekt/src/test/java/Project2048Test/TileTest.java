package Project2048Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project2048.Tile;

public class TileTest {
    private Tile tile;
    private Tile tile2;
    
    @BeforeEach
    public void setUp() {
        tile = new Tile();
    }

    @Test
    @DisplayName("Tester konstruktøren")
    public void constructorTest() {
        Assertions.assertEquals(0, tile.getTileValue(), "Her skulle verdien vært 0");
        Assertions.assertEquals("#808080", tile.getTileColor(), "Her skulle fargen vært #808080");
        
        tile2 = new Tile(32);
        Assertions.assertEquals(32, tile2.getTileValue(), "Her skulle verdien vært 32");
        Assertions.assertEquals("#E78365", tile2.getTileColor(), "Her skulle fargen vært #E78365");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tile2 = new Tile(-32);});
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tile2 = new Tile(10000000);});
    }

    @Test
    @DisplayName("Her skal jeg teste fargene for Tile.")
    public void updateTileValue() {
        tile.updateTileValue(2);
        Assertions.assertEquals("#ECE4DB", tile.getTileColor());
        Assertions.assertEquals(2, tile.getTileValue());
        
        tile.updateTileValue(4);
        Assertions.assertEquals("#EBE0CA", tile.getTileColor());
        Assertions.assertEquals(4, tile.getTileValue());
        
        tile.updateTileValue(8);
        Assertions.assertEquals("#E8B380", tile.getTileColor());
        Assertions.assertEquals(8, tile.getTileValue());
        
        tile.updateTileValue(16);
        Assertions.assertEquals("#E8996B", tile.getTileColor());
        Assertions.assertEquals(16, tile.getTileValue());
        
        tile.updateTileValue(32);
        Assertions.assertEquals("#E78365", tile.getTileColor());
        Assertions.assertEquals(32, tile.getTileValue());
        
        tile.updateTileValue(64);
        Assertions.assertEquals("#E56945", tile.getTileColor());
        Assertions.assertEquals(64, tile.getTileValue());
        
        tile.updateTileValue(128);
        Assertions.assertEquals("#E8CF7E", tile.getTileColor());
        Assertions.assertEquals(128, tile.getTileValue());
        
        tile.updateTileValue(256);
        Assertions.assertEquals("#E8CC70", tile.getTileColor());
        Assertions.assertEquals(256, tile.getTileValue());
        
        tile.updateTileValue(512);
        Assertions.assertEquals("#E7C863", tile.getTileColor());
        Assertions.assertEquals(512, tile.getTileValue());
        
        tile.updateTileValue(1024);
        Assertions.assertEquals("#E7C557", tile.getTileColor());
        Assertions.assertEquals(1024, tile.getTileValue());
        
        tile.updateTileValue(2048);
        Assertions.assertEquals("#E6C24C", tile.getTileColor());
        Assertions.assertEquals(2048, tile.getTileValue());
        
        tile.updateTileValue(4096);
        Assertions.assertEquals("#E6C24C", tile.getTileColor());
        Assertions.assertEquals(4096, tile.getTileValue());
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tile.updateTileValue(-1);});
            
        
    }
}
