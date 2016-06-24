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
        return null;
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
