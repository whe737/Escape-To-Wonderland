import javax.swing.*;
import java.awt.*;
public class Panel extends JPanel
{
    final int WIDTH = 1280;
    final int HEIGHT = 720;

    Sprite player = new Sprite(50, 300, 50, 60, 0, false);
    public Panel()
    {
        this.setLayout(null);
        
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // used with frame.pack()

        Thread game = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true)
                {
                    player.moveHorizontally();
                    if (player.x() < 0) player.setX(0);
                    else if (player.x() > WIDTH/4) player.setX(WIDTH/4);
                    player.moveVertically();
                    if (player.y() < 0) player.setY(0);
                    else if (player.y() +player.getHeight() > HEIGHT) player.setY(HEIGHT-player.getHeight());
                    

                    player.setYVelocity(0);
                    player.setXVelocity(0);
                    repaint();
                    sleep(5);
                }
                
            }
            
        });
        game.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g); // paint background
		Graphics2D g2D = (Graphics2D) g;
        g2D.setBackground(Color.black);

        player.draw(g2D);
    }

    public void sleep(int mili)
    {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}