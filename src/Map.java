import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class Map {
    private static int[][] map;
    private static List<Block> blockMap;

    public Map() {
        map = new int[Constant.MAP_WIDTH][Constant.MAP_HEIGHT];
        blockMap = new ArrayList<>();

        outBlockCreate();
        innerBlockCreate();
        createItem(0);
        createFragileBlock();
        playerSetup();
        makeBlocks();
    }

    public static void showMap() {
        for (int i = 0; i < Constant.MAP_HEIGHT; i++) {
            for (int j = 0; j < Constant.MAP_WIDTH; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static Block getBlock(int x, int y) {
        Block block = null;
        for (int i = 0; i < blockMap.size(); i++) {
            if (blockMap.get(i).getPosX() == x && blockMap.get(i).getPosY() == y) {
                block = blockMap.get(i);
            }
        }
        return block;
    }

    private void outBlockCreate() {
        for (int i = 0; i < Constant.MAP_WIDTH; i++) {
            map[i][0] = Constant.BLOCK_NOT_FRAGILE;
            map[i][14] = Constant.BLOCK_NOT_FRAGILE;

        }
        for (int j = 0; j < Constant.MAP_HEIGHT; j++) {
            map[0][j] = Constant.BLOCK_NOT_FRAGILE;
            map[19][j] = Constant.BLOCK_NOT_FRAGILE;
        }
    }

    private void innerBlockCreate() {
        for (int i = 3; i < Constant.MAP_WIDTH - 3; i += 3) {
            for (int j = 3; j < Constant.MAP_HEIGHT; j += 3) {
                map[i][j] = Constant.BLOCK_NOT_FRAGILE;
            }
        }
    }

    private void playerSetup() {
        map[1][1] = Constant.PLAYER1;
        map[1][2] = map[2][1] = 0;
        map[18][13] = Constant.PLAYER1;
        map[17][13] = map[18][12] = 0;
    }

    private void createItem(int count) {

        while (true) {
            int i = (int) (random() * Constant.MAP_WIDTH);
            int j = (int) (random() * Constant.MAP_HEIGHT);
            if (i >= 1 && i < 19 && j >= 1 && j <= 14) {
                if (map[i][j] == 0 && count < 5) {
                    map[i][j] = Constant.ITEM_BOMB_NUMBER;
                    count++;
                } else if (map[i][j] == 0 && 5 <= count && count < 10) {
                    map[i][j] = Constant.ITEM_POWER;
                    count++;
                } else if (map[i][j] == 0 && 10 <= count && count < 15) {
                    map[i][j] = Constant.ITEM_SPEED;
                    count++;
                }

                if (count == 15)
                    break;
            }
        }
    }

    private void createFragileBlock() {
        for (int i = 0; i < 150; i++) {
            int a = (int) (1 + Math.random() * 18);
            int b = (int) (1 + Math.random() * 13);
            if (map[a][b] == 0) {
                map[a][b] = Constant.BLOCK_FRAGILE;
            }
        }
    }

    private void makeBlocks() {
        for (int i = 0; i < Constant.MAP_WIDTH; i++) {
            for (int j = 0; j < Constant.MAP_HEIGHT; j++) {
                switch (map[i][j]) {
                    case Constant.BLOCK_NOT_FRAGILE:
                        blockMap.add(new Block(false, Constant.BLOCK_NOT_FRAGILE, i, j));
                        break;
                    case Constant.BLOCK_FRAGILE:
                        blockMap.add(new Block(false, Constant.BLOCK_FRAGILE, i, j));
                        break;
                    case Constant.ITEM_BOMB_NUMBER:
                        blockMap.add(new Block(false, Constant.ITEM_BOMB_NUMBER, i, j));
                        break;
                    case Constant.ITEM_POWER:
                        blockMap.add(new Block(false, Constant.ITEM_POWER, i, j));
                        break;
                    case Constant.ITEM_SPEED:
                        blockMap.add(new Block(false, Constant.ITEM_SPEED, i, j));
                        break;
                    default:
                        blockMap.add(new Block(true, 0, i, j));
                        break;
                }
            }
        }
    }

    public void drawMap(PApplet p, PImage[] imgs, PImage bombNumberItemImage, PImage powerItemImage, PImage speedItemImage) {
        for (int i = 0; i < Constant.MAP_WIDTH; i++) {
            for (int j = 0; j < Constant.MAP_HEIGHT; j++) {
                if (map[i][j] == Constant.BLOCK_NOT_FRAGILE)
                    p.image(imgs[0], i * 40, j * 40);
//                if (map[i][j] == Constant.BLOCK_FRAGILE ||
//                        map[i][j] == Constant.ITEM_SPEED ||
//                        map[i][j] == Constant.ITEM_POWER ||
//                        map[i][j] == Constant.ITEM_BOMB_NUMBER)
//                    p.image(imgs[1], i * 40, j * 40);
                else {
                    Block block = getBlock(i, j);
                    if (block.getType() == Constant.BLOCK_FRAGILE && !block.isCanGo()) {
                        p.image(imgs[1], i * 40, j * 40);
                    }

                    if (block.getType() == Constant.ITEM_BOMB_NUMBER && !block.isCanGo()) {
                        p.image(imgs[1], i * 40, j * 40);
                    } else if (block.getType() == Constant.ITEM_BOMB_NUMBER && block.isCanGo()) {
                        p.image(bombNumberItemImage, i * 40, j * 40, 40, 40);
                    }
                    if (block.getType() == Constant.ITEM_POWER && !block.isCanGo()) {
                        p.image(imgs[1], i * 40, j * 40);
                    } else if (block.getType() == Constant.ITEM_POWER && block.isCanGo()) {
                        p.image(powerItemImage, i * 40, j * 40, 40, 40);
                    }

                    if (block.getType() == Constant.ITEM_SPEED && !block.isCanGo()) {
                        p.image(imgs[1], i * 40, j * 40);
                    } else if (block.getType() == Constant.ITEM_SPEED && block.isCanGo()) {
                        p.image(speedItemImage, i * 40, j * 40, 40, 40);
                    }
                }
            }
        }
    }
}

