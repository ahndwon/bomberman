import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {
    private static int gameState;
    private Map map;

    private Character player1;
    private Character player2;

    private PImage[] blocks;
    private PImage[] charactersMove;
    private PImage[] charactersStay;
    private PImage[] bombImage;

    private PImage explodeCenterImage;
    private PImage[] explodeUpImage;
    private PImage[] explodeRightImage;
    private PImage[] explodeDownImage;
    private PImage[] explodeLeftImage;

    private PImage speedItemImage;
    private PImage powerItemImage;
    private PImage bombItemImage;

    private boolean isRightPressed;
    private boolean isLeftPressed;
    private boolean isUpPressed;
    private boolean isDownPressed;

    private boolean isRightPressed2;
    private boolean isLeftPressed2;
    private boolean isUpPressed2;
    private boolean isDownPressed2;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    @Override
    public void settings() {
        size(Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
    }

    @Override
    public void setup() {
        background(255);
        map = new Map();
        player1 = new Character(1, 40, 40);
        player2 = new Character(2, 720, 520);

        blocks = new PImage[3];
        charactersMove = new PImage[20];
        charactersStay = new PImage[12];
        bombImage = new PImage[3];

        explodeUpImage = new PImage[2];
        explodeRightImage = new PImage[2];
        explodeDownImage = new PImage[2];
        explodeLeftImage = new PImage[2];
        explodeCenterImage = new PImage();

        speedItemImage = new PImage();
        powerItemImage = new PImage();
        bombItemImage = new PImage();

        PImage img1 = loadImage("./image/bomberman-block.png");
        PImage img2 = loadImage("./image/bomberman-movement.png");
        PImage img3 = loadImage("./image/bomberman-stay.jpg");
        PImage img4 = loadImage("./image/bomberman-effect.png");
        PImage img5 = loadImage("./image/bomberman-items.png");

        img1.loadPixels();
        img2.loadPixels();
        img3.loadPixels();
        img4.loadPixels();
        img5.loadPixels();

        blocks[0] = img1.get(0, 40, 40, 40);
        blocks[1] = img1.get(40, 0, 40, 40);
        blocks[2] = img1.get(80, 40, 40, 40);
        int k = 0;
        for (int j = 0; j < 128; j += 32) {
            for (int i = 0; i < 105; i += 21) {
                charactersMove[k] = img2.get(i, j, 21, 32);
                k++;
            }
        }
        k = 0;
        for (int j = 0; j < 128; j += 32) {
            for (int i = 0; i < 63; i += 21) {
                charactersStay[k] = img3.get(i, j, 21, 32);
                k++;
            }
        }

        bombImage[0] = img4.get(0, 0, 24, 24);
        bombImage[1] = img4.get(24, 0, 24, 24);
        bombImage[2] = img4.get(48, 0, 24, 24);

        explodeCenterImage = img4.get(0, 24 * 3, 24, 24);

        explodeUpImage[0] = img4.get(24 * 6, 24 * 3, 24, 24);
        explodeUpImage[1] = img4.get(24 * 7, 24 * 3, 24, 24);

        explodeRightImage[0] = img4.get(24 * 4, 24 * 3, 24, 24);
        explodeRightImage[1] = img4.get(24 * 3, 24 * 3, 24, 24);

        explodeDownImage[0] = img4.get(24 * 8, 24 * 3, 24, 24);
        explodeDownImage[1] = img4.get(24 * 7, 24 * 3, 24, 24);

        explodeLeftImage[0] = img4.get(24 * 2, 24 * 3, 24, 24);
        explodeLeftImage[1] = img4.get(24 * 3, 24 * 3, 24, 24);


        powerItemImage = img5.get(0, 0, 24, 24);
        bombItemImage = img5.get(24 * 2, 0, 24, 24);
        speedItemImage = img5.get(24 * 4, 0, 24, 24);

        gameState = 1;
    }

    @Override
    public void keyReleased() {
        switch (keyCode) {
            case UP:
                isUpPressed = false;
                player1.setMoving(false);
                break;
            case DOWN:
                isDownPressed = false;
                player1.setMoving(false);
                break;
            case RIGHT:
                isRightPressed = false;
                player1.setMoving(false);
                break;
            case LEFT:
                isLeftPressed = false;
                player1.setMoving(false);
                break;
            case 65:
                // A
                isLeftPressed2 = false;
                player1.setMoving(false);
                break;
            case 68:
                // D
                isRightPressed2 = false;
                player2.setMoving(false);
                break;
            case 87:
                // W
                isUpPressed2 = false;
                player2.setMoving(false);
                break;
            case 83:
                isDownPressed2 = false;
                player2.setMoving(false);
                // S
                break;
        }
    }

    @Override
    public void keyPressed() {
        switch (keyCode) {
            case UP:
                isUpPressed = true;
                break;
            case DOWN:
                isDownPressed = true;
                break;
            case RIGHT:
                isRightPressed = true;
                break;
            case LEFT:
                isLeftPressed = true;
                break;
            case 65:
                // A
                isLeftPressed2 = true;
                break;
            case 68:
                // D
                isRightPressed2 = true;
                break;
            case 87:
                // W
                isUpPressed2 = true;
                break;
            case 83:
                isDownPressed2 = true;
                // S
                break;
            case 17:
                // control
                player2.setBomb();
                break;
            case 76:
                player1.setBomb();
                break;
            case 82:
                resetGame();
                break;
        }
    }

    private void resetGame() {
        map = new Map();
        player1 = new Character(1, 40, 40);
        player2 = new Character(2, 720, 520);
        gameState = 1;
    }

    private void moveCharacter() {
        if (isRightPressed && player1.checkCollision(Constant.DIRECTION_RIGHT)) {
            player1.setMoving(true);
            player1.moveRight();
        } else if (isLeftPressed && player1.checkCollision(Constant.DIRECTION_LEFT)) {
            player1.setMoving(true);
            player1.moveLeft();
        } else if (isUpPressed && player1.checkCollision(Constant.DIRECTION_UP)) {
            player1.setMoving(true);
            player1.moveUp();
        } else if (isDownPressed && player1.checkCollision(Constant.DIRECTION_DOWN)) {
            player1.setMoving(true);
            player1.moveDown();
        }

        if (isRightPressed2 && player2.checkCollision(Constant.DIRECTION_RIGHT)) {
            player2.setMoving(true);
            player2.moveRight();
        } else if (isLeftPressed2 && player2.checkCollision(Constant.DIRECTION_LEFT)) {
            player2.setMoving(true);
            player2.moveLeft();
        } else if (isUpPressed2 && player2.checkCollision(Constant.DIRECTION_UP)) {
            player2.setMoving(true);
            player2.moveUp();
        } else if (isDownPressed2 && player2.checkCollision(Constant.DIRECTION_DOWN)) {
            player2.setMoving(true);
            player2.moveDown();
        }
    }


    @Override
    public void draw() {
        if (gameState == 1) {
            background(255);
            moveCharacter();
            map.drawMap(this, blocks, bombItemImage, powerItemImage, speedItemImage);
            player1.drawCharacter(this, charactersStay, charactersMove);
            player1.drawBomb(this, bombImage, explodeCenterImage, explodeUpImage, explodeRightImage, explodeDownImage, explodeLeftImage);
            player2.drawCharacter(this, charactersStay, charactersMove);
            player2.drawBomb(this, bombImage, explodeCenterImage, explodeUpImage, explodeRightImage, explodeDownImage, explodeLeftImage);
        } else if (gameState == -1) {
            background(0);
            textSize(26);
            text("GAME OVER \nREGAME= 'R'", Constant.WINDOW_WIDTH / 2 - 100, Constant.WINDOW_HEIGHT / 2);
        }
    }

    @Override
    public void mousePressed() {
        player1.setX(mouseX);
        player1.setY(mouseY);
    }

    public static void gameOver() {
        gameState = -1;
    }
}
