import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Sprite
{
    public static int x = 0;
    public static int y = 0;
    private Rectangle rect; // x, y, width, height
    private int xVelocity;
    private int yVelocity;
    private Image image;
    private boolean isElipse;

    public Sprite(int x, int y, ImageIcon image, int width, int height, int velocity)
    {
        this.rect = new Rectangle(x, y, width, height);
        this.image = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);;
        this.xVelocity = velocity;
        this.yVelocity = velocity;
    }

    public Sprite(int x, int y, int width, int height, int velocity, boolean isElipse)
    {
        this.rect = new Rectangle(x, y, width, height);
        this.xVelocity = velocity;
        this.yVelocity = velocity;
        this.isElipse = isElipse;
    }

    public void draw(Graphics2D g2D)
    {
        if (image != null)
        {
            g2D.drawImage(image, rect.x, rect.y, null);
        }
        else if (isElipse)
        {
            g2D.fillOval(rect.x, rect.y, rect.width, rect.height);
        }
        else
        {
            g2D.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    public void drawDynamic(Graphics2D g2D)
    {
        if (image != null)
        {
            g2D.drawImage(image, rect.x + Sprite.x, rect.y, null);
        }
        else if (isElipse)
        {
            g2D.fillOval(rect.x + Sprite.x, rect.y, rect.width, rect.height);
        }
        else
        {
            g2D.fillRect(rect.x + Sprite.x, rect.y, rect.width, rect.height);
        }
    }

    // getters and setters
    public int x() {return rect.x;}
    public int y() {return rect.y;}
    public int xVelocity() {return xVelocity;}
    public int yVelocity() {return yVelocity;}

    public int getWidth() {return rect.width;}
    public int getHeight() {return rect.height;}
    public Rectangle getRect() {return rect;}
    public Image getImg() {return image;}

    public int getLeft() {return x();}
    public int getRight() {return (int) rect.getMaxX();}
    public int getTop() {return y();}
    public int getBottom() {return (int) rect.getMaxY();}

    public void setX(int x) {rect.x = x;}
    public void setY(int y) {rect.y = y;}
    public void setXVelocity(int velocity) {this.xVelocity = velocity;}
    public void setYVelocity(int velocity) {this.yVelocity = velocity;}
    public void inverseXVelocity() {xVelocity *= -1;}
    public void inverseYVelocity() {yVelocity *= -1;}

    // Movement
    public void changeX(int amount)
    {
        rect.x += amount;
    }
    public void changeY(int amount)
    {
        rect.y += amount;
    }
    public void moveHorizontally()
    {
        rect.x += xVelocity;
    }

    public void moveVertically()
    {
        rect.y += yVelocity;
    }
    
    // Collision
    public boolean collides(Sprite other)
    {
        Rectangle rect1 = new Rectangle(rect.x + Sprite.x, rect.y);
        Rectangle rect2 = new Rectangle(other.rect.x + Sprite.x, rect.y);
        return rect1.intersects(rect2);
    }

    public ArrayList<Sprite> collidesWith(ArrayList<Sprite> sprites)
    {
        ArrayList<Sprite> collidesWith = new ArrayList<Sprite>();
        for (Sprite sprite : sprites)
        {
            if (rect.intersects(sprite.rect)) collidesWith.add(sprite);
        }
        return collidesWith;
    }

    public boolean onPlatform(ArrayList<Sprite> sprites)
    {
        rect.y += 2;
        boolean onPlatform = collidesWith(sprites).size() > 0;
        rect.y -= 2;
        return onPlatform;
    }

}