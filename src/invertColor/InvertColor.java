package invertColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by vicboma on 15/07/14.
 */
public class InvertColor extends JComponent {

    private static final int MASK_COLOR = 255;
    private BufferedImage bufferedImage;

    public InvertColor() {
    }

    public InvertColor configure(String path, String image) {
        try {
            bufferedImage = ImageIO.read(new File(path + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public BufferedImage bufferedImage() {
        return this.bufferedImage;
    }

    public BufferedImage imageRender(final int x, final int y) {
        final int rgba = bufferedImage.getRGB(x, y);
        final Color color = new Color(rgba, true);
        final Color invertColor = new Color(MASK_COLOR - color.getRed(), MASK_COLOR - color.getGreen(), MASK_COLOR - color.getBlue());
        bufferedImage.setRGB(x, y, invertColor.getRGB());

        return bufferedImage;
    }

    public BufferedImage image(String path, String image) {
        BufferedImage inputFile = null;
        try {
            inputFile = ImageIO.read(new File(path + image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int x = 0; x < inputFile.getWidth(); x++) {
            for (int y = 0; y < inputFile.getHeight(); y++) {
                final int rgba = inputFile.getRGB(x, y);
                final Color color = new Color(rgba, true);
                final Color invertColor = new Color(MASK_COLOR - color.getRed(), MASK_COLOR - color.getGreen(), MASK_COLOR - color.getBlue());
                inputFile.setRGB(x, y, invertColor.getRGB());
            }
        }

        return inputFile;
    }
}

