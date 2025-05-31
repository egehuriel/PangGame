import java.awt.*;
import javax.swing.ImageIcon;
public class Arrow {
    int x;
    int y;
    int speed = 15;
    boolean screen = true;
    Image arrow;

    public Arrow(int x, int y){
        this.x = x;
        this.y = y;
        try {
            arrow = new ImageIcon("arrow.png").getImage();
        } catch (Exception e) {
        }
    }
    public void move(){
        y = y - speed;
    }
    public void draw(Graphics g){
        Graphics2D g1 = (Graphics2D)g;
        g1.setComposite(AlphaComposite.SrcOver);
        g.drawImage(arrow, x-31,y,120,400,null);
    }

    public boolean remove(){
        return (y + 20) < 0;
    }

    public Rectangle bounds(){
        return new Rectangle(x + 14, y, 4, 20);
    }
}
