import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin extends AABB {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public static BufferedImage coinImage = null;

    public boolean collected;

    public Coin(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        collected = false;
    }

    @Override
    public void render(Graphics g, int referenceFrame) {
        if(collected) {
            return;
        }
        if(coinImage == null) {
            try {
                coinImage = ImageIO.read(new File("assets/coin.png"));
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        g.drawImage(coinImage,x-referenceFrame,y, WIDTH, HEIGHT, null);
    }
}

