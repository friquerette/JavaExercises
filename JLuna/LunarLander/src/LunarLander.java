import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Java. Lunar lander simple simulator
 *
 * @author Sergey Iryupin
 * @version 0.3 dated Nov 03, 2018
 */

public class LunarLander extends JFrame {

    // service constants
    final String TITLE_OF_PROGRAM = "Lunar lander";
    final int WIN_WIDTH = 480;
    final int WIN_HEIGHT = 680;
    final int KEY_LEFT = 37;
    final int KEY_UP = 38;
    final int KEY_RIGHT = 39;
    final int KEY_DOWN = 40;
    final int KEY_CTRL = 17;
    final int KEY_ENTER = 10;
    int reverse = 1;

    // mathematical model
    private LunarLanderModel model;

    public static void main(String[] args) {
        new LunarLander();
    }

    public LunarLander() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        canvas.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));

        model = new LunarLanderModel();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KEY_UP:
                        model.addFuel(2);
                        break;
                    case KEY_DOWN:
                        model.addFuel(-2);
                        break;
                    case KEY_LEFT:
                        model.addDuration(-0.2f);
                        break;
                    case KEY_RIGHT:
                        model.addDuration(0.2f);
                        break;
                    case KEY_CTRL:
                        reverse = (reverse > 0)? -1: 1;
                        break;
                    case KEY_ENTER:
                        float duration = model.getDuration();
                        model.simulate(reverse);
                        model.setDuration(duration);
                        break;
                    default:
                        System.out.println(e.getKeyCode());
                }
                canvas.repaint();
            }
        });

        add(canvas);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void drawStringCenter(Graphics g, String text, int x, int y, int dx) {
        int width = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x + (dx - width)/2, y);
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            g.setColor(new Color(78, 154, 1)); //115, 210, 22));
            g.setFont(new Font("Arial Black", 0, 26));
            drawStringCenter(g, "FUEL", 0, 45, 120);
            drawStringCenter(g, "TOTAL", 0, 105, 120);
            drawStringCenter(g, "TIME", 0, 165, 120);

            drawStringCenter(g, "SPEED", 360, 45, 120);
            drawStringCenter(g, "A", 360, 105, 120);
            drawStringCenter(g, "ALT", 360, 165, 120);

            drawStringCenter(g, "FUEL", 0, WIN_HEIGHT - 165, 180);
            drawStringCenter(g, "TIME", 300, WIN_HEIGHT - 165, 180);

            g.setColor(Color.green);
            g.setFont(new Font("Arial", 0, 20));

            drawStringCenter(g, String.format("%3.1f", model.getFuelWeight()), 0, 70, 120);
            drawStringCenter(g, String.format("%3.1f", model.getTotalWeight()), 0, 130, 120);
            drawStringCenter(g, String.format("%3.1f", model.getFlightTime()), 0, 190, 120);

            drawStringCenter(g, String.format("%3.2f", model.getSpeed()), 360, 70, 120);
            drawStringCenter(g, String.format("%3.2f", model.getAcceleration()), 360, 130, 120);
            drawStringCenter(g, String.format("%3.2f", model.getHeight()), 360, 190, 120);

            g.setFont(new Font("Arial", 0, 70));
            drawStringCenter(g, Integer.toString(model.getIntFuel()), 0, WIN_HEIGHT - 50, 180);
            drawStringCenter(g, String.format("%3.1f", model.getDuration()), 300, WIN_HEIGHT - 50, 180);

            g.setFont(new Font("Arial", 0, 28));
            drawStringCenter(g, Integer.toString(model.getIntFuel() + 2), 0, WIN_HEIGHT - 120, 180);
            drawStringCenter(g, (model.getIntFuel() == 0)? "" : Integer.toString(model.getIntFuel() - 2), 0, WIN_HEIGHT - 10, 180);

            drawStringCenter(g, String.format("%3.1f", model.getDuration() + 0.2f), 300, WIN_HEIGHT - 120, 180);
            drawStringCenter(g, (model.getDuration() == 0)? "" : String.format("%3.1f", model.getDuration() - 0.2f), 300, WIN_HEIGHT - 10, 180);

            g.drawOval(240 - 50, WIN_HEIGHT - 125, 100, 100);
            drawStringCenter(g, (reverse > 0)? "^" : "v", 240 - 50, WIN_HEIGHT - 60, 100);
        }
    }
}