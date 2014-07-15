package windows;

import invertColor.InvertColor;
import utils.Utils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * Created by vicboma on 09/07/14.
 */
public class Windows {

    public static final String INVERT_IMAGE_COLOR = "Invert Image Color";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private JFrame jFrame;
    private InvertColor component;

    Windows(final JFrame frame, final WindowListener windowListener, final InvertColor component) {
        this.component = component;
        this.jFrame = frame;
        configureGUI();

        this.jFrame.setTitle(INVERT_IMAGE_COLOR);
        this.jFrame.setSize(WIDTH, HEIGHT);
        this.jFrame.setLocation(Utils.DisplayModeCenterX(), Utils.DisplayModeCenterY());
        this.jFrame.addWindowListener(windowListener);
        this.jFrame.repaint();
        this.jFrame.setVisible(true);
        this.jFrame.requestFocus();
    }

    public static Windows create(InvertColor component) {
        final JFrame frame = new JFrame();
        final JPanel panel = new JPanel();
        panel.add(component);
        frame.setContentPane(panel);
        final WindowListener windowListener = WindowsListenerImpl.create();
        return new Windows(frame, windowListener, component);
    }

    private void configureGUI() {
        final JMenuItem loadImage = new JMenuItem("Load Image");
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenuItem about = new JMenuItem("About");

        final JMenu fileMenu = new JMenu("File");
        fileMenu.add(loadImage);
        fileMenu.add(exit);

        final JMenu helpMenu = new JMenu("Help");
        helpMenu.add(about);

        final JMenuBar menu = new JMenuBar();
        menu.add(fileMenu);
        menu.add(helpMenu);

        this.jFrame.setJMenuBar(menu);

        final ActionListener actionListener = WindowsActionListenerImpl.create(this.jFrame, new JMenuItem[]{loadImage, about, exit}, component);
    }

    public JFrame component() {
        return this.jFrame;
    }
}
