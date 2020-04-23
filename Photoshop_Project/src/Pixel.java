import java.awt.*;

public class Pixel {
    public int r,g,b,a=255;
    public Color pixelColor;

    public Pixel (int pr, int pg, int pb){
        r = pr;
        g = pg;
        b = pb;
        pixelColor = new Color (r,g,b);
    }

    public Pixel (int pr, int pg, int pb, int pa){
        r = pr;
        g = pg;
        b = pb;
        a = pa;
    }

    public Pixel (Color pColor){
        pixelColor = pColor;
        r = pixelColor.getRed();
        g = pixelColor.getGreen();
        b = pixelColor.getBlue();
        a = pixelColor.getAlpha();
    }

}
