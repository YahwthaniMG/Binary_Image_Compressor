package edu.up.isgc.cg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.String;

public class Compressor {
    public static void compress(Color[][] pixelMatrix, String name) throws IOException {
        String filename=file(name, 0);
        createImg(pixelMatrix, filename, name);

    }

    /**
     * In this function Splits are performed, in order to obtain the name of the image
     * without the . and the extension in order to use it to call the original image
     */
    public static String file(String N, int x){
        if(x==0){
            String[] name_F= N.split(".c");
            String name= name_F[0] + ".bmp";
            return name;
        }
        else if(x==7){
            String[] name_F= N.split(".c");
            return name_F[0];
        } else{
            String[] name_F= N.split(".b");
            return name_F[0];
        }
    }

    /**
     *This function calculates the size in bytes of a file and returns its value
     *and prints it to the console
     */
    public static long getSize(String N) {
        try {
            Path ruta = Paths.get(N);
            long size = Files.size(ruta);
            System.out.printf("The %s is %d bytes. \n", N, size);
            return size;
        } catch (IOException e){
            System.err.println("Error -> " + e.getMessage());
        }
        return 0;
    }

    /**-
     * This function creates my BufferedImage with which I obtain an empty image that
     * is used to generate the display of the compressed image
     */
    public static void createImg(Color[][]pixelMatrix, String Imagen, String name) throws IOException{
        String Ima=file(Imagen, 1);
        BufferedImage imgS = ImageIO.read(new File(Imagen));
        BufferedImage img = new BufferedImage(imgS.getWidth(), imgS.getHeight(), BufferedImage.TYPE_INT_RGB);
        removeCo_reduceIm(img, pixelMatrix, Ima, name);
    }

    /**
     *In this function I use my BufferedImage to create the compressed image display
     *and get the RGB of each pixel from the pixelMatrix and save it to my binary file
     */
    public static void removeCo_reduceIm(BufferedImage imagen,Color[][]pixelMatrix, String I, String name) throws IOException {
        BufferedImage img = new BufferedImage(imagen.getWidth(), imagen.getHeight(), BufferedImage.TYPE_INT_RGB);
        int column=5;
        int R,G,B;
        FileOutputStream file = createBi_Fi(name);
        for(int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                if (x!=column){
                    img.setRGB(x, y, pixelMatrix[x][y].getRGB());
                    R=pixelMatrix[x][y].getRed()-128;
                    G=pixelMatrix[x][y].getGreen()-128;
                    B=pixelMatrix[x][y].getBlue()-128;
                    writeBi_Fi(file, R, G, B);
                } else{
                    if(y+1==img.getHeight()){
                        column+=5;
                    }
                }
            }
        }
        saveIma(img,I,0);
        closeBi_Fi(file);
    }

    /**
     * In this function I create my binary file
     */
    public static FileOutputStream createBi_Fi(String name) throws IOException {
        File outputFile = new File(name);
        FileOutputStream compressed = new FileOutputStream(outputFile);
        return compressed;
    }

    /**
     * In this function I write the RGB to my binary file
     */
    public static void writeBi_Fi(FileOutputStream file, int R, int G, int B) throws IOException {
        file.write(R);
        file.write(G);
        file.write(B);
    }

    /**
     * In this function I close my binary file
     */
    public static void closeBi_Fi(FileOutputStream file) throws IOException {
        file.close();
    }

    /**
     * In this function save the visualization of my image
     */
    public static void saveIma(BufferedImage img, String name, int X){
        if(X==0) {
            File outputImage = new File(name + "0.bmp");
            try {
                ImageIO.write(img, "bmp", outputImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            File outputImage = new File(name);
            try {
                ImageIO.write(img, "bmp", outputImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}