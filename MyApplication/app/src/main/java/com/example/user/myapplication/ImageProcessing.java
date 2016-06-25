package com.example.user.myapplication;

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
        System.out.println(mostFrequent);//mistake, map to command
        return mostFrequent;
    }

    /*
     * Counts beads in input image using
     * Hough Circle Trasform
     */ 
    public static int countBeads(Bitmap image) {
        return 0;
    }

    /*
     * Gets input hex color and returns category of it
     * Blue, Green, Red etc
     */
    private static int getSimpleColor(int hex) {
        return classify(hex).ordinal();
    }

    public static void hexToHSL(int color, int[] c) {
        int re = (color & 0xFF0000) >> 16;
        int gr = (color & 0x00FF00) >> 8;
        int bl = (color & 0x0000FF);

        double r = re /255.0;
        double g = gr /255.0;
        double b = bl /255.0;
        double v;
        double m;
        double vm;
        double r2, g2, b2;

        int h = 0; // default to black
        int s = 0;
        int l = 0;
        v = Math.max(r,g);
        v = Math.max(v,b);
        m = Math.min(r,g);
        m = Math.min(m,b);
        l = (int)((m + v) / 2.0);
        vm = v - m;
        s = (int)vm;
        s /= (l <= 0.5) ? (v + m ) : (2.0 - v - m) ;

        r2 = (v - r) / vm;
        g2 = (v - g) / vm;
        b2 = (v - b) / vm;
        if (r == v)
        {
            h = (int)(g == m ? 5.0 + b2 : 1.0 - g2);
        }
        else if (g == v)
        {
            h = (int)(b == m ? 1.0 + r2 : 3.0 - b2);
        }
        else
        {
            h = (int)(r == m ? 3.0 + g2 : 5.0 - r2);
        }
        h /= 6.0;
        c[0] = h;
        c[1] = s;
        c[2] = l;
    }

    public static ourColors classify(int color)
    {
        //color = 0xFFFF00;
        //System.out.println(((color&0xFF0000)>>16)+"-"+((color&0x00FF00)>>8)+"-"+(color&0x0000FF));
        int[] c = new int [3];
        hexToHSL(color, c);
        float hue = c[0];
        float sat = c[1];
        float lgt = c[2];

        if (lgt < 0.2)  return ourColors.Black;
        if (lgt > 0.8)  return ourColors.White;

        if (sat < 0.25) return ourColors.Gray;

        if (hue < 30)   return ourColors.Red;
        if (hue < 90)   return ourColors.Yellow;
        if (hue < 150)  return ourColors.Green;
        if (hue < 210)  return ourColors.Cyan;
        if (hue < 270)  return ourColors.Blue;
        if (hue < 330)  return ourColors.Magenta;
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

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    
    public static ourColors classify(int color)
    {
        float[] hsbvals = new float[3];

        Color.RGBtoHSB((color&0xFF0000)>>16, (color&0x00FF00)>>8, (color&0x0000FF)>>0, hsbvals);
        //System.out.println(((color&0xFF0000)>>16)+"-"+((color&0x00FF00)>>8)+"-"+(color&0x0000FF));
        float hue = hsbvals[0]*360;
        float sat = hsbvals[1];
        float lgt = hsbvals[2];     

       // System.out.println(hue+"-"+lgt+"-"+sat);
        if (sat < 0.25) return ourColors.Gray;
        if (lgt < 0.1)  return ourColors.Black;
        if (lgt > 0.9)  return ourColors.White;

        if (hue < 30)   return ourColors.Red;
        if (hue < 90)   return ourColors.Yellow;
        if (hue < 150)  return ourColors.Green;
        if (hue < 210)  return ourColors.Cyan;
        if (hue < 270)  return ourColors.Blue;
        if (hue < 330)  return ourColors.Magenta;
        return ourColors.Red;
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
