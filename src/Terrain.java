import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain extends AABB {

    public static BufferedImage grassTile = null;
    public static BufferedImage dirtTile = null;

    public Terrain(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    @Override
    public void render(Graphics g, int referenceFrame) {
        if(grassTile == null || dirtTile == null) {
            try {
                grassTile = ImageIO.read(new File("assets/grass.png"));
                dirtTile = ImageIO.read(new File("assets/dirt.png"));
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        // Draw the grass
        for(int i = x; i < x+width; i += 50) {
            g.drawImage(grassTile,i-referenceFrame,y,50,50,null);
        }

        // Fill in dirt below
        for(int i = x; i < x+width; i+=50) {
            for(int j = y+50; j < y+height; j+= 50) {
                g.drawImage(dirtTile,i-referenceFrame,j,50,50,null);
            }
        }
    }

}
