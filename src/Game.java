import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public static final int STARTX = WIDTH/2;
    public static final int STARTY = 150;

    public static final double ticksPerSecond = 40;

    private int level;
    private int referenceFrame;

    private boolean running;

    private Thread thread;

    private List<Terrain> allTerrain;
    private List<Coin> allCoins;

    private Player player;

    public Game(int selectedLevel) {
        running = false;
        level = selectedLevel;
        referenceFrame = 0;
        player = new Player(STARTX, STARTY);

        allTerrain = new ArrayList<Terrain>();
        allCoins = new ArrayList<Coin>();

        addKeyListener(this);
        try {
            loadLevel(selectedLevel);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        setBackground(new Color(0xcaf0f8));
    }

    public synchronized void start() {
        if(running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if(!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private void loadLevel(int level) throws FileNotFoundException{
        String filename = "levels/" + level + ".csv";
        File levelFile = new File(filename);
        Scanner reader = new Scanner(levelFile);
        while(reader.hasNextLine()) {
            String data[] = reader.nextLine().split(",");
            if(data[0].equals("terrain")) {
                allTerrain.add(new Terrain(Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4])));
            }
            if(data[0].equals("coin")) {
                allCoins.add(new Coin(Integer.parseInt(data[1]),
                        Integer.parseInt(data[2])));
            }
        }
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1e9/ticksPerSecond;
        double delta = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                tick();
                delta--;
            }
            //render();
            repaint();
        }
        stop();
    }
    private void tick() {
        player.tick();
        referenceFrame = player.x - WIDTH/2;
        if(player.touchingGround) {
            if(!player.intersectsBelow(player.onTopOf)) {
                player.touchingGround = false;
                player.onTopOf = null;
            }
        }
        // Handle falling off the map
        else if(player.y > HEIGHT) {
            player.x = STARTX;
            player.y = STARTY;
        }
        if(player.touchingRight) {
            if(!player.intersectsRight(player.againstRight)) {
                player.touchingRight = false;
                player.againstRight = null;
            }
        }

        if(player.touchingLeft) {
            if(!player.intersectsLeft(player.againstLeft)) {
                player.touchingLeft = false;
                player.againstLeft = null;
            }
        }

        for(Coin c : allCoins) {
            if(player.intersects(c)) {
                c.collected = true;
            }
        }

        for(Terrain t : allTerrain) {
            if(!player.touchingGround) {
                if(player.intersectsBelow(t)) {
                    player.touchingGround = true;
                    player.onTopOf = t;
                    player.dy = 0;
                    if(player.y+player.height > t.y) {
                        player.y = t.y-player.height;
                    }
                }
            }
            if(!player.touchingRight) {
                if(player.onTopOf != t && player.intersectsRight(t)) {
                    player.touchingRight = true;
                    player.againstRight = t;
                    player.dx = 0;

                    if(player.x+player.width > t.x) {
                        player.x = t.x-player.width;
                    }

                }
            }
            if(!player.touchingLeft) {
                if(player.onTopOf != t && player.intersectsLeft(t)) {
                    player.touchingLeft = true;
                    player.againstLeft = t;
                    player.dx = 0;

                    if(player.x < (t.x+t.width)) {
                        player.x = t.x+t.width;
                    }
                }
            }
        }

    }

    public void paint(Graphics g) {
        player.render(g, referenceFrame);
        for(Terrain t : allTerrain) {
            t.render(g, referenceFrame);
        }
        for(Coin c : allCoins) {
            c.render(g, referenceFrame);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:

                if(!player.touchingGround) {
                    break;
                }


                player.dy = Player.JUMPSPEED;
                player.touchingGround = false;
                player.onTopOf = null;
                break;
            case KeyEvent.VK_LEFT:
                player.dx = - Player.WALKSPEED;
                break;
            case KeyEvent.VK_RIGHT:
                if(!player.touchingRight) {
                    player.dx = Player.WALKSPEED;
                }
                break;
            default:
                return;



        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                player.dx = 0;
                break;
            default:
                return;
        }
    }
}
