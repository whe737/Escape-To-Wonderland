import javax.swing.*;
import java.awt.event.*;
public class Frame extends JFrame implements KeyListener
{
    Panel panel;
    Start startPanel;
    boolean on = true;

    boolean w = false;
    boolean s = false;
    boolean a = false;
    boolean d = false;
    public Frame()
    {
        this.addKeyListener(this);
        this.setFocusable(true);
        startPanel = new Start(this);
        this.add(startPanel);
        this.setSize(800, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(140, 50);
        this.pack();
        this.setVisible(true);
        

        Thread movement = new Thread(new Runnable() {

            @Override
            public void run() {
                while (on)
                {
                    if (w) panel.player.setYVelocity(-2);
                    else if (s) panel.player.setYVelocity(2);

                    if (a) panel.player.setXVelocity(-2);
                    else if (d) panel.player.setXVelocity(2);
                    sleep(5);
                }
                
            }
            
        });
        movement.start();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar() + " " + e.getKeyCode());
        int key = e.getKeyCode();
        if (key == 87) w = true;
        else if (key == 65) a = true;
        else if (key == 83) s = true;
        else if (key == 68) d = true;
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 87) w = false;
        else if (key == 65) a = false;
        else if (key == 83) s = false;
        else if (key == 68) d = false;
        else if (key == 32) panel.shoot();
        
    }

    public void sleep(int mili)
    {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}