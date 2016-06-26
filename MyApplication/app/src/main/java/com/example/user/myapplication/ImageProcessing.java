package com.example.user.myapplication;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessing {

    static{ System.loadLibrary("opencv_java3"); }

    /*
     * Tile information, global and specific to
     * the playboard
     */
    public static int TILES_ROW = 3;
    public static int TILES_COL = 8;
    public static int NUM_COLORS = 17; // 7 + 10 numbers

    public static enum ourColors{
        Empty,
        Red,
        Blue,
        Green,
        Magenta,
        Yellow,
        Orange,
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
        int marginWidth = tileWidth / 4;
        int marginHeight = tileHeight / 4;
        for(int x = 0 + marginWidth; x < tileWidth - marginWidth; x++) {
            for(int y = 0 + marginHeight; y < tileHeight - marginHeight; y++) {
                color = image.getPixel(fromx + x, fromy + y);
                ourColors classifyResult = classify(color);
                System.out.println(color+"\n");
                int colorReturned = (int) classifyResult.ordinal();
                frequency[colorReturned]++;
                if(frequency[colorReturned] > maxFrequency) {
                    mostFrequent = colorReturned;
                    maxFrequency = frequency[colorReturned];
                } 
            }
        }
        if(mostFrequent == ourColors.White.ordinal() || mostFrequent == ourColors.Gray.ordinal()) {
            return countBeads(Bitmap.createBitmap(image,fromx,fromy,tox-fromx,toy-fromy));
        }
        System.out.println(mostFrequent);
        return mostFrequent;
    }

    /*
     * Counts beads in input image using
     * Hough Circle Trasform
     */
    public static int countBeads(Bitmap image) {
            Mat mat = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC1);
            Mat grayMat = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC1);

            Utils.bitmapToMat(image, mat);

            /* convert to grayscale */
            int colorChannels = (mat.channels() == 3) ? Imgproc.COLOR_BGR2GRAY
                    : ((mat.channels() == 4) ? Imgproc.COLOR_BGRA2GRAY : 1);

            Imgproc.cvtColor(mat, grayMat, colorChannels);

            /* reduce the noise so we avoid false circle detection */
            Imgproc.GaussianBlur(grayMat, grayMat, new Size(9, 9), 2, 2);

            // accumulator value
            double dp = 1.0;
            // minimum distance between the center coordinates of detected circles in pixels
            double minDist = 2;

            // min and max radii (set these values as you desire)
            int minRadius = 0, maxRadius = 10000;

            // param1 = gradient value used to handle edge detection
            // param2 = Accumulator threshold value for the
            // cv2.CV_HOUGH_GRADIENT method.
            // The smaller the threshold is, the more circles will be
            // detected (including false circles).
            // The larger the threshold is, the more circles will
            // potentially be returned.
            double param1 = 10, param2 = 10;

            /* create a Mat object to store the circles detected */
            Mat circles = new Mat(image.getWidth(), image.getHeight(), CvType.CV_8UC1);

            //List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            //Imgproc.findContours(grayMat,contours,new Mat(), Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
            /* find the circle in the image */
            Imgproc.HoughCircles(grayMat, circles,
                    Imgproc.CV_HOUGH_GRADIENT, dp, minDist, param1,
                    param2, minRadius, maxRadius);

            /* get the number of circles detected */
            int numberOfCircles = (circles.rows() == 0) ? 0 : circles.cols();
            //numberOfCircles %= 11; //max number allowed is 10
            return 8 + numberOfCircles; //index of 0 is 8
    }

    /*
     * Classifies a colour in a colour caategory
     */
    public static ourColors classify(int color)
    {
        float[] hsbvals = new float[3];

        Color.colorToHSV(color, hsbvals);
        float hue = hsbvals[0];
        float sat = hsbvals[1];
        float lgt = hsbvals[2];

        if (lgt < 0.25)  return ourColors.Black;
        if (lgt > 0.7 && sat < 0.3) return ourColors.White;
        if (sat < 0.25 && lgt > 0.5) return ourColors.Gray;

        if (hue < 40)   return ourColors.Red;
        if (hue < 50)  return ourColors.Orange;
        if (hue < 65)   return ourColors.Yellow;
        if (hue < 160)  return ourColors.Green;

        if (hue < 250)  return ourColors.Blue;
        if (hue < 345)  return ourColors.Magenta;
        else return ourColors.Red;
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
