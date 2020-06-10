import java.awt.*;

public class AABB {

    public int x;
    public int y;
    public int width;
    public int height;

    public AABB(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g, int referenceFrame) {
        g.drawRect(x-referenceFrame, y, width, height);
    }

    public boolean intersects(AABB other) {
        return !(other.x > (x+width)
                || (other.x+other.width) < x
                || other.y > (y+height)
                || (other.y+other.height) < y);
    }

    public boolean intersectsBelow(AABB other) {
        if(x >= (other.x+other.width)) {
            return false;
        }
        if(x+width <= other.x) {
            return false;
        }

        // underneath
        if(y > other.y) {
            return false;
        }

        if(y+height >= other.y) {
            return true;
        }
        return false;
    }
    public boolean intersectsRight(AABB other) {
        if(y > (other.y+other.height)) {
            return false;
        }
        if(y+height<other.y) {
            return false;
        }
        if(x > other.x) {
            return false;
        }

        if(x+width >= other.x) {
            return true;
        }
        return false;
    }

    public boolean intersectsLeft(AABB other) {
        return other.intersectsRight(this);
    }

}
