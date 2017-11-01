import processing.core.PApplet;
import processing.core.PImage;

public class Block {
    private boolean canGo;
    private int type;
    private int posX;
    private int posY;
    private boolean isExplosion;

    public Block(boolean canGo, int type, int posX, int posY) {
        this.canGo = canGo;
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    public boolean isCanGo() {
        return canGo;
    }

    public void setCanGo(boolean canGo) {
        this.canGo = canGo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isExplosion() {
        return isExplosion;
    }

    public void setExplosion(boolean explosion) {
        isExplosion = explosion;
    }
}
