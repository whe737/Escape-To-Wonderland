import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;

public class EndScreen extends JPanel
{
    private Frame parentFrame;
    private ArrayList<ScoreObject> scores;
    private JScrollPane scrollPane;
    private int scoreNum = 0;
    public EndScreen(Frame parentFrame)
    {
        this.parentFrame = parentFrame;
        scrollPane = new JScrollPane();
        scrollPane.setBounds(900, 300, 300, 350);
        this.add(scrollPane);

        scoreNum = this.parentFrame.panel.score;

        ImageIcon back=new ImageIcon("./assets/gameOver.png");
        ImageIcon restartIcon=new ImageIcon("./assets/restartButton.png");
        JLabel background=new JLabel(back);
        JButton restartButton=new JButton(restartIcon);
        JLabel score=new JLabel("Your score was " + this.scoreNum);//);
        score.setFont(new Font("Calibri", Font.PLAIN, 50));
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
                Sprite.health = 100;
                parentFrame.dispose();
                new Frame();
            }
        });
        this.setLayout(null);
        this.add(restartButton);
        this.add(score);
        this.add(background);


        try {
            // get arraylist
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("Scores.ser")));
            scores = (ArrayList<ScoreObject>)in.readObject();
            System.out.println(scores);
        } catch (Exception e) {
            System.out.println("No prior scores");
            scores = new ArrayList<>();
        }

        try {
            // updates values in arraylist
            String name = JOptionPane.showInputDialog(parentFrame, "What is your name?");
            scores.add(new ScoreObject(name, scoreNum));
            sort(scores);
            System.out.println(scores);
            String[] col= {"Name", "Score"};
            Object[][] row = new Object[scores.size()][2];
            for (int i = 0; i < scores.size(); i++)
            {
                row[i][0] = scores.get(i).name;
                row[i][1] = scores.get(i).score;
            }

            JTable table = new JTable(row, col);
            table.setFont(new Font("Calibri", Font.PLAIN, 25));
            table.setRowHeight(30);
            scrollPane.setViewportView(table);

            // saves arraylist
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("Scores.ser")));
            out.writeObject(scores);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sort(ArrayList<ScoreObject> arr)
    {
        for (int i = 0; i < arr.size(); i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.size(); j++)
            {
                if (arr.get(j).score > arr.get(index).score) index = j; 
            }

            if (index != i)
            {
                ScoreObject temp = arr.get(index);
                arr.set(index, arr.get(i));
                arr.set(i, temp);
            }
        }
    }
}