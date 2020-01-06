import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.geom.*;

public class GamePanel extends JPanel implements ActionListener,MouseMotionListener{

 
    private javax.swing.Timer _timer;
    private BufferedImage _image;
    private int _x, _y;
    private int level,score,width,height,mouseX,mouseY,tick,numEnemies,numCities, destroyedCities,previousPoints,cityReset;
    private long start,secondsPassed,lastWave;
    private boolean displayIntro,_gameOver,planeFly;
    private City[] cities;
    private Timer t,t1; 
    private boolean[] cityStatus;
    private Plane plane;
    private AudioClip newLevel,fire,explosion;
    private Battery[] batteries;
    private KeyZListener zListener;
    private KeyXListener xListener;
    private KeyCListener cListener;
    private KeyAListener aListener;
    private ArrayList<EnemyMissile> eProxies;
    private Random _generator;
    
    
    

    public GamePanel(){
        this.setBackground(Color.BLACK);
        width = 800;
        height = 600;
        this.setSize(800, 600);
        this.setPreferredSize(new Dimension(800, 600));
        
        zListener = new KeyZListener(this);
        xListener = new KeyXListener(this);
        cListener = new KeyCListener(this);
        aListener = new KeyAListener(this);
        
        displayIntro = true;
        _gameOver = false;
        planeFly = false;
        level = 0;
        score = -300;
        previousPoints = -300;
        cityReset = -300;
        destroyedCities = 0;
        
        tick = 25;
        t = new Timer(tick,this);
        t1 = new Timer(1000,this); 
        
        cities = new City[6];
        cities[0] = new City(150,550);
        cities[1] = new City(200,550);
        cities[2] = new City(250,550);
        cities[3] = new City(500,550);
        cities[4] = new City(550,550);
        cities[5] = new City(600,550);
        numCities = 6;
        
        newLevel = loadSound("newLevel.wav");
        fire = loadSound("fire.wav");
        explosion = loadSound("explosion.wav");
        
        plane = new Plane(width);
        
        batteries = new Battery[3];
        batteries[0] = new Battery(75,550);
        batteries[1] = new Battery(400,550);
        batteries[2] = new Battery(725,550);
        
        eProxies = new ArrayList<EnemyMissile>(); 
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        mouseX = 400;
        mouseY = 300;
        addMouseMotionListener(this);
        newLevel();
        t.start();
    
     
        

     
    }
    public EnemyMissile eMissileFactory()
    /**
     * This method implements the factory method design pattern to build new tetriminos during Tetris game play.
     */
    {
        EnemyMissile eMissile;
        int randomNumber;
        _generator = new Random();
        Random rand = new Random();
        int rand_int1 = rand.nextInt(500);
        int rand_int2 = rand.nextInt(9);
        randomNumber = _generator.nextInt(9)+1;
        switch(randomNumber) {
            case 1: eMissile = new EnemyMissile(rand_int1,rand_int2,150,550);     break;
            case 2: eMissile = new EnemyMissile(rand_int1,rand_int2,200,550);     break;
            case 3: eMissile = new EnemyMissile(rand_int1,rand_int2,250,550);     break;
            case 4: eMissile = new EnemyMissile(rand_int1,rand_int2,500,550);     break;
            case 5: eMissile = new EnemyMissile(rand_int1,rand_int2,550,550);     break;
            case 6: eMissile = new EnemyMissile(rand_int1,rand_int2,600,550);     break;
            case 7: eMissile = new EnemyMissile(rand_int1,rand_int2,75,550);     break;
            case 8: eMissile = new EnemyMissile(rand_int1,rand_int2,400,550);     break;
            default: eMissile = new EnemyMissile(rand_int1,rand_int2,725,550);     break;
        }
        return eMissile;
    }
    public void actionPerformed(ActionEvent e)
    {
       if (e.getSource() == t1){
            secondsPassed = (System.currentTimeMillis() - start)/1000;
        }
        else{
       for (int i = 0; i < batteries.length; i ++){
           if (batteries[i].update()){
                    explosion.play();
                }
            }
       if (secondsPassed > 7 && plane.getStatus()){
            plane.update();
            if (batteries[0].collides(plane.getArea()) || batteries[1].collides(plane.getArea())|| batteries[2].collides(plane.getArea())){
                plane.kill();
                score = score + 1000;
            }
        }
        
       numCities = 0;
       for (int i = 0;i < cities.length;i++){
           if (cities[i].getStatus()){
               numCities ++;
        }
    }
        if (numCities == 0){
            _gameOver = true;
        }
        checkNewLevel();
        repaint();
    }  
    }
    public void newLevel () {
        level++;
        cityReset += score - previousPoints;
        previousPoints = score;
        numEnemies = 0;
        plane.reset();
        start = System.currentTimeMillis();
        lastWave = 0;
        secondsPassed = 0;
        for (int i = 0;i<batteries.length;i++) {
            batteries[i].rebuild();
        }
        newLevel.play();
        for (int j = 0;j<(5*level)+5;j++) {
            eProxies.add(eMissileFactory());
        }
        score += (50*numCities);
        destroyedCities = 0;
        if(cityReset >  8000)
        {
            for (int z =0; z <cities.length;z++){
                cities[z].rebuild();
            }
            cityReset = score-8000;
        }
        repaint();

    }
    public void checkNewLevel(){
        if(eProxies.size() == 0){
            newLevel();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if (displayIntro) {
            drawTitleScreen(g);     
        } else {
            g.setColor(Color.BLUE);
            for (int i = 0;i<cities.length;i++) {
                
                cities[i].draw(g);
            }
            
            for (int i = 0;i<numEnemies;i++) {
                /*enemies[i].draw(g); */
            }
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.RED);
            for (int i = 0;i<eProxies.size();i++) {
                    EnemyMissile thisMissile =eProxies.get(i);
                    thisMissile.moveForward();
                    g2.draw(new Line2D.Double(thisMissile.getCX(),thisMissile.getCY(),thisMissile.getSX(),thisMissile.getSY()));
                  }
            
            g.setColor(Color.YELLOW);
            g.fillRect(0,550,width,50);
            
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier New",Font.PLAIN,  12));
            g.drawString ("Level: " + level + " " + "Score: " + score, 250, 250);
            
                for (int j = 0;j<eProxies.size();j++) {
                    EnemyMissile thisMissile =eProxies.get(j);
                    for (int i = 0;i<batteries.length;i++) {
                        if (batteries[i].collides(thisMissile)){
                            eProxies.remove(thisMissile);
                            score += 100;
                        }
                        else if (batteries[i].collidesB(thisMissile)){
                            eProxies.remove(thisMissile);
                        }
                        if (batteries[i].getStatus()){
                            batteries[i].draw(g);
                        }   
                    }
                    for(int u= 0; u<cities.length;u++){
                        if (cities[u].collides(thisMissile, destroyedCities)){
                            numCities -= 1;
                            destroyedCities += 1;
                            eProxies.remove(thisMissile);
                        }
                        if (cities[u].getStatus()){
                            cities[u].draw(g);
                        } 
                    }
                }
            
            plane.draw(g);
            
            if (_gameOver) {
                g.setColor(Color.RED);
                g.setFont(new Font("Courier New",Font.PLAIN,  75));
                g.drawString("THE END",50,240);
                t.stop();
                t1.stop();
            }       
        }
        
    }
    
