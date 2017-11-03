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

    private List<Bomb> playerBombs;
    private static List<Bomb> allBombs;

    public Character(int playerType, int x, int y) {
        this.playerType = playerType;
        this.x = x;
        this.y = y;
        speed = 3;
        bombNumber = 1;
        bombPower = 5;
        isMoving = false;
        direction = 0;
        playerBombs = new ArrayList<>();
        allBombs = new ArrayList<>();
    }

    public void setBomb() {
        if (playerBombs.size() < bombNumber) {
            int posX = x / Constant.BLOCK_WIDTH;
            int posY = y / Constant.BLOCK_WIDTH;
            playerBombs.add(new Bomb(posX, posY));
            allBombs.add(playerBombs.get(playerBombs.size()-1));
            Map.setBombBlock(posX, posY);
            Map.showMap();
        }
    }

    public Bomb getBomb(int posX, int posY) {
        for (Bomb b : allBombs) {
            if (b.getX() == posX && b.getY() == posY) {
                return b;
            }
        }
        return null;
    }


    public void drawBomb(PApplet p, PImage[] bombImage, PImage explodeCenterImage, PImage[] explodeUpImage, PImage[] explodeRightImage, PImage[] explodeDownImage, PImage[] explodeLeftImage) {
        long endTime = System.currentTimeMillis();

        boolean rightExplosion = true;
        boolean leftExplosion = true;
        boolean upExplosion = true;
        boolean downExplosion = true;

        for (int i = 0; i < playerBombs.size(); i++) {

            Bomb bomb = playerBombs.get(i);
            int x = bomb.getX() * Constant.BLOCK_WIDTH;
            int y = bomb.getY() * Constant.BLOCK_WIDTH;
            p.image(bombImage[(int) (((endTime / 1000)) % 3)], x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);

            if (bomb.checkTimer(endTime) == 8) {
                p.image(explodeCenterImage, x, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                Map.getBlock(bomb.getX(), bomb.getY()).setExplosion(true);

                for (int j = 0; j < bombPower - 1; j++) {
                    int posX = bomb.getX() + j + 1 < 20 ? bomb.getX() + j + 1 : 19;

                    if (Map.getBlock(posX, bomb.getY()).getType() != 0) {
                        rightExplosion = false;
                        if (Map.getBlock(posX, bomb.getY()).getType() == Constant.BOMB) {
                            if (getBomb(posX, bomb.getY()) != null) {
                                getBomb(posX, bomb.getY()).setTimer(bomb.getTimer());
                                allBombs.remove(getBomb(posX, bomb.getY()));
                            }
                        }
                        if (Map.getBlock(posX, bomb.getY()).getType() != Constant.BLOCK_NOT_FRAGILE) {
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                        }
                        if (Map.getBlock(posX, bomb.getY()).getType() == Constant.BLOCK_FRAGILE) {
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                            Map.getBlock(posX, bomb.getY()).setExplosion(true);
                        }
                        break;
                    }
                    if (rightExplosion) {

                        if (j == bombPower - 2) {
                            p.image(explodeRightImage[0], posX * Constant.BLOCK_WIDTH, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                        } else {
                            p.image(explodeRightImage[1], posX * Constant.BLOCK_WIDTH, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                        }
                    }
                    Map.getBlock(posX, bomb.getY()).setExplosion(true);
                }

                for (int j = 0; j < bombPower - 1; j++) {
                    int posY = bomb.getY() + j + 1 < 20 ? bomb.getY() + j + 1 : 14;

                    if (Map.getBlock(bomb.getX(), posY).getType() != 0) {
                        downExplosion = false;
                        if (Map.getBlock(bomb.getX(), posY).getType() == Constant.BOMB) {
                            if (getBomb(bomb.getX(), posY) != null) {
                                getBomb(bomb.getX(), posY).setTimer(bomb.getTimer());
                                allBombs.remove(getBomb(bomb.getX(), posY));
                            }
                        }
                        if (Map.getBlock(bomb.getX(), posY).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);
                        if (Map.getBlock(bomb.getX(), posY).getType() == Constant.BLOCK_FRAGILE) {
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);
                            Map.getBlock(bomb.getX(), posY).setExplosion(true);
                        }
                        break;
                    }
                    if (downExplosion) {

                        if (j == bombPower - 2)
                            p.image(explodeDownImage[0], x, posY * Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                        else
                            p.image(explodeDownImage[1], x, posY * Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                    }
                    Map.getBlock(bomb.getX(), posY).setExplosion(true);
                }


                for (int j = 0; j < bombPower - 1; j++) {
                    int posX = bomb.getX() - (j + 1) < 0 ? 0 : bomb.getX() - (j + 1);

                    if (Map.getBlock(posX, bomb.getY()).getType() != 0)  {
                        leftExplosion = false;
                        if (Map.getBlock(posX, bomb.getY()).getType() == Constant.BOMB) {
                            if (getBomb(posX, bomb.getY()) != null) {
                                getBomb(posX, bomb.getY()).setTimer(bomb.getTimer());
                                allBombs.remove(getBomb(posX, bomb.getY()));
                            }
                        }
                        if (Map.getBlock(posX, bomb.getY()).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                        if (Map.getBlock(posX, bomb.getY()).getType() == Constant.BLOCK_FRAGILE) {
                            Map.getBlock(posX, bomb.getY()).setCanGo(true);
                            Map.getBlock(posX, bomb.getY()).setExplosion(true);
                        }
                        break;
                    }
                    if (leftExplosion) {

                        if (j == bombPower - 2)
                            p.image(explodeLeftImage[0], posX * Constant.BLOCK_WIDTH, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                        else
                            p.image(explodeLeftImage[1], posX * Constant.BLOCK_WIDTH, y, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                    }
                    Map.getBlock(posX, bomb.getY()).setExplosion(true);
                }

                for (int j = 0; j < bombPower - 1; j++) {
                    int posY = bomb.getY() - (j + 1) < 0 ? 0 : bomb.getY() - (j + 1);

                    if (Map.getBlock(bomb.getX(), posY).getType() != 0 ) {
                        upExplosion = false;
                        if (Map.getBlock(bomb.getX(), posY).getType() == Constant.BOMB) {
                            if (getBomb(bomb.getX(), posY) != null) {
                                getBomb(bomb.getX(), posY).setTimer(bomb.getTimer());
                                allBombs.remove(getBomb(bomb.getX(), posY));
                            }
                        }
                        if (Map.getBlock(bomb.getX(), posY).getType() != Constant.BLOCK_NOT_FRAGILE)
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);

                        if (Map.getBlock(bomb.getX(), posY).getType() == Constant.BLOCK_FRAGILE) {
                            Map.getBlock(bomb.getX(), posY).setCanGo(true);
                            Map.getBlock(bomb.getX(), posY).setExplosion(true);
                        }
                        break;
                    }
                    if (upExplosion) {
                        if (j == bombPower - 2)
                            p.image(explodeUpImage[0], x, posY * Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                        else
                            p.image(explodeUpImage[1], x, posY * Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH, Constant.BLOCK_WIDTH);
                    }
                    Map.getBlock(bomb.getX(), posY).setExplosion(true);
                }
            }

            if (bomb.checkTimer(endTime) == 0) {
                int bombX = bomb.getX();
                int bombY = bomb.getY();

                for (int j = 0; j < bombPower; j++) {
                    if (Map.getBlock(bombX + j, bombY) != null && Map.getBlock(bombX + j, bombY).isExplosion()) {
                        if (Map.getBlock(bombX + j, bombY).getType() != 0) {
                            Map.getBlock(bombX + j, bombY).setType(0);
                        }
                        Map.getBlock(bombX + j, bombY).setExplosion(false);
                    }
                    if (Map.getBlock(bombX - j, bombY) != null && Map.getBlock(bombX - j, bombY).isExplosion()) {
                        if (Map.getBlock(bombX - j, bombY).getType() != 0) {
                            Map.getBlock(bombX - j, bombY).setType(0);
                        }
                        Map.getBlock(bombX - j, bombY).setExplosion(false);
                    }
                    if (Map.getBlock(bombX, bombY + j) != null && Map.getBlock(bombX, bombY + j).isExplosion()) {
                        if (Map.getBlock(bombX, bombY + j).getType() != 0) {
                            Map.getBlock(bombX, bombY + j).setType(0);
                        }
                        Map.getBlock(bombX, bombY + j).setExplosion(false);
                    }

                    if (Map.getBlock(bombX, bombY - j) != null && Map.getBlock(bombX, bombY - j).isExplosion()) {
                        if (Map.getBlock(bombX, bombY - j).getType() != 0) {
                            Map.getBlock(bombX, bombY - j).setType(0);
                        }
                        Map.getBlock(bombX, bombY - j).setExplosion(false);
                    }
                }
                playerBombs.remove(i);
                Map.setNullBlock(bomb.getX(), bomb.getY());
            }
        }
    }

    public void drawCharacter(PApplet p, PImage[] stayImgs, PImage[] moveImgs) {
        if (Map.getBlock(x / Constant.BLOCK_WIDTH, y / Constant.BLOCK_WIDTH).isExplosion()) {
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

        int leftX = x / Constant.BLOCK_WIDTH;
        int topY = y / Constant.BLOCK_WIDTH;

        int rightX = (x + Constant.PLAYER_WIDTH) / Constant.BLOCK_WIDTH;
        int bottomY = (y + Constant.PLAYER_HEIGHT) / Constant.BLOCK_WIDTH;

        if ((y % Constant.BLOCK_WIDTH < 5 && (direction == Constant.DIRECTION_UP)
                || (y + Constant.PLAYER_HEIGHT) % Constant.BLOCK_WIDTH < 5 && direction == Constant.DIRECTION_DOWN)) {
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
        } else if ((x % Constant.BLOCK_WIDTH < 5 && (direction == Constant.DIRECTION_LEFT)
                || (x + Constant.PLAYER_WIDTH) % Constant.BLOCK_WIDTH < 5 && direction == Constant.DIRECTION_RIGHT)) {
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

        switch (Map.getBlock(x / Constant.BLOCK_WIDTH, y / Constant.BLOCK_WIDTH).getType()) {
            case Constant.ITEM_BOMB_NUMBER:
                bombNumber++;
                Map.getBlock(x / Constant.BLOCK_WIDTH, y / Constant.BLOCK_WIDTH).setType(0);
                break;
            case Constant.ITEM_POWER:
                bombPower += 4;
                Map.getBlock(x / Constant.BLOCK_WIDTH, y / Constant.BLOCK_WIDTH).setType(0);
                break;
            case Constant.ITEM_SPEED:
                speed++;
                Map.getBlock(x / Constant.BLOCK_WIDTH, y / Constant.BLOCK_WIDTH).setType(0);
                break;
        }

        return true;
    }
}
