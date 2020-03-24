import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    final static int W = 800, H = 600;

    BufferedImage image; //the offscreen image
    Graphics2D graphics; //the "tools" needed to draw in the image

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        createOffscreenImage();
        init();
    }

    private void createOffscreenImage() {
        image = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, W, H);
    }

    private void init() {
        setPreferredSize(new Dimension(W, H));
        setBorder(BorderFactory.createEtchedBorder());
        //Adaugam un mouseListener pentru a putea desena forme la click pe suprafata DrawingPanel.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                drawShape(e.getX(), e.getY());
                repaint();
            }
        });
    }

    private void drawShape(int x, int y) {
        Random random = new Random();
        //Generam o dimensiune random (minim 10 ca sa fie vizibila)
        int radius = random.nextInt(100) + 10;
        int sides = frame.configPanel.getNumberOfSides();
        //Generam o culoare random
        Color color = new Color(random.nextFloat(),
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat());
        graphics.setColor(color);
        graphics.fill(new RegularPolygon(x, y, radius, sides));
    }

    public void clearCanvas() {
        //Pentru a curata canvasul, desenam in el un dreptunghi ce incepe la coordonatele (0,0) si se intinde pe
        //toata dimensiunea canvas-ului.
        graphics.setColor(Color.WHITE);
        graphics.fill(new Rectangle(0, 0, W, H));
        repaint();
    }

    @Override
    public void update(Graphics g) { }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
