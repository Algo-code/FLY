import java.awt.image.BufferedImage;


public abstract class FlyingObject {
    private Controller.Types mainType;
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage image;
    private Controller.Types type;

    public Controller.Types getType() {
        return type;
    }

    public void setType(Controller.Types type) {
        this.type = type;
    }

    public Controller.Types getMainType() {
        return mainType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public abstract boolean outOfBounds();

    public abstract void move();

    public boolean shootBy(Bullet bullet){
        int x = bullet.getX();
        int y = bullet.getY();
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
    }

}