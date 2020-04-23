import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImportedImage {
    public BufferedImage image;
    String path;
    Pixel[][] pixels;
    public double scale;

    public ImportedImage(String pPath) {
        scale = 0.5;
        path = pPath;
        File loadFile = new File(pPath);
        try {
            image = ImageIO.read(loadFile);
            calculatePixels();
        } catch (IOException e) {
            System.out.println("Could not read file");
        }
    }

    public void calculatePixels() {
        pixels = new Pixel[image.getWidth()][image.getHeight()];
        if (!path.endsWith(".png")) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pixels[x][y] = new Pixel(howMuchRed(x, y), howMuchGreen(x, y), howMuchBlue(x, y));
                }
            }
        }
        else{
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pixels[x][y] = new Pixel(new Color(image.getRGB(x,y),true));
                }
            }
        }

    }

    public int howMuchRed(int x, int y) {
        try {
            int clr = image.getRGB(x, y);
            return ((clr & 0x00ff0000) >> 16);
        } catch (Exception e) {
            System.out.println(x + "  outside range  " + y);
            return (0);
        }
    }

    public int howMuchGreen(int x, int y) {
        try {
            int clr = image.getRGB(x, y);


            return ((clr & 0x0000ff00) >> 8);
        } catch (Exception e) {
            return (0);
        }

    }

    public int howMuchBlue(int x, int y) {
        try {
            int clr = image.getRGB(x, y);
            return (clr & 0x000000ff);
        } catch (Exception e) {
            return (0);
        }
    }

}
