package com.example.user.myapplication;

import android.graphics.Bitmap;

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

    public static enum ourColors{
        Black,
        White,
        Gray,
        Yellow,
        Green,
        Cyan,
        Blue,
        Magenta,
        Red,
        Empty
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
        BufferedImage[] tiles = splitImage(image);
        int[] commands = new ourColors[tiles.length];
        for(int i = 0; i < tiles.length; i++) {
            System.out.print(i+"\t");
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
    private static ourColors getCommand(BufferedImage tile) {
        if(tile == null) {
            System.out.println("Input image is null");
        }
        int[] frequency = new int[NUM_COLORS];
        int color;
        ourColors mostFrequent = ourColors.Empty;
        int maxFrequency = 0;
        for(int x = 0; x < tileWidth; x++) {
            for(int y = 0; y < tileHeight; y++) {
                color = tile.getRGB(x, y);
                ourColors classifyResult= classify(color);
                //System.out.println(tile.hashCode()+""+classifyResult);
                short colorReturned = (short) classifyResult.ordinal();
                frequency[colorReturned]++;
                if(frequency[colorReturned] > maxFrequency) {
                    mostFrequent = classify(color);
                    maxFrequency = frequency[colorReturned];
                } 
            }
        }
        if(mostFrequent == ourColors.White || mostFrequent == ourColors.Gray) {
            //TODO return countBeads(tile);
        }
        System.out.println(mostFrequent);
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
        //return range(hex2Rgb(new String(hex)));
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
    /*public static void main(String[] args) {
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
