import javax.swing.JFrame;
import javax.swing.JPanel;

public class MissileApp extends JFrame
{
    private GamePanel _gamePanel;
    
    public MissileApp()
    {
      super("Missile Command");
      this.setSize(MissileConstants.SCREEN_WIDTH,MissileConstants.SCREEN_HEIGHT);
      _gamePanel = new GamePanel();
      _gamePanel.setPreferredSize(new java.awt.Dimension(800, 600));
      this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
      this.add(_gamePanel);
      this.setVisible(true);
      this.setResizable(false);
      
    }
    
   public static void main(String[] args)
   {
      MissileApp app = new MissileApp();
   }

   

   
}
