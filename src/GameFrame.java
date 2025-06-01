import java.awt.*;
import java.io.*;
import java.util.List;
import javax.swing.*;
public class GameFrame extends JFrame{
    User user = new User();
    Member current = null;
    JButton continuebutton;
    boolean log = false;
    JButton loginbutton;
    JButton registerbutton;
    ImageIcon resizeimage;
    
    public GameFrame(){
        super("Pang Game");
        try {
            user.load("log.txt");
        } catch (IOException e) {
            System.err.println("hata log txt gameframe");
        }
        ImageIcon icon = new ImageIcon("pangicon.png");
        Image smallimage = icon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        ImageIcon resizeimage = new ImageIcon(smallimage);
        setIconImage(resizeimage.getImage());
        setSize(800,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JMenuBar menubar = new JMenuBar();
        JMenu gamemenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem login = new JMenuItem("Login");
        JMenuItem register = new JMenuItem("Register");
        JMenuItem exit = new JMenuItem("Quit");
        newGame.addActionListener(e -> newgamebutton());
        login.addActionListener(e->loginpage());
        exit.addActionListener(e -> System.exit(0));
        gamemenu.add(newGame);
        gamemenu.add(register);
        gamemenu.add(exit);
        setJMenuBar(menubar);
        setVisible(true);
        JPanel menupanel = new JPanel();
        JPanel menupanel1 = new JPanel();
        JPanel logopanel = new JPanel();
        menupanel.setLayout(new BoxLayout(menupanel, BoxLayout.Y_AXIS));
        menupanel.setBorder(BorderFactory.createEmptyBorder(100,300,100,300));
        ImageIcon logo = new ImageIcon("SuperPangLogo.png");
        Image scaledlogo = logo.getImage().getScaledInstance(450, 225, Image.SCALE_SMOOTH);
        JLabel logolabel = new JLabel(new ImageIcon(scaledlogo));
        logolabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logopanel.add(logolabel);
        menupanel1.setLayout(new BoxLayout(menupanel1, BoxLayout.Y_AXIS));
        menupanel1.add(Box.createRigidArea(new Dimension(0, 40)));
        menupanel1.add(logopanel);
        menupanel1.add(Box.createVerticalGlue()); 
        menupanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menupanel1.add(menupanel);
        menupanel1.add(Box.createVerticalGlue()); 
        continuebutton = new JButton("Continue");
        continuebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuebutton.setVisible(false);
        continuebutton.addActionListener(e -> {
            JFrame gamewindow = new JFrame("PANG GAME");
            gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamewindow.setContentPane(new GamePanel(current));
            gamewindow.pack();
            gamewindow.setLocationRelativeTo(null);
            gamewindow.setVisible(true);
        });
        JButton newgamebutton = new JButton("New Game");
        loginbutton = new JButton("Login");
        registerbutton = new JButton("Register");
        JButton highscorebutton = new JButton("HighScores");
        JButton about = new JButton("About");
        JButton exitbutton = new JButton("Quit");
        newgamebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscorebutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscorebutton.addActionListener( e -> highscore());
        exitbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newgamebutton.addActionListener(e -> newgamebutton());
        about.addActionListener(e -> aboutpage());
        loginbutton.addActionListener(e -> {
        if (log == true) {
            log = false;
            continuebutton.setVisible(false);
            loginbutton.setText("Login");
            registerbutton.setVisible(true);
            JOptionPane.showMessageDialog(this, "Signed out.", "Signed Out", JOptionPane.INFORMATION_MESSAGE);
        } else {
            loginpage();
        }
    });
        registerbutton.addActionListener(e -> registerpage());
        exitbutton.addActionListener(e -> System.exit(0));
        menupanel.add(continuebutton);
        menupanel.add(Box.createRigidArea(new Dimension(0,10)));
        menupanel.add(newgamebutton);
        menupanel.add(Box.createRigidArea(new Dimension(0,10)));
        menupanel.add(loginbutton);
        menupanel.add(Box.createRigidArea(new Dimension(0,10)));
        menupanel.add(registerbutton);
        menupanel.add(Box.createRigidArea(new Dimension(0,10)));
        menupanel.add(highscorebutton);
        menupanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menupanel.add(about);
        about.setAlignmentX(Component.CENTER_ALIGNMENT);
        menupanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menupanel.add(exitbutton);
        menupanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton testbutton = new JButton("TEST");
        //testbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        testbutton.addActionListener(e -> {
            JFrame gamewindow = new JFrame("Game Test");
            gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamewindow.setContentPane(new GamePanel(current));
            gamewindow.pack();
            gamewindow.setLocationRelativeTo(null);
            gamewindow.setVisible(true);
        });
        //menupanel.add(testbutton);
        add(menupanel1);
    }

    public void newgamebutton(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1,10,10));
        ImageIcon image = new ImageIcon("pangicon.png");
        Image smallimage = image.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        resizeimage = new ImageIcon(smallimage);
        if (log == true){
            JButton startnewgame = new JButton("Start New Game");
            JButton signout = new JButton("Sign Out");
            startnewgame.addActionListener(e -> {
                int newgameoption = JOptionPane.showOptionDialog(this, "Starting a new game...", "Game", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, resizeimage, new String[]{"START", "Cancel"}, "START");
                if(newgameoption == JOptionPane.OK_OPTION){
                    current.setLevel(1);
                    current.setPoint(0);
                    current.setLive(3);
                    JFrame gamewindow = new JFrame("PANG GAME");
                    gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gamewindow.setContentPane(new GamePanel(current));
                    gamewindow.pack();
                    gamewindow.setLocationRelativeTo(null);
                    gamewindow.setVisible(true);       
                    gamewindow.setAlwaysOnTop(true);
                }else{
                    Window window = SwingUtilities.getWindowAncestor(panel);
                    if(window != null){
                        window.dispose();
                    }
                }
                Window window = SwingUtilities.getWindowAncestor(panel);
                if(window != null){
                    window.dispose();
                }
            });
            signout.addActionListener(e -> {
                log = false;
                continuebutton.setVisible(false);
                loginbutton.setText("Login");
                loginbutton.setVisible(true);
                registerbutton.setVisible(true);
                JOptionPane.showMessageDialog(this, "Signed out.", "Signed Out", JOptionPane.PLAIN_MESSAGE, resizeimage);
            });
            panel.add(startnewgame);
            panel.add(signout);
        } else {
            JButton playwithoutsave = new JButton("Play Without Save");
            playwithoutsave.setToolTipText("WARNING: Progress will not be saved!!!");
            playwithoutsave.addActionListener(e -> {
                int ok = JOptionPane.showConfirmDialog(this, "Playing without save means you will be playing the game without saving your progress!", "WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, resizeimage);
                if(ok == JOptionPane.OK_OPTION){
                    JFrame gamewindow = new JFrame("Game Test");
                    gamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gamewindow.setContentPane(new GamePanel(current));
                    gamewindow.pack();
                    gamewindow.setLocationRelativeTo(null);
                    gamewindow.setVisible(true);
                    gamewindow.setAlwaysOnTop(true);
                    }else {
                        Window window = SwingUtilities.getWindowAncestor(panel);
                        if(window != null){
                            window.dispose();
                    }
                }
                Window window = SwingUtilities.getWindowAncestor(panel);
                if(window != null){
                    window.dispose();
                }
            });
            JButton backbutton = new JButton("Back");
            backbutton.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.dispose();
                }
            });
            panel.add(playwithoutsave);
            panel.add(backbutton);
        }
        JOptionPane.showMessageDialog(this, panel, "New Game", JOptionPane.PLAIN_MESSAGE);
    }

    public void loginpage(){
        JTextField usernameinput = new JTextField();
        JPasswordField passwordinput = new JPasswordField();
        Object[] login = { "Username", usernameinput, "Password", passwordinput };
        ImageIcon image = new ImageIcon("pangicon.png");
        Image smallimage = image.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        ImageIcon resizeimage = new ImageIcon(smallimage);
        int result = JOptionPane.showConfirmDialog(this, login, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,resizeimage);
        if(result == JOptionPane.OK_OPTION){
            String username = usernameinput.getText();
            String password = passwordinput.getText();
            Member member = user.login(username, password);
            if(member != null){
                current = member; log = true;
                if(member.getLevel() > 1 || member.getPoint() > 0){
                    continuebutton.setVisible(true);
                }else{
                    continuebutton.setVisible(false);
                }
                loginbutton.setText("Sign Out");
                registerbutton.setVisible(false);
                JOptionPane.showMessageDialog(this, "Login successful", "Login Successful", JOptionPane.PLAIN_MESSAGE, resizeimage);
            }else{
                JOptionPane.showMessageDialog(this, "No User Found", "Error", JOptionPane.PLAIN_MESSAGE, resizeimage);
            }
        }
    }
    public void registerpage(){
        JTextField registeruser = new JTextField();
        JPasswordField registerpassword = new JPasswordField();
        Object[] register = {"Username", registeruser, "Password", registerpassword};
        ImageIcon image = new ImageIcon("pangicon.png");
        Image smallimage = image.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        ImageIcon resizeimage = new ImageIcon(smallimage);
        int result = JOptionPane.showConfirmDialog(this, register, "Sign Up", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, resizeimage);
        if(result == JOptionPane.OK_OPTION){
            String username = registeruser.getText().trim();
            String password = registerpassword.getText().trim();
            boolean status = false;
            try{
                status = user.register(username, password);
            }catch(IOException e){
                System.err.println("hata register game frame");
            }
            if(status == true){
                current = user.getuser(username);
                log = true;
                loginbutton.setText("Sign Out");
                loginbutton.setVisible(true);
                registerbutton.setVisible(false);
                continuebutton.setVisible(false);
                try {
                    user.save("log.txt");
                } catch (IOException e) {
                    System.err.println("hata register log txt");
                }
                JOptionPane.showMessageDialog(this, username + " signed up", "Succes", JOptionPane.PLAIN_MESSAGE, resizeimage);
            } else {
                JOptionPane.showMessageDialog(this, "Username is used. Try Aga'n!", "Error", JOptionPane.PLAIN_MESSAGE, resizeimage);
            }                        
        }
    }

    void highscore(){
        List<Member> list = user.highscore(5);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setSize(300, 600);
        JTextArea text = new JTextArea();
        text.setEditable(false);
        StringBuilder string = new StringBuilder();
        string.append("TOP HIGHSCORE\n\n");
        for(int i = 0; i < list.size(); i++){
            Member member = list.get(i);
            string.append((i + 1) + " " + member.getUsername() + " SCORE = " + member.getPoint() + "\n");
        }
        text.setText(string.toString());
        JScrollPane scroll = new JScrollPane(text);
        panel.add(scroll, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "HIGHSCORES", JOptionPane.PLAIN_MESSAGE, resizeimage);


    }
    void aboutpage(){
        JOptionPane.showMessageDialog(this, "Game Created by:\nEge Huriel (202030702114) \nege.huriel@std.yeditepe.edu.tr", "ABOUT", JOptionPane.PLAIN_MESSAGE, resizeimage);

    }
}
