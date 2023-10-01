
import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GameFrame(){
        // Set the new variable GamePanel
        this.add(new GamePanel());
        
        // Add title
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // Since we add compments to the JFrame this.pack will fit all the compents snug
        // into the JFrame
        this.pack();
        this.setVisible(true);
        // This is how we get our program to go into the middle of the computer screen
        this.setLocationRelativeTo(null);
    }
}
