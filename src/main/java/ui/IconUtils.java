package ui;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class IconUtils {

    private IconUtils() {
    }

    public static BufferedImage createCustomIcon() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, 32, 32);
        g2d.dispose();
        return img;
    }
}