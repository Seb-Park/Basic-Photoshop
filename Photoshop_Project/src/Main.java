import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main implements Runnable, KeyListener {
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public JPanel rightContainer;
    public JButton importNewButton;
    public JButton quickRender;
    public BufferStrategy bufferStrategy;
    public String filePath;
    public ArrayList<ImportedImage> importedImages;
    JMenuBar menuBar;
    JMenu fileMenu, submenu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem;
    JCheckBoxMenuItem cbMenuItem;

    public Main() {

        setUpGraphics();
        importedImages = new ArrayList<ImportedImage>();

    }

    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }

    public void run() {

//        Scanner scanner = new Scanner(System.in);
//        filePath = scanner.nextLine();
//        JOptionPane.showInputDialog(frame, filePath);
//        System.out.println(filePath);
        render();
        while (true) {
            moveThings();
//            render();
            pause(20);
        }
    }


    public void moveThings() {

    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Photostore");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(new BorderLayout(0, 0));

        rightContainer = new JPanel();
        rightContainer.setLayout(new BoxLayout(rightContainer, BoxLayout.Y_AXIS));
        rightContainer.setBackground(new Color(50, 50, 50));
        importNewButton = new JButton("New Image +");
        importNewButton.setBackground(new Color(100, 100, 100));
        importNewButton.setOpaque(true);
        importNewButton.addActionListener(e -> {
            importNewImage();
        });
        quickRender = new JButton("Quick Render");
        quickRender.addActionListener(e -> {
            render();
        });
        rightContainer.add(importNewButton);
        rightContainer.add(quickRender);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(BorderLayout.CENTER, canvas);
        panel.add(BorderLayout.EAST, rightContainer);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);

        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component) evt.getSource();
                canvas.setBounds(0, 0, frame.getWidth() - rightContainer.getWidth(), frame.getHeight());
//                render();
            }

//            /** Time to wait */
//            private final int DELAY = 1000;
//            /** Waiting timer */
//            private javax.swing.Timer waitingTimer;
//
//            /**
//             * Handle resize event.
//             */
//            @Override
//            public void componentResized(ComponentEvent e)
//            {
//                if (this.waitingTimer==null)
//                {
//                    /* Start waiting for DELAY to elapse. */
//                    this.waitingTimer = new Timer(DELAY,this);
//                    this.waitingTimer.start();
//                }
//                else
//                {
//                    /* Event came too soon, swallow it by resetting the timer.. */
//                    this.waitingTimer.restart();
//                }
//            }
//
//            /**
//             * Actual resize method
//             */
//            public void applyResize()
//            {
//                //...
//            }
//
//            /**
//             * Handle waitingTimer event
//             */
//            public void actionPerformed(ActionEvent ae)
//            {
//                /* Timer finished? */
//                if (ae.getSource()==this.waitingTimer)
//                {
//                    /* Stop timer */
//                    this.waitingTimer.stop();
//                    this.waitingTimer = null;
//                    /* Resize */
//                    this.applyResize();
//                }
//            }
        });

        setUpMenu();
    }

    public void setUpMenu() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        JMenuItem f1, f2, f3, f4, f5;
//        JFrame f = new JFrame("Menu and MenuItem Example");
        JMenuBar mb = new JMenuBar();
        fileMenu = new JMenu("File");
        submenu = new JMenu("Open Recent");
        f1 = new JMenuItem("Open...");
        f2 = new JMenuItem("Open Recent");
        f3 = new JMenuItem("Import...");
        f3.addActionListener(e->
        {
            importNewImage();
        });
        f4 = new JMenuItem("Export");
        f4.addActionListener(
                e->{
                    export();
                }
        );
        f5 = new JMenuItem("Item 5");
        fileMenu.add(f1);
        fileMenu.add(f2);
        fileMenu.add(submenu);
        fileMenu.addSeparator();
        fileMenu.add(f3);
        fileMenu.add(f4);
        submenu.add(f5);
        mb.add(fileMenu);
        frame.setJMenuBar(mb);
        frame.setVisible(true);
    }

    public void importNewImage() {
//http://stackoverflow.com/questions/12558413/how-to-filter-file-type-in-filedialog
        String dir;
        FileDialog fdL = new FileDialog(new Frame(), "Import", FileDialog.LOAD);
        fdL.show();
        dir = fdL.getDirectory();
        String fileName = fdL.getFile();

        FileInputStream fis;
        try {
            if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                fis = new FileInputStream(dir + fileName);
//            ObjectInputStream ois=new ObjectInputStream(fis);
//            var = ois.readObject();
                importedImages.add(new ImportedImage(dir + fileName));
                fis.close();
                System.out.println("File " + fileName + " was loaded successfully");
            }
        } catch (IOException e) {
            System.out.println("IOException: Image could not be loaded.");
            System.out.println(e);
        }
        render();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

        for (int i = 0; i < importedImages.size(); i++) {
            for (int x = 0; x < importedImages.get(i).pixels.length; x += 2) {
                for (int y = 0; y < importedImages.get(i).pixels[x].length; y += 2) {
                    g.setColor(new Color(importedImages.get(i).pixels[x][y].r, importedImages.get(i).pixels[x][y].g, importedImages.get(i).pixels[x][y].b, importedImages.get(i).pixels[x][y].a));
                    g.fillRect((int) (x * importedImages.get(i).scale), (int) (y * importedImages.get(i).scale), 2, 2);
                }
            }
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void export(){

        String dir;
        FileDialog fdL = new FileDialog(new Frame(), "Export", FileDialog.SAVE);
        fdL.show();
        dir = fdL.getDirectory();
        String fileName = fdL.getFile();

        //part of code from https://stackoverflow.com/questions/12984207/cannot-convert-current-canvas-data-into-image-in-java
//
//        BufferedImage image=new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
//
//        Graphics2D g2d = image.createGraphics();
//        canvas.printAll(g2d);
//        g2d.dispose();
//        try {
//            ImageIO.write(image, "png", new File(dir+fileName));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        Container pane = frame.getContentPane();
        BufferedImage img = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        pane.printAll(g2d);
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File(dir+fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            importNewImage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}