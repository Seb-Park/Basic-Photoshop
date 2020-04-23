import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements Runnable, KeyListener {
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public String filePath;
    public ArrayList<ImportedImage> importedImages;

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
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

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
                canvas.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                render();
            }
        });
    }

    public void importNewImage() {

        String dir;
        FileDialog fdL = new FileDialog(new Frame(), "load me", FileDialog.LOAD);
        fdL.show();
        dir = fdL.getDirectory();
        String fileName = fdL.getFile();

        FileInputStream fis;
        try {
            fis = new FileInputStream(dir + fileName);
//            ObjectInputStream ois=new ObjectInputStream(fis);
//            var = ois.readObject();
            importedImages.add(new ImportedImage(dir + fileName));
            fis.close();
            System.out.println("File " + fileName + " was loaded successfully");

        } catch (IOException e) {
            System.out.println("IOException: Image could not be loaded.");
            System.out.println(e);
        }
        render();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

        for(int i = 0; i < importedImages.size(); i++){
            for(int x = 0; x< importedImages.get(i).pixels.length; x++){
                for(int y = 0; y< importedImages.get(i).pixels[x].length; y++){
                    g.setColor(new Color(importedImages.get(i).pixels[x][y].r,importedImages.get(i).pixels[x][y].g,importedImages.get(i).pixels[x][y].b,importedImages.get(i).pixels[x][y].a));
                    g.drawRect(x,y,1,1);
                }
            }
        }

        g.dispose();
        bufferStrategy.show();
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