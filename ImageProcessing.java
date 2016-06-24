import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;

public class ImageProcessing {

    /*
     * Tile information, global and specific to
     * the playboard
     */
    public static int TILES_ROW = 10;
    public static int TILES_COL = 5;
    public static int NUM_COLORS = 17; // 7 + 10 numbers
    // enum would be better
    public static int WHITE = 0x1;      // number 
    public static int BLACK = 0x2;     // end
    public static int RED = 0x3;       // for
    public static int GREEN = 0x4;     // move
    public static int BLUE = 0x5;      // left
    public static int YELLOW = 0x6;    //right
    public static int PINK = 0x7;      // if
    public static int LIGHTBLUE = 0x8; // else
    public static int GREY = 0x9;      // beads

    /*
     * set every time, specific to each capture
     */
    private static int imageWidth;
    private static int imageHeight;
    private static int tileWidth;
    private static int tileHeight;

    /*
     *  Return array of commands from input image
     *  Assume fullscreen, resinfg could be done later
     */
    public static int[] run(BufferedImage image) {
        BufferedImage[] tiles = splitImage(image);
        int[] commands = new int[tiles.length];
        for(int i = 0; i < tiles.length; i++) {
            commands[i] = getCommand(tiles[i]);
        }
        return commands;
    }

    /*
     * Splits image into tiles
     * tiles are TILES_ROW * TILES_COL in total
     */
    private static BufferedImage[] splitImage(BufferedImage image) {
        // set every time
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        tileWidth = imageWidth / TILES_COL;
        tileHeight = imageHeight / TILES_ROW;

        BufferedImage[] tiles = new BufferedImage[TILES_ROW * TILES_COL];
        //break image into tiles
        for(int i = 0; i < TILES_ROW; i++) {
            for(int j = 0; j < TILES_COL; j++) {
                //reserve memory
                tiles[i * TILES_COL + j] =
                    new BufferedImage(tileWidth, tileHeight, image.getType());
                //draw tiles
                Graphics2D renderer = tiles[i * TILES_COL + j].createGraphics();
                renderer.drawImage(image, 0, 0, tileWidth, tileHeight,
                        tileWidth * j, tileHeight * i,
                        tileWidth * j + tileWidth, tileHeight * i + tileHeight, null);
                renderer.dispose();
            }
        }
        test(tiles);
        return tiles;
    }

    /*
     * Returns command number corresponding to input image tile
     */
    private static int getCommand(BufferedImage tile) {
        if(tile == null) {
            System.out.println("Input image is null");
        }
        int[] frequency = new int[NUM_COLORS];
        int color;
        int mostFrequent = 0;
        int maxFrequency = 0;
        for(int x = 0; x < tileWidth; x++) {
            for(int y = 0; y < tileHeight; y++) {
                color = tile.getRGB(x, y);
                color = getSimpleColor(color);
                frequency[color]++;
                if(frequency[color] > maxFrequency) {
                    mostFrequent = color;
                    maxFrequency = frequency[color];
                } 
            }
        }
        if(mostFrequent == WHITE || mostFrequent == GREY) {
            return countBeads(tile);
        }
        return mostFrequent;
    }

    /*
     * Counts beads in input image using
     * Hough Circle Trasform
     */ 
    public static int countBeads(BufferedImage image) {
        return 0;
    }

    /*
     * Gets input hex color and returns category of it
     * Blue, Green, Red etc
     */
    private static int getSimpleColor(int hex) {
        return 0;
    } 

    /*
     * Functions for testing purposes
     */
    private static void test(BufferedImage[] tiles) {
        for(int i = 0; i < tiles.length; i++) {
            try {
                ImageIO.write(tiles[i], "jpg", new File("tile" + i + ".jpg"));
            } catch (Exception e) {
                System.out.println("Error in writting tile " + i);
            }
        }
    }

    public static void main(String[] args) {
        File input = new File("board.jpg");
        try {
            BufferedImage image = ImageIO.read(input);
            run(image);
        } catch (Exception e) {
            System.out.println("Error reading input image");
        }
    }
}
