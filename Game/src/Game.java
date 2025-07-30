import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class Game extends JFrame {
    private static Game game_game;
    private static long last_frame_time;
    private static Image snow;
    private static Image wall;
    private static float drop_top = 100;



    public static void main(String[] args) throws IOException {
        wall = ImageIO.read(Objects.requireNonNull(Game.class.getResourceAsStream("wall.png")));
        snow = ImageIO.read(Objects.requireNonNull(Game.class.getResourceAsStream("snow.png")));
        game_game = new Game();
        game_game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_game.setLocation(100, 50);
        game_game.setSize(1100, 800);
        last_frame_time = System.nanoTime();
        game_game.setVisible(true);
        GameField game_field = new GameField();
        game_game.add(game_field);
        game_game.setResizable(false);
    }

    public static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        final float delt_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        final float drop_v = 200;
        final float drop_left = 100;
        final int snow_height = 50;
        final int snow_width = 50;
        drop_top = drop_top + drop_v * delt_time;

        g.drawImage(wall, 10, 10, 1100, 730, null);

        g.drawImage(snow, (int) drop_left, (int) drop_top, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left, (int) drop_top - 200, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left, (int) drop_top - 400, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left, (int) drop_top - 600, snow_width, snow_height, null);

        g.drawImage(snow, (int) drop_left + 200, (int) drop_top - 100, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 200, (int) drop_top - 300, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 200, (int) drop_top - 500, snow_width, snow_height, null);

        g.drawImage(snow, (int) drop_left + 400, (int) drop_top, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 400, (int) drop_top - 200, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 400, (int) drop_top - 400, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 400, (int) drop_top - 600, snow_width, snow_height, null);

        g.drawImage(snow, (int) drop_left + 600, (int) drop_top - 100, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 600, (int) drop_top - 300, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 600, (int) drop_top - 500, snow_width, snow_height, null);

        g.drawImage(snow, (int) drop_left + 800, (int) drop_top, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 800, (int) drop_top - 200, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 800, (int) drop_top - 400, snow_width, snow_height, null);
        g.drawImage(snow, (int) drop_left + 800, (int) drop_top - 600, snow_width, snow_height, null);

        if (drop_top > game_game.getHeight()) {
            drop_top = 100;
        }
    }

    public static class GameField extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();

        }
    }
}
