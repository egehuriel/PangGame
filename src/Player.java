import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class Player {
    int x;
    int y;
    Image playerstill;
    Image playershotting;
    Image playerstill2;
    Image playerstillleft;
    Image playerstillleft2;
    boolean shotting = false;
    boolean walk = false;
    boolean direction = false;
    boolean direction1 = false;
    int live = 3;
    boolean hit = false;
    Timer hittimer;
    boolean hit1 = true;
    boolean invincible = false;
    public Player(){
        x = 100;
        y = 450;
        try {
            playerstill = new ImageIcon("playerstill.png").getImage();
            playershotting = new ImageIcon("playershotting.png").getImage();
            playerstill2 = new ImageIcon("playerstill2.png").getImage();
            playerstillleft = new ImageIcon("playerstillleft.png").getImage();
            playerstillleft2 = new ImageIcon("playerstillleft2.png").getImage();
        } catch (Exception e) {
            System.err.println("hata player çizim consturcytor");
        }
    }
    public void draw(Graphics g){
        Graphics2D g1 = (Graphics2D) g;
        g1.setComposite(AlphaComposite.SrcOver);
        if(hit == true && !hit1){
            return;
        }
        if(shotting == true){
            g.drawImage(playershotting, x, y, 64, 64, null);
        }else if(walk == true && direction == true){
            if(direction1){
                g.drawImage(playerstill2, x, y, 64,64, null);
            }else{
                g.drawImage(playerstill,x,y, 64,64,null);
            }
        }else if(walk == true && direction == false){
            if(direction1 == true){
                g.drawImage(playerstillleft, x, y, 64, 64, null);
            }else{
                g.drawImage(playerstillleft2, x, y, 64, 64, null);
            }
        } 
        else{
            g.drawImage(playerstill, x,y,64,64,null);
        }
    }

    public void moveright(){
        x = x + 20;
        if(shotting == false){
            walk = true;
            direction = true;
            direction1 = !direction1;
        }
    }
    public void moveleft(){
        x = x - 20;
        if(shotting==false){
            walk = true;
            direction = false;
            direction1 = !direction1;
        }
    }
    public Arrow shoot(){
        shotting = true;
        return new Arrow(x,y);
    }

        public int getlive(){
        return live;
    }

    public void loselive(){
        live--;
        hit = true;
        hit1 = true;
        invincible = true;
        if(hittimer != null && hittimer.isRunning() == true){
            hittimer.stop();
        }
        hittimer = new Timer(100, new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent e){
                hit1 = !hit1;
                count++;
                if(count > 15){
                    hit = false;
                    hit1 = true;
                    hittimer.stop();
                    invincible = false;
                }
            }
        });
        hittimer.start();
    }



}


