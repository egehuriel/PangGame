import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.ImageIcon;
public class Bubble {
    int x;
    int y;
    int rad;
    int dx;
    int dy;
    Image bubble;

    public Bubble(int x, int y, int rad, int dx, int dy){
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.dx = dx;
        this.dy = dy;
        if(rad > 40){
            bubble = new ImageIcon("redbubble.png").getImage();
        }else if(rad > 20){
            bubble = new ImageIcon("bluebubble.png").getImage();
        }else{
            bubble = new ImageIcon("greenbubble.png").getImage();
        }
    }
    void move(){
        x = x + dx;
        y = y + dy;

        if(x <= 0 || x + rad * 2 >= 1000){
            dx = -dx;
        }
        dy += 1;
        if(dy > 30) dy = 30;
        if(dy < -30) dy = -30;
        if (y <= 0) {
            y = 0;
            dy = -dy;
        }
        if(y + rad * 2 >= 520){
            y = 520 - rad * 2;
            dy = -dy;
        }
    } 
    void draw(Graphics g){
        g.drawImage(bubble,x,y,rad *2, rad *2, null);
    }
    public Rectangle bounds(){
        return new Rectangle(x, y, rad *2, rad*2);
    }
    public List<Bubble> split(){
        List<Bubble> newBubbles = new ArrayList<>();
        if(rad > 20){
            int newrad = rad / 2;
            newBubbles.add(new Bubble(x, y, newrad, 3,-10));
            newBubbles.add(new Bubble(x,y,newrad,-3,-10));
        }
        return newBubbles;
    }
}
