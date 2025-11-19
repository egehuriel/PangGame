import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    Font font1;
    Clip clip1;
    Clip clip2;
    Clip clip3;
    Clip clip4;
    Clip clip5;
    Clip clip6;
    Clip clip7;
    Clip clip8;
    Clip clip9;
    Clip clip10;
    int time = 100;
    int fps = 0;
    Player player;
    List<Bubble> bubble;
    List<Arrow> arrow;
    Timer timer;
    int level;
    int point;
    boolean gameover = false;
    Member current;
    Image level1background;
    Image level2background;
    Image level3background;
    Image level4background;
    Image level5background;
    ImageIcon resizeimage;
    boolean start = true;
    int startcountdown = 3;
    Timer startcountdowntimer;
    boolean levelupa = false;
    boolean pause = false;
    boolean gamefinish = false;
    boolean mute = false;



    public GamePanel(Member current){
        ImageIcon icon = new ImageIcon("pangicon.png");
        Image smallimage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        resizeimage = new ImageIcon(smallimage);
        this.current = current;
        this.player = new Player();
        this.bubble = new ArrayList<>(); 
        this.arrow = new ArrayList<>();
        if(current != null){
            level = current.getLevel();
            point = current.getPoint();
        }else{
            level = 1;
            point = 0;
        }
        setPreferredSize(new Dimension(1000, 750));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                    player.moveleft();
                } else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                    player.moveright();
                } else if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    arrow.add(player.shoot());
                }else if(key == KeyEvent.VK_ESCAPE){
                    pause = !pause;
                    if(pause == true){
                        timer.stop();
                    }else{
                        timer.start();
                    }
                    repaint();
                }else if (key == KeyEvent.VK_M) {
                    mute = !mute;
                    if(mute == true){
                        stopmusic();
                    }else{
                        if(level == 1) level1backgroundmusic();
                        else if(level == 2) level2backgroundmusic();
                        else if(level == 3) level3backgroundmusic();
                        else if(level == 4) level4backgroundmusic();
                        else if(level == 5) level5backgroundmusic();
                    }
                }else if (key == KeyEvent.VK_N) {
                    mute = !mute;
                    if(mute == true){
                        stopsound();
                    }else{
                        if(level == 1) level1backgroundmusic();
                        else if(level == 2) level2backgroundmusic();
                        else if(level == 3) level3backgroundmusic();
                        else if(level == 4) level4backgroundmusic();
                        else if(level == 5) level5backgroundmusic();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    player.shotting = false;
                }
                if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
                    player.walk = false;
                }
            }

            
        });
        try{
            font1 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/ARCADE_I.TTF")).deriveFont(20f);
        }catch(Exception e){
            System.err.println("hata font yukeleme constructor game panek");
            font1 = new Font("Arial", Font.BOLD, 20);
        }
        try {
            level1background = new ImageIcon("level1background.png").getImage();
            level2background = new ImageIcon("level2background.png").getImage();
            level3background = new ImageIcon("level3background.png").getImage();
            level4background = new ImageIcon("level4background.png").getImage();
            level5background = new ImageIcon("level5background.png").getImage();
        } catch (Exception e) {
            System.err.print("level background  hata");
        }
        timer = new Timer(1000 / 60, e-> {
            update(); repaint(); fps++;
            if(fps >= 60){
                time--;
                fps = 0;
                if(time <= 0){
                    gameover();
                }
            }
            
        });
        startcountdowntimer = new Timer(1000, e-> {
            startcountdown--;
            if(startcountdown < -1){
                startcountdowntimer.stop();
                start = false;
                timer.start();
            }
            repaint();
        });
        if(current == null || level == 0){
          level = 1; 
          level1();  
        } else{
        if(current.level == 1) level1();
        else if(current.level == 2) level2();
        else if(current.level == 3) level3();
        else if(current.level == 4) level4();
        else if(current.level == 5) level5();
        }
        startcountdowntimer.start();
        try {
        if(current.getUsername().equals("admin")){
            player.live = 100;
        }
        } catch (Exception e) {
            System.err.println("gormezden gel (admin test)");
        }
    }
    public void paint(Graphics g){
        super.paintComponent(g);
        g.setFont(font1);
        if(level == 1 && level1background !=null){
            g.drawImage(level1background, 0,0,getWidth(),getHeight(),null);
        }else if(level == 2 && level2background != null){
            g.drawImage(level2background, 0,0,getWidth(),getHeight(), null);
        }else if(level == 3 && level3background !=null){
            g.drawImage(level3background,0,0,getWidth(),getHeight(),null);
        }else if(level == 4 && level4background != null){
            g.drawImage(level4background,0,0,getWidth(),getHeight(),null);
        }else if(level == 5 && level5background != null ){
            g.drawImage(level5background,0,0,getWidth(),getHeight(),null);
        }
        g.setFont(font1.deriveFont(40f));
        g.setColor(Color.BLACK);
        g.drawString("TIME :" + time, 630,60);
        g.setColor(Color.WHITE);
        g.drawString("TIME :" + time, 628,62);
        if(level == 5){
            g.setFont(font1.deriveFont(40f));
            g.setColor(Color.WHITE);
            g.drawString("TIME :" + time, 630,60);
            g.setColor(Color.BLACK);
            g.drawString("TIME :" + time, 628,62);
        }
        player.draw(g);
        for(Arrow arrow : arrow){
            arrow.draw(g);
        }
        for(Bubble bubble : bubble){
            bubble.draw(g);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,540,getWidth(),1000);
        g.setColor(Color.WHITE);
        g.drawString("LIVES: " + player.live, 20, 650);
        g.drawString("SCORE: " + point, 20,700);
        if(gameover == true){
            g.setColor(Color.RED);
            g.setFont(font1.deriveFont(90f));
            g.drawString("GAME OVER", getWidth()/2-400, getHeight()/2-150);
        }
        if(levelupa == true && level <= 4){
            g.setColor(Color.RED);
            g.setFont(font1.deriveFont(100f));
            g.drawString("LEVEL", getWidth()/2-400, getHeight()/2-150);
            g.drawString("COMPLETED", getWidth()/2-400, getHeight()/2);
        }
        if(start == true) {
            g.setColor(Color.WHITE);
            g.setFont(font1.deriveFont(110f));
            String countdown = startcountdown > 0 ? String.valueOf(startcountdown) : "START";
            FontMetrics fm = g.getFontMetrics();
            int startX = (getWidth() - fm.stringWidth(countdown)) / 2;
            int startY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(countdown, startX, startY);
        }
        if(pause == true){
                g.setColor(Color.WHITE);
                g.setFont(font1.deriveFont(100f));
                g.drawString("PAUSED", getWidth()/2 - 250, getHeight()/2 - 100);
                g.setColor(Color.BLACK);
                g.drawString("PAUSED", getWidth()/2 - 249, getHeight()/2 - 99);
            }
            if(levelupa == true && level == 5){
                g.setColor(Color.WHITE);
                g.setFont(font1.deriveFont(80f));
                g.drawString("YOU WIN", getWidth()/2 -150, getHeight()/2  -200);
                g.setColor(Color.BLACK);
                g.drawString("YOU WIN", getWidth()/2 -148, getHeight()/2 -202);
                
            }

    }
    void update(){
        if(start == true) return;
        repaint();
        for(int i = 0; i < arrow.size(); i++){
            for(int j = 0; j < bubble.size(); j++){
                if(arrow.get(i).bounds().intersects(bubble.get(j).bounds())){
                Bubble hit = bubble.remove(j);
                arrow.remove(i);
                i--;
                if(hit.rad > 40){
                    point = point + 100;
                }else if(hit.rad > 20){
                    point = point + 500;
                }else{
                    point = point + 1000;
                }
                bubble.addAll(hit.split());
                bubblepopsoundeffect();
                break;
                }
            }
        }
        for(Bubble bubble : bubble){
            bubble.move();
        }
        for(Arrow arrow : arrow){
            arrow.move();
        }
        Rectangle playerBounds = new Rectangle(player.x, player.y, 40, 40);
            for (int i = 0; i < bubble.size(); i++) {
                if(bubble.get(i).bounds().intersects(playerBounds) && !player.invincible) {
                    Bubble hit = bubble.remove(i);
                    player.loselive();
                    bubble.addAll(hit.split());
                    if (player.getlive() <= 0) {
                        gameover();
                    }
                    losinglivesoundeffect();
                    break;
                }
            }
            if(player.live <= 0){
                gameover();
            }
            if(time <= 0){
                timer.stop();
                gameover();
                return;
            }
            if(bubble.isEmpty()){
                levelup();
            }
    }
    void levelup(){
        timer.stop();
        save();
        stopmusic();
        levelupa = true;
        if(level <= 4){
    
            levelupsoundeffect();
            Timer leveluptimer = new Timer(3000, e -> {
                JOptionPane.showMessageDialog(this, "LEVEL "+ level + " COMPLETED! -- LEVEL " + (level + 1) + " STARTS!!", "LEVEL UP!!", JOptionPane.INFORMATION_MESSAGE, resizeimage); 
                level++;
                time = 100;
                player.x = 100;
                player.y = 450;
                if(player.live < 3){
                    player.live = 3;
                }
                if(level == 1){
                    level1();
                } else if(level == 2){
                    level2();
                } else if(level == 3){
                    level3();
                } else if(level == 4){
                    level4();
                } else if(level == 5){
                    level5();
                }
                levelupa = false;
                timer.start();
            });
            leveluptimer.setRepeats(false);
            leveluptimer.start();
            
        }
        if(level >= 5){
            gamefinish = true;
            gamefinishedsoundeffect();
            stopmusic();
            repaint();
            Timer finish = new Timer(5000, e -> {
                int option = JOptionPane.showOptionDialog(this, "CONGRATULATIONS!\nYOU FINISHED THE GAME!", "GAME COMPLETED", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, resizeimage, new String[]{"GO BACK TO MENU", "EXIT"}, "GO BACK TO MENU");
                if (option == 0) {
                    SwingUtilities.getWindowAncestor(this).dispose(); 
                    stopmusic();
                } else if (option == 1) {
                    System.exit(0);
                }
            });
            finish.setRepeats(false);
            finish.start();
            return;
        }

    }
    void gameover(){
        save();
        gameover = true;
        repaint();
        timer.stop();
        int option = JOptionPane.showOptionDialog(this, "TRY AGAIN", "GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, resizeimage, new String[]{"TRY AGAIN", "EXIT"}, "TRY AGAIN");
        if(option == 0 ){
            gameover = false;
            time = 100;
            player = new Player();
            bubble.clear();
            arrow.clear();
            if(current != null){
                level = current.getLevel();
            }else{
                level = 1;
            }
            if(level == 1) level1();
            if(level == 2) level2();
            if(level == 3) level3();
            if(level == 4) level4();
            if(level == 5) level5();
            repaint();
            timer.start();
        }else{
            System.exit(ABORT);
        }
    }
    void save(){
        if(current == null){
            return;
        }
        current.setPoint(point);
        current.setLevel(level);
        current.setLive(player.live);
        User user = new User();
        try{
            user.load("log.txt");
            user.updateuser(current);
            user.save("log.txt");
        }catch(Exception e){
            System.err.println("hata save gamepanel (log.txt)");
        }





    }
    void level1(){
        start = true;
        startcountdown = 3;
        startcountdowntimer.restart();
        startsoundeffect();
        if(clip2 != null && clip2.isRunning()){
            clip2.stop();
        }
        if(clip3 != null && clip3.isRunning()){
            clip3.stop();
        }
        level1backgroundmusic();
        bubble.add(new Bubble(400,150,50,3,-10));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(600,100,30,2,-6));
        repaint();
        if(player.live == 0){
            gameover();
        }
    }
    void level2(){
        if(clip1 != null && clip1.isRunning()){
            clip1.stop();
        }
        if(clip3 != null && clip3.isRunning()){
            clip3.stop();
        }
        start = true;
        startcountdown = 3;
        startcountdowntimer.restart();
        startsoundeffect();
        if(player.live == 0){
            gameover();
        }
        level2backgroundmusic();
        bubble.add(new Bubble(400,150,50,3,-10));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(456,90,40,2,-6));
        bubble.add(new Bubble(350,100,42,2,-6));
        bubble.add(new Bubble(420,100,80,2,-6));
        bubble.add(new Bubble(480,100,55,2,-6));
        repaint();
    }
    void level3(){
        if(player.live <= 3){
            player.live = 4;
        }
        if(clip1 != null && clip1.isRunning()){
            clip1.stop();
        }
        if(clip2 != null && clip2.isRunning()){
            clip2.stop();
        }
        if(clip4 != null && clip4.isRunning()){
            clip4.stop();
        }
        level3backgroundmusic();
        start = true;
        startcountdown = 3;
        startcountdowntimer.restart();
        startsoundeffect();
        if(player.live == 0){
            gameover();
        }
        bubble.add(new Bubble(400,150,95,3,-10));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(341,100,60,2,-6));
        bubble.add(new Bubble(534,100,48,2,-6));
        bubble.add(new Bubble(600,100,30,2,-6));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(677,123,82,2,-6));
        bubble.add(new Bubble(600,100,100,2,-6));
        repaint();        
    }
    void level4(){
        if(player.live <= 3){
            player.live = 5;
        }
        if(clip1 != null && clip1.isRunning()){
            clip1.stop();
        }
        if(clip2 != null && clip3.isRunning()){
            clip2.stop();
        }
        if(clip3 != null && clip3.isRunning()){
            clip3.stop();
        }
        if(clip5 != null && clip5.isRunning()){
            clip5.stop();
        }
        level4backgroundmusic();
        start = true;
        startcountdown = 3;
        startcountdowntimer.restart();
        startsoundeffect();
        if(player.live == 0){
            gameover(); 
        }
        bubble.add(new Bubble(400,150,95,3,-10));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(341,100,60,2,-6));
        bubble.add(new Bubble(534,100,48,2,-6));
        bubble.add(new Bubble(600,100,30,2,-6));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(677,123,82,2,-6));
        bubble.add(new Bubble(600,100,100,2,-6));
        bubble.add(new Bubble(456,110,55,2,-6));
        repaint();
        
    }
    void level5(){
        if(player.live <= 3){
            player.live = 6;
        }
        if(clip1 != null && clip1.isRunning()){
            clip1.stop();
        }
        if(clip2 != null && clip2.isRunning()){
            clip2.stop();
        }
        if(clip3 != null && clip3.isRunning()){
            clip3.stop();
        }
        if(clip4 != null && clip4.isRunning()){
            clip4.stop();
        }
        level5backgroundmusic();
        start = true;
        startcountdown = 3;
        startcountdowntimer.restart();
        startsoundeffect();
        if(player.live == 0){
            gameover();
        }
        bubble.add(new Bubble(400,150,95,3,-10));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(341,100,60,2,-6));
        bubble.add(new Bubble(534,100,48,2,-6));
        bubble.add(new Bubble(600,100,30,2,-6));
        bubble.add(new Bubble(550,130,60,3,-10));
        bubble.add(new Bubble(677,123,82,2,-6));
        bubble.add(new Bubble(600,100,100,2,-6));
        bubble.add(new Bubble(456,110,55,2,-6));
        bubble.add(new Bubble(600,100,100,2,-6));
        bubble.add(new Bubble(456,110,55,2,-6));
        bubble.add(new Bubble(600,100,100,2,-6));
        repaint();
    }

    void startsoundeffect(){

        try {
            AudioInputStream audioinputStream = AudioSystem.getAudioInputStream(getClass().getResource("/startsoundeffect.wav"));
            clip7 = AudioSystem.getClip();
            clip7.open(audioinputStream);
            clip7.loop(Clip.LOOP_CONTINUOUSLY);
            clip7.start();
        } catch (Exception e) {
            System.err.println("level1 arkaplandaki muzik hata ");
        }
    }
    void bubblepopsoundeffect(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/bubblepopsoundeffect.wav"));
            clip8 = AudioSystem.getClip();
            clip8.open(audioInputStream);
            clip8.start();
        } catch (Exception e) {
            System.err.println("ses hata bubble pop sound effect");
        }
    }

    void losinglivesoundeffect(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/losinglife.wav"));
            clip9 = AudioSystem.getClip();
            clip9.open(audioInputStream);
            clip9.start();
        } catch (Exception e) {
            System.err.println("hata can kaybi ses efekti");
        }
    }

    void level1backgroundmusic(){
        try {
            if(clip1 != null && clip1.isRunning()){
                return;
            }
            AudioInputStream audioinputStream = AudioSystem.getAudioInputStream(getClass().getResource("/backgroundmusic.wav"));
            clip1 = AudioSystem.getClip();
            clip1.open(audioinputStream);
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            clip1.start();
        } catch (Exception e) {
            System.err.println("level1 arkaplandaki muzik hata ");
        }
    }

    void level2backgroundmusic(){
        try {
            if(clip2 != null && clip2.isRunning()){
                return;
            }
            AudioInputStream audioinputstream = AudioSystem.getAudioInputStream(getClass().getResource("/level2music.wav"));
            clip2 = AudioSystem.getClip();
            clip2.open(audioinputstream);
            clip2.loop(Clip.LOOP_CONTINUOUSLY);
            clip2.start();
        } catch (Exception e) {
            System.err.println("level 2 muzik hata");
        }
    }

    void level3backgroundmusic(){
        try {
            if(clip3 != null && clip3.isRunning()){
                return;
            }
            AudioInputStream audioinputstream = AudioSystem.getAudioInputStream(getClass().getResource("/level3music.wav"));
            clip3 = AudioSystem.getClip();
            clip3.open(audioinputstream);
            clip3.loop(Clip.LOOP_CONTINUOUSLY);
            clip3.start();
        } catch (Exception e) {
            System.err.println("level3 muzik hata");
        }
    }



    void levelupsoundeffect(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/levelupsoundeffect.wav"));
            clip10 = AudioSystem.getClip();
            clip10.open(audioInputStream);
            clip10.start();
        } catch (Exception e) {
            System.err.println("hata mario levelup ses efekti");
        }
    }

    void level4backgroundmusic(){
        try {
            if(clip4 != null && clip4.isRunning()){
                return;
            }
            AudioInputStream audioinputstream = AudioSystem.getAudioInputStream(getClass().getResource("/level4music.wav"));
            clip4 = AudioSystem.getClip();
            clip4.open(audioinputstream);
            clip4.start();
        } catch (Exception e) {
            System.err.println("hata level4 muzik");
        }
    }
    void level5backgroundmusic(){
        try {
            if(clip5 != null && clip5.isRunning()){
                return;
            }
            AudioInputStream audioinputstream = AudioSystem.getAudioInputStream(getClass().getResource("/level5music.wav"));
            clip5 = AudioSystem.getClip();
            clip5.open(audioinputstream);
            clip5.loop(Clip.LOOP_CONTINUOUSLY);
            clip5.start();
        } catch (Exception e) {
            System.err.println("level5 muzik hata");
        }
    }

    void stopmusic(){
        if(clip1 != null && clip1.isRunning()) 
            clip1.stop();
        if(clip2 != null && clip2.isRunning()) 
            clip2.stop();
        if(clip3 != null && clip3.isRunning()) 
            clip3.stop();
        if(clip4 != null && clip4.isRunning()) 
            clip4.stop();
        if(clip5 != null && clip5.isRunning()) 
            clip5.stop();
        if(clip6 != null && clip6.isRunning())
            clip6.stop();
    }
    void stopsound(){
        if(clip1 != null && clip1.isRunning()) 
            clip1.stop();
        if(clip2 != null && clip2.isRunning()) 
            clip2.stop();
        if(clip3 != null && clip3.isRunning()) 
            clip3.stop();
        if(clip4 != null && clip4.isRunning()) 
            clip4.stop();
        if(clip5 != null && clip5.isRunning()) 
            clip5.stop();
        if(clip6 != null && clip6.isRunning())
            clip6.stop();
        if(clip7 != null && clip7.isRunning())
            clip7.stop();
        if(clip8 != null && clip8.isRunning())
            clip8.stop();
        if(clip9 != null && clip9.isRunning())
            clip9.stop();
        if(clip10 != null && clip10.isRunning())
            clip10.stop();
    }

    void gamefinishedsoundeffect(){
        try {
            if(clip6 != null && clip6.isRunning()){
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/gamefinish.wav"));
            clip6 = AudioSystem.getClip();
            clip6.open(audioInputStream);
            clip6.start();
        } catch (Exception e) {
            System.err.println("ses hata VICTORY KUTLAMA efetki");
        }
    }
    

}

