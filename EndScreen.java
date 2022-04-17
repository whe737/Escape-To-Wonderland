import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JPanel
{
    public EndScreen()
    {
        ImageIcon back=new ImageIcon("./assets/gameOver.png");
        ImageIcon restartIcon=new ImageIcon("./assets/restartButton.png");
        JLabel background=new JLabel(back);
        JButton restartButton=new JButton(restartIcon);
        JLabel score=new JLabel("Your score was ");//);
        score.setFont(new Font("Calibri", Font.PLAIN, 64));
        background.setBounds(0,0,1280,720);
        score.setBounds(340,400,600,100);
        restartButton.setFocusable(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setVisible(true);
        restartButton.setBounds(500, 558, 255, 110);
        restartButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new Frame();
            }
        });
        this.setLayout(null);
        this.add(restartButton);
        this.add(score);
        this.add(background);
    }
}