    public void drawTitleScreen (Graphics g) {
        g.setFont(new Font("Courier New",Font.BOLD,  75));
        g.setColor(Color.RED);
        g.drawString("MISSILE COMMAND",32,113);
        g.setFont(new Font("Courier New",Font.PLAIN,  22));
        g.setColor(Color.WHITE);
        g.drawString("Instructions:",41,230);
        g.drawString("Mouse: Aim",41,261);
        g.drawString("z,x,c: Fire from 1st, 2nd, 3rd base",41,291);
        g.setFont(new Font("Courier New",Font.PLAIN,  42));
        g.drawString("Press A to Begin",41,330);
    }
    private AudioClip loadSound(String fileName) {
        URL url = getClass().getResource(fileName);
        return Applet.newAudioClip(url);
    }
    private class KeyZListener extends KeyInteractor 
    {
        
        public KeyZListener(JPanel p)
        {
            super(p,KeyEvent.VK_Z);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if (!_gameOver && batteries[0].fire(mouseX, mouseY)){
                fire.play();
                /*repaint(); */
            }
        }
    }
   private class KeyXListener extends KeyInteractor 
    {
        public KeyXListener(JPanel p)
        {
            super(p,KeyEvent.VK_X);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if (!_gameOver && batteries[1].fire(mouseX, mouseY)){
                fire.play();
                /*repaint(); */
            }
        }
    }
    private class KeyCListener extends KeyInteractor
    {
        public KeyCListener(JPanel p)
        {
            super(p,KeyEvent.VK_C);
        }

        public  void actionPerformed (ActionEvent e) {
            if (!_gameOver && batteries[2].fire(mouseX, mouseY)) {
                fire.play();
                /*repaint(); */
            }
        }
    }
     private class KeyAListener extends KeyInteractor /* press a to start game */
    {
        public KeyAListener(JPanel p)
        {
            super(p,KeyEvent.VK_A);
        }

        public  void actionPerformed (ActionEvent e) {
            if (!_gameOver) {
                displayIntro = false;
                repaint();
            }
        }
    }
    public void mouseMoved(MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void mouseDragged(MouseEvent e) {}   
   
     

}
