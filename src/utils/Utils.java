package utils;

import windows.Windows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vicboma on 15/07/14.
 */
public class Utils {

    private static final int HALF = 0x02;

    private static Set<String> setExtension = new HashSet() {
        {
            add("jpeg");
            add("jpg");
            add("gif");
            add("tiff");
            add("tif");
            add("png");
            add("bmp");
        }
    };

    private static DisplayMode displayMode() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    }

    public static Integer DisplayModeCenterX() {
        final int width = displayMode().getWidth();
        final int centerX = (width / HALF) - (Windows.WIDTH / HALF);
        return centerX;
    }

    public static Integer DisplayModeCenterY() {
        final int height = displayMode().getHeight();
        final int centerY = (height / HALF) - (Windows.HEIGHT / HALF);
        return centerY;
    }

    public static Boolean isValidExtension(File f) {
        String ext = null;
        String nameFile = f.getName();
        final int index = nameFile.lastIndexOf('.');

        if (index > 0 && index < nameFile.length() - 1)
            ext = nameFile.substring(index + 1).toLowerCase();

        return (setExtension.contains(ext));
    }

    public static Boolean save(BufferedImage image, String path, String file) {
        Boolean result = false;
        final File outputFile = new File(path + "invert-" + file);
        try {
            ImageIO.write(image, "jpg", outputFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
