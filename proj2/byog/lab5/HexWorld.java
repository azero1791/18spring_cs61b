package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random RANDOM = new Random(1561351);
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    /**
     * return the xOffSet of some row of hexagon
     * @param rowi the ith row of hexagon
     * @param hexSize the size of hexagon
     * @return
     */

    private static int getXOffSet(int rowi, int hexSize) {
        if (rowi < hexSize) {
            return (3 * hexSize - 2 - getRowWidth(rowi, hexSize)) / 2;
        }   else {
            rowi = 2 * hexSize - 1 - rowi;
            return (3 * hexSize - 2 - getRowWidth(rowi, hexSize)) / 2;
        }
    }

    /**
     * get the width of some row of hexagon
     * @param rowi
     * @param hexSize
     * @return
     */
    private static int getRowWidth(int rowi, int hexSize) {
        if (rowi < hexSize) {
            return hexSize + rowi * 2;
        }   else {
            rowi = 2 * hexSize - 1 - rowi;
            return hexSize + rowi * 2;
        }
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, getRowWidth(5, 3));
        assertEquals(5, getRowWidth(4, 3));
        assertEquals(7, getRowWidth(3, 3));
        assertEquals(7, getRowWidth(2, 3));
        assertEquals(5, getRowWidth(1, 3));
        assertEquals(3, getRowWidth(0, 3));
        assertEquals(2, getRowWidth(0, 2));
        assertEquals(4, getRowWidth(1, 2));
        assertEquals(4, getRowWidth(2, 2));
        assertEquals(2, getRowWidth(3, 2));
    }

    @Test
    public void testGetXOffSet() {
        assertEquals(1, getXOffSet(0, 2));
        assertEquals(0, getXOffSet(1, 2));
        assertEquals(2, getXOffSet(0, 3));
        assertEquals(1, getXOffSet(1, 3));
        assertEquals(0, getXOffSet(2, 3));
        assertEquals(3, getXOffSet(0, 4));
        assertEquals(2, getXOffSet(1, 4));
        assertEquals(1, getXOffSet(2, 4));
        assertEquals(0, getXOffSet(3, 4));
    }

    /**
     * get the absolute start position of some row of hexagon
     * @param rowi the ith row of hexagon where rowi == 0 is the button row
     * @param startP the startPostion where we start to calculate start position x of rowi
     * @param hexSize the size of hexagon
     * @return
     */
    private static int getRowStartX(int rowi, Position startP, int hexSize) {
        return startP.getX() + getXOffSet(rowi, hexSize);
    }

    /**
     * add the hexagon row by row
     * @param world the world need to be drawn
     * @param startPosition the most left and button position
     * @param tetile some tetile type
     * @param rowWidth the width of light of some row of hexagon
     */
    private static void addRow(TETile[][] world, Position startPosition, TETile tetile, int rowWidth) {
        int startY = startPosition.getY();
        int startX = startPosition.getX();
        for (int x = 0; x < rowWidth; x++) {
            world[startX + x][startY] = tetile;
        }
    }

    /**
     * add hexagon to the world row by row
     * @param world the game world needed to be drawn
     * @param p the start position where the given hexagon will be drawn
     * @param tetile the tile element type
     * @param size the size of hexagon
     */
    public static void addHexagon(TETile[][] world, Position p, TETile tetile, int size) {

        for (int rowi = 0; rowi < size * 2; rowi++) {
            int startY = rowi + p.getY();
            int startX = getRowStartX(rowi, p, size);

            Position startPosition = new Position(startX, startY);

            addRow(world, startPosition, tetile, getRowWidth(rowi, size));
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.NOTHING;
            default: return Tileset.NOTHING;
        }
    }

    public static void fillWithHex(TETile[][] world) {

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int x = 0; x < WIDTH - 7; x += 7) {
            for (int y = 0; y < HEIGHT - 7; y += 7) {
                addHexagon(world, new Position(x, y), randomTile(), 2);
            }
        }



    }
    public static void main(String[] args) {
        TERenderer renderer = new TERenderer();
        renderer.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        fillWithHex(world);

        renderer.renderFrame(world);
    }

}
