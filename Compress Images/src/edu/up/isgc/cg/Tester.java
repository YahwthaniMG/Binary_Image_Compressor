package edu.up.isgc.cg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class Tester {

    public static void main(String[] args) {
        System.out.println("Start: " + new Timestamp(new Date().getTime()));
        try {
            String filename = "Test05";
            BufferedImage img = ImageIO.read(new File(filename + ".bmp"));
            Color[][] pixelMatrix = new Color[img.getWidth()][img.getHeight()];

            for(int x = 0; x < img.getWidth(); x++){
                for(int y = 0; y < img.getHeight(); y++){
                    int rgb = img.getRGB(x, y);
                    int red = (rgb >> 16) & 0x000000FF;
                    int green = (rgb >> 8) & 0x000000FF;
                    int blue = (rgb) & 0x000000FF;
                    pixelMatrix[x][y] = new Color(red, green, blue);
                }
            }

            Compressor.compress(pixelMatrix, filename + ".compressed");
            Decompressor.decompress(filename + ".compressed", filename + "_decompressed.bmp");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finish: " + new Timestamp(new Date().getTime()));
    }
}
