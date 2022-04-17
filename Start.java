import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JPanel{
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private Frame parentFrame;
    public Start(Frame parentFrame)
    {
        this.setLayout(null);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // used with frame.pack()
        this.parentFrame = parentFrame;
        addComponents();
    }

    private void addComponents()
    {
        ImageIcon buttonIcon=new ImageIcon("./assets/startButton.png");
        ImageIcon tutorialBG=new ImageIcon("./assets/tutorial.png");
        JLabel background=new JLabel(tutorialBG);
        JButton startButton = new JButton();
        startButton.setFocusable(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setVisible(true);
        startButton.setIcon(buttonIcon);
        startButton.setBounds(500, 558, 250, 110);
        background.setBounds(0,0,1280,720);
        startButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.startPanel.setVisible(false);
                Panel pain = new Panel(parentFrame);
                parentFrame.panel = pain;
                parentFrame.add(pain);
            }
            
        });
        this.add(startButton);
        this.add(background);
    }
}