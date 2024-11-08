import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PingPongGameFrame extends JPanel implements ActionListener, KeyListener {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 300;

    private int ballX = 100, ballY = 100, ballVelX = 2, ballVelY = 2;
    private int paddle1Y = 100, paddle2Y = 100;
    private final int PADDLE_WIDTH = 50, PADDLE_HEIGHT = 60;
    private Timer timer;

    private Image backgroundImage;
    private Image paddle1Image, paddle2Image;

    // Constructor
    public PingPongGameFrame() {
        // Load background image
        ImageIcon backgroundIcon = new ImageIcon("background.jpeg");
        backgroundImage = backgroundIcon.getImage();

        // Load paddle images
        ImageIcon paddle1 = new ImageIcon("paddle one.jpeg");
        paddle1Image = paddle1.getImage();

        ImageIcon paddle2 = new ImageIcon("paddletwo.jpeg");
        paddle2Image = paddle2.getImage();

        // Set preferred size for the panel
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Add key listener to the panel
        addKeyListener(this);
        setFocusable(true);

        // Initialize timer for animation (calls actionPerformed every 10ms)
        timer = new Timer(10, this);
        timer.start();
    }

    // Method to start the game by making the JFrame visible
    public void startGame(JFrame gameFrame) {
        gameFrame.setVisible(true);
    }

    // Action event handler for game logic (ball movement, collision, etc.)
    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballVelX;
        ballY += ballVelY;

        // Ball collision with Player 1 paddle (left side)
        if (ballX <= 40 && ballY >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballVelX = -ballVelX; // Reflect ball horizontally
            // Adjust vertical speed based on where the ball hits the paddle
            int paddleCenter = paddle1Y + PADDLE_HEIGHT / 2;
            int ballCenter = ballY + 5; // Ball radius = 5 (half of 10)
            ballVelY = (ballCenter - paddleCenter) / 5;  // Modify vertical velocity based on impact position
        }

        // Ball collision with Player 2 paddle (right side)
        if (ballX >= 460 && ballY >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballVelX = -ballVelX; // Reflect ball horizontally
            // Adjust vertical speed based on where the ball hits the paddle
            int paddleCenter = paddle2Y + PADDLE_HEIGHT / 2;
            int ballCenter = ballY + 5; // Ball radius = 5 (half of 10)
            ballVelY = (ballCenter - paddleCenter) / 5;  // Modify vertical velocity based on impact position
        }

        // Ball collision with top and bottom walls
        if (ballY <= 0 || ballY >= getHeight() - 10) {
            ballVelY = -ballVelY;
        }

        // Ball out of bounds (reset the ball position)
        if (ballX <= 0 || ballX >= getWidth()) {
            ballX = 250;
            ballY = 150;
            ballVelX = -ballVelX;
            ballVelY = 2;
        }

        // Redraw the game
        repaint();
    }

    // Paint the game components (ball, paddles, etc.)
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Draw the ball and paddles
        drawGame(g);
    }

    // Draw the ball and paddles
    private void drawGame(Graphics g) {
        // Draw ball
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, 30, 30); // Ball is 30x30px

        // Draw paddles using images
        g.drawImage(paddle1Image, 10, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT, this); // Player 1 Paddle
        g.drawImage(paddle2Image, 440, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT, this); // Player 2 Paddle
    }

    // Handle key events for paddle movement
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // Player 1 controls (A and Z keys)
        if (e.getKeyCode() == KeyEvent.VK_A && paddle1Y > 0) {
            paddle1Y -= 30; // Move player 1 up
        } else if (e.getKeyCode() == KeyEvent.VK_Z && paddle1Y < getHeight() - PADDLE_HEIGHT) {
            paddle1Y += 30; // Move player 1 down
        }

        // Player 2 controls (Up and Down arrow keys)
        if (e.getKeyCode() == KeyEvent.VK_UP && paddle2Y > 0) {
            paddle2Y -= 30; // Move player 2 up
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && paddle2Y < getHeight() - PADDLE_HEIGHT) {
            paddle2Y += 30; // Move player 2 down
        }
    }

    public static void main(String[] args) {
        // Create the game frame (JFrame)
        JFrame gameFrame = new JFrame("Ping Pong Game");
        PingPongGameFrame gamePanel = new PingPongGameFrame();

        // Set frame properties
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameFrame.setLocationRelativeTo(null); // Center the window
        gameFrame.setResizable(false);

        // Add the game panel (which handles the game drawing) to the frame
        gameFrame.add(gamePanel);

        // Start the game
        gamePanel.startGame(gameFrame);
    }
}