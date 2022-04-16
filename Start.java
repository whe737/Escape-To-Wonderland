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
        JButton startButton = new JButton();
        startButton.setBounds(540, 300, 200, 100);
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
    }
}