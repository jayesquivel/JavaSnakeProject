// Import needed frames
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
    // THis static keyword will determine the height and width of the screen
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // This static keyword will determine how big do we want the objects in the game
    static final int UNIT_SIZE = 25;
    // We want to determine how many objects are on the screen at a given time
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    // Create a delay for the timer
    static final int DELAY = 75;

    // Next we'll create an array to hold the cordinates for all the body parts 
    // of the snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    // Declare the apples that will be eaten and where they will be located at
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        // This constructor will give the game panel it's color as well as it's
        // option to start the game.
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    // Method used to start the game
    public void startGame(){
        // This method only starts the game creating new apples as well as changing
        // the running method equal to true since it was false when we first initliazed it
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    // This method is going to create a grid to help us place our grapics in the window
    public void draw (Graphics g){
        if(running){
           /*for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }*/
            // Setting the color of the apple and what it looks like
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // This set's what the snake looks like
            for (int i = 0; i<bodyParts; i++){
                // This is the head of the snake
                if(i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                // This is the body of the snake
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

        }
        else{
            gameOver(g);
        }
    }
    // This method creates the cordinates for the new apples once they appear
    public void newApple(){
        appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    // This method is used to move the snake and it's body parts
    public void move(){
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        // This switch is how we move the snake different directions
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
        }
    }
    // Method used to check if the snake ate an apple
    public void checkApple(){
        if((x[0] == appleX) && (y[0]) == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    // Method used to see if th use crashed into a wall
    public  void checkCollisions (){
        // This determines if the head ran into the body part and will trigger
        // a game over method
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        // This part of code checks if head touches the left border of the screen
        if(x[0] < 0){
            running = false;
        }
        // This part of code checks if head touches the right border of the screen
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        // This part of code checks if head touches the top border of the screen
        if(y[0] < 0){
            running = false;
        }
        // This part of code checks if head touches the bottom border of the screen
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        // Stop the timer if any of these conditions are true
        if(!running){
            timer.stop();
        }
    }
    // Method to determine if the game is over
    public void gameOver(Graphics g){
        // Score text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e){
        // This method will determine if the game is running then it will cause
        // the snake to move
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            // This switch determines and limits the user to only turn left 90 degrees
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if(direction != 'R'){
                    direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:
                if(direction != 'L'){
                    direction = 'R';
                }
                break;
                case KeyEvent.VK_UP:
                if(direction != 'D'){
                    direction = 'U';
                }
                break;
                case KeyEvent.VK_DOWN:
                if(direction != 'U'){
                    direction = 'D';
                }
                break;
            }

        }
    }
}
