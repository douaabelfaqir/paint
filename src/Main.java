
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JFrame {

    public Main() {
        setTitle("Mon Paint");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PaintPanel panneau = new PaintPanel();

        JButton couleur = new JButton("Choisir couleur");
        JButton effacer = new JButton("Effacer");

        couleur.addActionListener(e -> {
            Color nouvelleCouleur = JColorChooser.showDialog(
                    this, "Choisir une couleur", Color.BLACK);

            if (nouvelleCouleur != null) {
                panneau.setCouleur(nouvelleCouleur);
            }
        });

        effacer.addActionListener(e -> panneau.effacer());

        JPanel barre = new JPanel();
        barre.add(couleur);
        barre.add(effacer);

        add(barre, BorderLayout.NORTH);
        add(panneau, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

class PaintPanel extends JPanel {

    private ArrayList<ArrayList<Point>> dessins = new ArrayList<>();
    private Color couleur = Color.BLACK;

    public PaintPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dessins.add(new ArrayList<>());
                dessins.get(dessins.size() - 1).add(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (!dessins.isEmpty()) {
                    dessins.get(dessins.size() - 1).add(e.getPoint());
                    repaint();
                }
            }
        });
    }

    public void setCouleur(Color c) {
        couleur = c;
    }

    public void effacer() {
        dessins.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        g2.setColor(couleur);

        for (ArrayList<Point> dessin : dessins) {
            for (int i = 1; i < dessin.size(); i++) {
                Point p1 = dessin.get(i - 1);
                Point p2 = dessin.get(i);

                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}
