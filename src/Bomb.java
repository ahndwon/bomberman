import com.sun.tools.internal.jxc.ap.Const;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Bomb {
    private int x;
    private int y;
    private long timer;
    private List<int[]> bombArea;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        bombArea = new ArrayList<>();
        timer = System.currentTimeMillis();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getTimer() {
        return timer;
    }

    public int checkTimer(long currentTime) {
        if (currentTime - timer > 3800)
            return 0;
        if (currentTime - timer > 3000) {
            return 8;
        }
        return 1;
    }

    public List<int[]> getBombArea() {
        return bombArea;
    }
}


