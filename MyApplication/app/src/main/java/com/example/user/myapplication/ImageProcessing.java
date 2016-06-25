package com.example.user.myapplication;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageProcessing {

    /*
     * Tile information, global and specific to
     * the playboard
     */
    public static int TILES_ROW = 10;
    public static int TILES_COL = 5;
    public static int NUM_COLORS = 17; // 7 + 10 numbers

    public static enum ourColors{
        Empty,
        Red,
        Blue,
        Green,
        Magenta,
        Yellow,
        Cyan,
        Black,
        White,
        Gray
    }

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
    public static int[] run(Bitmap image) {
        int[] commands = new int[TILES_COL * TILES_ROW];
        splitImage(image, commands);
        return commands;
    }

    /*
     * Splits image into tiles
     * tiles are TILES_ROW * TILES_COL in total
     */
    private static void splitImage(Bitmap image, int[] commands) {
        // set every time
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        tileWidth = imageWidth / TILES_COL;
        tileHeight = imageHeight / TILES_ROW;

        //break image into tiles
        for(int i = 0; i < TILES_ROW; i++) {
            for(int j = 0; j < TILES_COL; j++) {
                commands[i * TILES_COL + j] =
                        getCommand(image,tileWidth * j, tileHeight * i,
                        tileWidth * j + tileWidth, tileHeight * i + tileHeight);
            }
        }
    }

    /*
     * Returns command number corresponding to input image tile
     */
    private static int getCommand(Bitmap image, int fromx, int fromy, int tox, int toy) {
        if(image == null) {
            System.out.println("Input image is null");
        }
        int[] frequency = new int[NUM_COLORS];
        int color;
        int mostFrequent = ourColors.Empty.ordinal();
        int maxFrequency = 0;
        for(int x = 0; x < tileWidth; x++) {
            for(int y = 0; y < tileHeight; y++) {
                color = image.getPixel(x, y);
                ourColors classifyResult = classify(color);
                //System.out.println(tile.hashCode()+""+classifyResult);
                int colorReturned = (int) classifyResult.ordinal();
                frequency[colorReturned]++;
                if(frequency[colorReturned] > maxFrequency) {
                    mostFrequent = colorReturned;
                    maxFrequency = frequency[colorReturned];
                } 
            }
        }
        if(mostFrequent == ourColors.White.ordinal() || mostFrequent == ourColors.Gray.ordinal()) {
            return countBeads(image);
        }
        System.out.println(mostFrequent);
        return mostFrequent;
    }

    /*
     * Counts beads in input image using
     * Hough Circle Trasform
     */
    public static int countBeads(Bitmap image) {
        return 2; //TODO: psalios
        /*Mat mat = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8U);
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, pixels);
        Mat circles = new Mat();
        int iCannyUpperThreshold = 10;
        int iMinRadius = 20;
        int iMaxRadius = 1000;
        int iAccumulator = 200;
        Imgproc.HoughCircles( mat, circles, Imgproc.CV_HOUGH_GRADIENT, 2.0, mat.rows() / 8, iCannyUpperThreshold, iAccumulator, iMinRadius, iMaxRadius);
        return circles.cols();*/
    }

    /*
     * Classifies a colour in a colour caategory
     */
    public static ourColors classify(int color)
    {
        float[] hsbvals = new float[3];

        Color.colorToHSV(color, hsbvals);
        float hue = hsbvals[0]*360;
        float sat = hsbvals[1];
        float lgt = hsbvals[2];


        if (lgt < 0.15)  return ourColors.Black;
        if (lgt > 0.7 && sat< 0.3) return ourColors.White;
        if (sat < 0.25) return ourColors.Gray;

        if (hue < 40)   return ourColors.Red;
        if (hue < 90)   return ourColors.Yellow;
        if (hue < 160)  return ourColors.Green;
        if (hue < 210)  return ourColors.Cyan;
        if (hue < 250)  return ourColors.Blue;
        if (hue < 315)  return ourColors.Magenta;
        return ourColors.Red;
    }

    /*
      Functions for testing purposes

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
        File input = new File("sample.jpg");
        try {
            BufferedImage image = ImageIO.read(input);
            run(image);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading input image");
        }
    }*/
}
