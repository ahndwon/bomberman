import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private int playerType;
    private boolean isMoving;
    private int speed;
    private int bombNumber;
    private int bombPower;
    private int x;
    private int y;
    private int direction;
    private int i = 0;

    private List<Bomb> bombs;

    public Character(int playerType, int x, int y) {
        this.playerType = playerType;
        this.x = x;
        this.y = y;
        speed = 3;
        bombNumber = 1;
        bombPower = 5;
        isMoving = false;
        direction = 0;
        bombs = new ArrayList<>();
    }

    public void setBomb() {
        if (bombs.size() < bombNumber) {
            int posX = x / 40;
            int posY = y / 40;
            bombs.add(new Bomb(posX, posY));
        }
    }


    public void drawBomb(PApplet p, PImage[] bombImage, PImage explodeCenterImage, PImage[] explodeUpImage, PImage[] explodeRightImage, PImage[] explodeDownImage, PImage[] explodeLeftImage) {
        long endTime = System.currentTimeMillis();

        boolean rightExplosion = true;
        boolean leftExplosion = true;
        boolean upExplosion = true;
        boolean downExplosion = true;

        for (int i = 0; i < bombs.size(); i++) {

            Bomb bomb = bombs.get(i);
            int x = bomb.getX() * 40;
            int y = bomb.getY() * 40;
            p.image(bombImage[(int) (((endTime / 1000)) % 3)], x, y, 40, 40);

            if (bomb.checkTimer(endTime) == 8) {

                p.image(explodeCenterImage, x, y, 40, 40);
                Map.getBlock(bomb.getX(), bomb.getY()).setExplosion(true);

                for (int j = 0; j < bombPower - 1; j++) {
                    int posX = bomb.getX() + j + 1 < 20 ? bomb.getX() + j + 1 : 19;
                    int type = Map.getBlock(posX, bomb.getY()).getType();
//                    if (type == 9 || type == 2
//                            || (type == 6 && Map.getBlock(posX, bomb.getY()).isCanGo())
//                            || (type == 7 && Map.getBlock(posX, bomb.getY()).isCanGo())
//                            || (type == 8 && Map.getBlock(posX, bomb.getY()).isCanGo())) {
                    if (Map.getBlock(posX, bomb.getY()).getType() != 0) {
                        rightExplosion = false;
                        if (Map.getBlock(posX, bomb.getY()).getType() != Constant.BLOCK_NOT_FRAGILE) {
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                        }
                        if (Map.getBlock(posX, bomb.getY()).getType() == Constant.BLOCK_FRAGILE) {
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                        }
//                        Map.showMap();
                        break;
                    }
                    if (rightExplosion) {
                        Map.getBlock(posX, bomb.getY()).setExplosion(true);
                        if (j == bombPower - 2) {
                            p.image(explodeRightImage[0], posX * 40, y, 40, 40);
                        } else {
                            p.image(explodeRightImage[1], 40 * posX, y, 40, 40);
                        }
                    }
                }

                for (int j = 0; j < bombPower - 1; j++) {
                    int posY = bomb.getY() + j + 1 < 20 ? bomb.getY() + j + 1 : 14;
                    if (Map.getBlock(bomb.getX(), posY).getType() != 0) {
                        downExplosion = false;
                        if (Map.getBlock(bomb.getX(), posY).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);
                        break;
                    }
                    if (downExplosion) {
                        Map.getBlock(bomb.getX(), posY).setExplosion(true);
                        if (j == bombPower - 2)
                            p.image(explodeDownImage[0], x, posY * 40, 40, 40);
                        else
                            p.image(explodeDownImage[1], x, 40 * posY, 40, 40);
                    }
                }


                for (int j = 0; j < bombPower - 1; j++) {
                    int posX = bomb.getX() - (j + 1) < 0 ? 0 : bomb.getX() - (j + 1);
                    if (Map.getBlock(posX, bomb.getY()).getType() != 0) {
                        leftExplosion = false;
                        if (Map.getBlock(posX, bomb.getY()).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                        break;
                    }
                    if (leftExplosion) {
                        Map.getBlock(posX, bomb.getY()).setExplosion(true);
                        if (j == bombPower - 2)
                            p.image(explodeLeftImage[0], posX * 40, y, 40, 40);
                        else
                            p.image(explodeLeftImage[1], 40 * posX, y, 40, 40);
                    }
                }

                for (int j = 0; j < bombPower - 1; j++) {
                    int posY = bomb.getY() - (j + 1) < 0 ? 0 : bomb.getY() - (j + 1);
                    if (Map.getBlock(bomb.getX(), posY).getType() != 0) {
                        upExplosion = false;
                        if (Map.getBlock(bomb.getX(), posY).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);
                        break;
                    }
                    if (upExplosion) {
                        Map.getBlock(bomb.getX(), posY).setExplosion(true);
                        if (j == bombPower - 2)
                            p.image(explodeUpImage[0], x, posY * 40, 40, 40);
                        else
                            p.image(explodeUpImage[1], x, 40 * posY, 40, 40);
                    }
                }
            }

            if (bomb.checkTimer(endTime) == 0) {
                int bombX = bomb.getX();
                int bombY = bomb.getY();

                for (int j = 0; j < bombPower; j++) {
                    if (Map.getBlock(bombX + j, bombY).getType() != 0) {
                        Map.getBlock(bombX + j, bombY).setType(0);
                    }
                    if (Map.getBlock(bombX + j, bombY) != null && Map.getBlock(bombX + j, bombY).isExplosion()) {
                        System.out.println("j : "+ j + " " + Map.getBlock(bombX + j, bombY).getType());

                        Map.getBlock(bombX + j, bombY).setExplosion(false);
                    }
                    if (Map.getBlock(bombX - j, bombY) != null && Map.getBlock(bombX - j, bombY).isExplosion()) {
                        System.out.println(Map.getBlock(bombX - j, bombY).getType());
                        if (Map.getBlock(bombX - j, bombY).getType() != 0) {
                            Map.getBlock(bombX - j, bombY).setType(0);
                        }
                        Map.getBlock(bombX - j, bombY).setExplosion(false);
                    }
                    if (Map.getBlock(bombX, bombY + j) != null && Map.getBlock(bombX, bombY + j).isExplosion()) {
                        System.out.println(Map.getBlock(bombX, bombY + j).getType());
                        if (Map.getBlock(bombX, bombY + j).getType() != 0) {
                            Map.getBlock(bombX, bombY + j).setType(0);
                        }
                        Map.getBlock(bombX, bombY + j).setExplosion(false);
                    }

                    if (Map.getBlock(bombX, bombY - j) != null && Map.getBlock(bombX, bombY - j).isExplosion()) {
                        System.out.println(Map.getBlock(bombX, bombY - j).getType());
                        if (Map.getBlock(bombX, bombY - j).getType() != 0) {
                            Map.getBlock(bombX, bombY - j).setType(0);
                        }
                        Map.getBlock(bombX, bombY - j).setExplosion(false);
                    }
                }
                bombs.remove(i);
            }
        }
    }

    public void drawCharacter(PApplet p, PImage[] stayImgs, PImage[] moveImgs) {
        if (Map.getBlock(x / 40, y / 40).isExplosion()) {
            Main.gameOver();
        }

        long endTime = System.currentTimeMillis();
        if (endTime % 10 == 0)
            i++;

        if (!isMoving) {
            p.image(stayImgs[direction * 3 + i % 3], x, y, Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT);
        } else {
            p.image(moveImgs[direction * 5 + i % 5], x, y, Constant.PLAYER_WIDTH, Constant.PLAYER_HEIGHT);
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void moveRight() {
        this.x += speed;
    }

    public void moveLeft() {
        this.x -= speed;
    }

    public void moveUp() {
        this.y -= speed;
    }

    public void moveDown() {
        this.y += speed;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean checkCollision(int direction) {
        this.direction = direction;

        int leftX = x / 40;
        int topY = y / 40;

        int rightX = (x + Constant.PLAYER_WIDTH) / 40;
        int bottomY = (y + Constant.PLAYER_HEIGHT) / 40;

        if ((y % 40 < 5 && (direction == Constant.DIRECTION_UP)
                || (y + Constant.PLAYER_HEIGHT) % 40 < 5 && direction == Constant.DIRECTION_DOWN)) {
            if (direction == Constant.DIRECTION_UP) {
                Block block1 = Map.getBlock(leftX, topY - 1);
                Block block2 = Map.getBlock(rightX, topY - 1);
                if (!block1.isCanGo() || !block2.isCanGo()) {
                    return false;
                }
            } else if (direction == Constant.DIRECTION_DOWN) {
                Block block1 = Map.getBlock(leftX, bottomY);
                Block block2 = Map.getBlock(rightX, bottomY);
                if (!block1.isCanGo() || !block2.isCanGo()) {
                    return false;
                }
            }
        } else if ((x % 40 < 5 && (direction == Constant.DIRECTION_LEFT)
                || (x + Constant.PLAYER_WIDTH) % 40 < 5 && direction == Constant.DIRECTION_RIGHT)) {
            if (direction == Constant.DIRECTION_RIGHT) {
                Block block1 = Map.getBlock(rightX, topY);
                Block block2 = Map.getBlock(rightX, bottomY);
                if (!block1.isCanGo() || !block2.isCanGo()) {
                    return false;
                }
            } else if (direction == Constant.DIRECTION_LEFT) {
                Block block1 = Map.getBlock(leftX - 1, topY);
                Block block2 = Map.getBlock(leftX - 1, bottomY);
                if (!block1.isCanGo() || !block2.isCanGo()) {
                    return false;
                }
            }
        }

//        System.out.println(Map.getBlock((leftX + rightX) / 2, topY).getType());
//        System.out.println(Map.getBlock((leftX + rightX) / 2, bottomY).getType());
//        System.out.println(Map.getBlock(rightX, (topY + bottomY) / 2).getType());
//        System.out.println(Map.getBlock(leftX, (topY + bottomY) / 2).getType());
        switch (Map.getBlock(x / 40, y / 40).getType()) {
            case Constant.ITEM_BOMB_NUMBER:
                bombNumber++;
                Map.getBlock(x / 40, y / 40).setType(0);
                break;
            case Constant.ITEM_POWER:
                bombPower += 4;
                Map.getBlock(x / 40, y / 40).setType(0);
                break;
            case Constant.ITEM_SPEED:
                speed++;
                Map.getBlock(x / 40, y / 40).setType(0);
                break;
        }

        return true;
    }
}
