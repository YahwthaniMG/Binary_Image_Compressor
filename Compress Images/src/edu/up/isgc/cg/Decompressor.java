package edu.up.isgc.cg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.String;

public class Decompressor {
    /**
     * This function calls the necessary functions to perform the decompression of
     * my image as well as I show in the console the size in bytes of the original
     * and compressed file, as well as the loss of bytes
     */
    public static void decompress( String comp, String descomp) throws IOException {
        String name=Compressor.file(comp, 7);
        System.out.print("The size of the original image ");
        long size=Compressor.getSize(name+".bmp");
        System.out.print("The size of the compressed file is: ");
        long size2=Compressor.getSize(comp);
        System.out.print("The byte reduction was: "+(size-size2)+"\n");
        byte[] matrix=open_closeFile(comp);
        BufferedImage compIma=createImg(name);
        descompIma(compIma,matrix, descomp);

    }

    /**
     * This function creates my BufferedImage with which I get an empty image that
     * is used to generate the uncompressed image
     */
    public static BufferedImage createImg(String name) throws IOException{
        BufferedImage imgS = ImageIO.read(new File(name+".bmp"));
        BufferedImage img = new BufferedImage(imgS.getWidth(), imgS.getHeight(), BufferedImage.TYPE_INT_RGB);
        return img;
    }

    /**
     * This function uses my BufferedImage to create the uncompressed image where
     * I color the pixels as I move through the columns and fill the empty columns
     * with the same color as the previous column.
     */
    public static void descompIma(BufferedImage imagen, byte[] dates2, String descomp) {
        int cont=0;
        int column=5;
        for(int x = 0; x < imagen.getWidth(); x++) {
            for (int y = 0; y < imagen.getHeight(); y++) {
                if (x!=column ){
                    Color aux= new Color(dates2[cont]+128,dates2[cont+1]+128,dates2[cont+2]+128);
                    imagen.setRGB(x,y, aux.getRGB());
                    cont+=3;
                } else{
                    Color aux= new Color(imagen.getRGB(x-1,y));
                    imagen.setRGB(x,y, aux.getRGB());
                    if(y+1==imagen.getHeight()) {
                        column+= 5;
                    }
                    }
                }

            }
        Compressor.saveIma(imagen,descomp,1);
        }

    /**
     * In this function I create a reader of my binary file and save it
     * in the variable dates2
     */
    public static byte[] open_closeFile(String comp) throws IOException{
        File Path = new File(comp);
        FileInputStream dates = new FileInputStream(Path);
        byte[] dates2 = new byte[(int)Path.length()];
        dates.read(dates2);
        dates.close();
        return dates2;
    }


}
