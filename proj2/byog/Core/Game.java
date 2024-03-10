package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.RandomUtils;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame = null;

        long seed = Integer.parseInt(input.substring(1, input.length() - 2));
        Random RANDOM = new Random(seed);


        int numRooms = RandomUtils.uniform(RANDOM, 10);
        int numHallways = RandomUtils.uniform(RANDOM, 15);

        makeRooms(finalWorldFrame, numRooms, RANDOM);
        makeHallways(finalWorldFrame, numHallways, RANDOM);
        makeWalls(finalWorldFrame);

        return finalWorldFrame;
    }

    public void makeRooms(TETile[][] world, int numRooms, Random RANDOM) {
        for (int i = 0; i < numRooms; i++) {
            makeSingleRoom(world, RANDOM);
        }
    }

    public void makeSingleRoom(TETile[][] world, Random RANDOM) {
        int widthBorder = 6;
        int heightBorder = 5;

        int width = RandomUtils.uniform(RANDOM, widthBorder);
        int height = RandomUtils.uniform(RANDOM, heightBorder);

        int x = RandomUtils.uniform(RANDOM, WIDTH - width);
        int y = RandomUtils.uniform(RANDOM, HEIGHT - height);
        Position position = new Position(x, y);

        fillWithTiles(world, position, Tileset.FLOOR, width, height);

    }

    public void fillWithTiles(TETile[][] world, Position position, TETile tile, int width, int height) {
        int startX = position.getX(), startY = position.getY();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[startX + i][startY + j] = tile;
            }
        }
    }

    public void makeHallways(TETile[][] world, int numHallways, Random RANDOM) {
        int direction = RandomUtils.uniform(RANDOM, 2);
        int lengthBorder = 9;

        int length = RandomUtils.uniform(RANDOM, lengthBorder);

        int x = 0, y = 0;

        if (direction == 1) {
            x = RandomUtils.uniform(RANDOM, WIDTH - length);
            y= RandomUtils.uniform(RANDOM, HEIGHT);
        }   {
            x = RandomUtils.uniform(RANDOM, WIDTH);
            y = RandomUtils.uniform(RANDOM, HEIGHT - length);
        }

        Position position = new Position(x, y);
        /* the hallway is horizontal if direction == 1*/
        if (direction == 1) {
            fillWithTiles(world, position, Tileset.FLOOR, length, 1);
        }   else {
            fillWithTiles(world, position, Tileset.FLOOR, 1, length);
        }
    }

    public void makeWalls(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y] == null && closeToFloor(world, new Position(x, y))) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public boolean closeToFloor(TETile[][] world, Position position) {
        int x = position.getX(), y = position.getY();
        if (world[x + 1][y].equals(Tileset.FLOOR)) {
            return true;
        }
        if (world[x - 1][y].equals(Tileset.FLOOR)) {
            return true;
        }
        if (world[x][y - 1].equals(Tileset.FLOOR)) {
            return true;
        }
        if (world[x][y + 1].equals(Tileset.FLOOR)) {
            return true;
        }
        return false;
    }
}
