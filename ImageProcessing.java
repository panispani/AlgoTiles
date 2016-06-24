import java.awt.image.BufferedImage;

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
    private int imageWidth;
    private int imageHeight;
    private int tileWidth;
    private int tileHeight;

    /*
     *  Return array of commands from input image
     *  Assume fullscreen, resinfg could be done later
     */
    public int[] run(BufferedImage image) {
        BufferedImage[] tiles = splitImage(image);
        return null;
    }

    /*
     * Splits image into tiles
     * tiles are TILES_ROW * TILES_COL in total
     */
    private BufferedImage[] splitImage(BufferedImage image) {
        // set every time
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        tileWidth = imageWidth / TILES_COL;
        tileHeight = imageHeight / TILES_ROW;

        BufferedImage[] tiles = new BufferedImage[TILES_ROW * TILES_COL];
        //break image into tiles
        for(int i = 0; i < TILES_ROW; i++) {
            for(int j = 0; j < TILES_COL; j++) {
                tiles[i * TILES_COL + j] = new BufferedImage(tileWidth, tileHeight, image.getType());
            }
        }
        return tiles;
    }
}
