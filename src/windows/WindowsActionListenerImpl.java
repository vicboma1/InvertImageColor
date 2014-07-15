package windows;

import invertColor.InvertColor;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by vicboma on 09/07/14.
 */
public class WindowsActionListenerImpl implements ActionListener {

    public static final String REQUEST_FOCUS = "requestFocus";
    public static final String ABOUT = "About";
    public static final String LOAD_IMAGE = "Load Image";
    public static final String EXIT = "Exit";
    public static final String OK = " OK!";
    public static final String KO = " KO!";
    private static final String INVERT_IMAGE_COLOR_COPYRIGHT_C_2014_VICTOR_BOLINCHES = "Invert Image Color \nCopyright (c) 2014 Victor Bolinches";
    private static final String SELECT_CHIP8_SUPER_CHIP_ROM = "Select image to invert color";
    private Map<String, ActionCommand> mapActions;

    WindowsActionListenerImpl(JMenuItem[] jMenuItems, Map<String, ActionCommand> mapActions) {
        addListeners(jMenuItems);
        this.mapActions = mapActions;
    }

    public static ActionListener create(final JFrame frame, final JMenuItem[] menuItems, final InvertColor invertColor) {
        return new WindowsActionListenerImpl(menuItems,
                new Hashtable<String, ActionCommand>() {
                    {
                        put(LOAD_IMAGE, () -> {
                            FileDialog loadFile = new FileDialog(frame, SELECT_CHIP8_SUPER_CHIP_ROM, FileDialog.LOAD);
                            loadFile.setDirectory("./resource");
                            loadFile.setFile("*");
                            loadFile.setVisible(true);

                            final File file = new File(loadFile.getDirectory(), loadFile.getFile());
                            final boolean isLoaded = Utils.isValidExtension(file);
                            if (isLoaded) {
                                System.out.println(LOAD_IMAGE + OK);
                                /**
                                 * Step by Step
                                 *
                                 * invertColor.configure(loadFile.getDirectory(), loadFile.getFile());
                                 * final BufferedImage bufferedImage = invertColor.bufferedImage();
                                 *
                                 * for (int x = 0; x < bufferedImage.getWidth(); x++) {
                                 *     for (int y = 0; y < bufferedImage.getHeight(); y++) {
                                 *       final BufferedImage image = invertColor.imageRender(x,y);
                                 *       frame.getContentPane().getGraphics().drawImage(image, 0, 0, null);
                                 *   }
                                 * }
                                 * Utils.save(image,loadFile.getDirectory(),loadFile.getFile());
                                 */
                                final BufferedImage image = invertColor.image(loadFile.getDirectory(), loadFile.getFile());
                                frame.getContentPane().getGraphics().drawImage(image, 0, 0, null);
                                Utils.save(image, loadFile.getDirectory(), loadFile.getFile());
                            } else
                                System.out.println(LOAD_IMAGE + KO);

                            this.get(REQUEST_FOCUS).execute();
                        });
                        put(ABOUT, () -> {
                            JOptionPane.showMessageDialog(frame, INVERT_IMAGE_COLOR_COPYRIGHT_C_2014_VICTOR_BOLINCHES);
                            this.get(REQUEST_FOCUS).execute();
                        });
                        put(EXIT, () -> {
                            WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
                            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
                            System.gc();
                            System.exit(0);
                        });
                        put(REQUEST_FOCUS, () -> {
                            frame.requestFocus();
                        });
                    }
                });
    }

    private void addListeners(JMenuItem[] jMenuItems) {
        IntStream stream = IntStream.range(0, jMenuItems.length);
        stream.sequential().forEach(x -> jMenuItems[x].addActionListener(this));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final String itemSelected = event.getActionCommand();
        if (this.mapActions.containsKey(itemSelected))
            this.mapActions.get(itemSelected).execute();
    }
}
