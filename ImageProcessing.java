import java.awt.image.BufferedImage;

public class ImageProcessing {
    public static int TILES_ROW = 10;
    public static int TILES_COL = 5;

    // assume full screen
    public int[] run(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int tileWidth = width / TILES_COL;
        int tileHeight = height / TILES_ROW;
        BufferedImage[] tiles = new BufferedImage[TILES_ROW * TILES_COL];
        //break image into tiles
        for(int i = 0; i < TILES_ROW; i++) {
            for(int j = 0; j < TILES_COL; j++) {
                tiles[i * TILES_COL + j] = new BufferedImage(tileWidth, tileHeight, image.getType());
            }
        }
        //examine tiles
        return null; 
    } 
}
