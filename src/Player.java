import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends AABB{

    public final static int WALKSPEED = 4;
    public final static int JUMPSPEED = -30;

    public final static int GRAVITY = 2;

    public final static int WIDTH = 50;
    public final static int HEIGHT = 100;

    public static BufferedImage standing = null;

    public static BufferedImage walkRight1 = null;
    public static BufferedImage walkRight2 = null;
    public static BufferedImage walkRight3 = null;

    public static BufferedImage walkLeft1 = null;
    public static BufferedImage walkLeft2 = null;
    public static BufferedImage walkLeft3 = null;

    public BufferedImage currentImage = null;

    public int dx;
    public int dy;

    public boolean touchingGround;
    public AABB onTopOf;

    public boolean touchingRight;
    public AABB againstRight;

    public boolean touchingLeft;
    public AABB againstLeft;

    public int frameCounter;
    public int tickCounter;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        touchingGround = false;
        onTopOf = null;

        touchingRight = false;
        againstRight = null;

        touchingLeft = false;
        againstLeft = null;

        frameCounter = 1;
        tickCounter = 0;
    }

    public void tick() {
        tickCounter++;
        if(tickCounter == 5) {
            if(++frameCounter == 4) {
                frameCounter = 1;
            }
            tickCounter = 0;
        }
        if(!touchingGround) {
            dy = dy+GRAVITY;
        }
        y += dy;
        x += dx;

        if(dx > 0) {
            switch(frameCounter) {
                case 1:
                    currentImage = Player.walkRight1;
                    break;
                case 2:
                    currentImage = Player.walkRight2;
                    break;
                case 3:
                    currentImage = Player.walkRight3;
                    break;
                default:
                    currentImage = null;
                    break;
            }
        }
        else if(dx < 0) {
            switch(frameCounter) {
                case 1:
                    currentImage = Player.walkLeft1;
                    break;
                case 2:
                    currentImage = Player.walkLeft2;
                    break;
                case 3:
                    currentImage = Player.walkLeft3;
                    break;
                default:
                    currentImage = null;
                    break;
            }
        }
        else {
            currentImage = Player.standing;
        }

    }

    @Override
    public void render(Graphics g, int referenceFrame) {
        if(standing == null) {
            try {
                standing = ImageIO.read(new File("assets/standing.png"));

                walkRight1 = ImageIO.read(new File("assets/walkRight1.png"));
                walkRight2 = ImageIO.read(new File("assets/walkRight2.png"));
                walkRight3 = ImageIO.read(new File("assets/walkRight3.png"));

                walkLeft1 = ImageIO.read(new File("assets/walkLeft1.png"));
                walkLeft2 = ImageIO.read(new File("assets/walkLeft2.png"));
                walkLeft3 = ImageIO.read(new File("assets/walkLeft3.png"));

                currentImage = standing;
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        g.drawImage(currentImage,x-referenceFrame,y,WIDTH,HEIGHT,null);
    }
}
