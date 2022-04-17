import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class Panel extends JPanel
{
    private Frame parentFrame;
    final int WIDTH = 1280;
    final int HEIGHT = 720;
    Thread game;

    Sprite player = new Sprite(50, 300, 50, 60, 0, false);
    ArrayList<Sprite> obstacles = new ArrayList<>(); // prob not gonna be used
    ArrayList<Sprite> enemies = new ArrayList<>(); // nerf darts
    ArrayList<Sprite> shooters = new ArrayList<>(); // enemies that shoot pellets
    Sprite background = new Sprite(0, 0, new ImageIcon("./assets/skyBG.png"), 2943, 750, -1);
    Sprite background2 = new Sprite(2943, 0, new ImageIcon("./assets/skyBG2.png"), 2953, 750, -1);
    
    public Panel(Frame parentFrame)
    {
        this.parentFrame = parentFrame;
        this.setLayout(null);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // used with frame.pack()

        obstacles.add(new Sprite(0, 0, 50, 50, 0, false));
        for (int i =0; i < 4; i++)
        {
            enemies.add(new Sprite(WIDTH, (int)(Math.random() * (HEIGHT - 100) + 50), 50, 50, -(int)(Math.random() *3 + 2), false));
        }
        shooters.add(new Sprite(WIDTH - 100, (int)(Math.random() * (HEIGHT - 100) + 50), 50, 60, 1, false));
        

        game = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true)
                {
                    // bullets
                    for (int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).moveHorizontally();
                        if (enemies.get(i).x() < 0){
                            enemies.remove(i);
                            i--;
                            enemies.add(new Sprite(WIDTH, (int) (Math.random() * (HEIGHT - 100) + 50), 50, 50, -(int)(Math.random() *3 + 2), false));
                        }
                    }

                    player.moveHorizontally();
                    if (player.x() < 0) player.setX(0);
                    else if (player.x() > WIDTH/4) player.setX(WIDTH/4);
                    // object collision x axis
                    ArrayList<Sprite> collidesWith = player.collidesWith(obstacles);
                    if (collidesWith.size() > 0)
                    {
                        if (player.xVelocity() < 0) // LEFT
                        {
                            player.setX(collidesWith.get(0).getRight());
                        }     
                        else if (player.xVelocity() > 0) // RIGHT
                        {
                            player.setX(collidesWith.get(0).getLeft() - player.getWidth());
                        }
                    }
                    player.setXVelocity(0);

                    player.moveVertically();
                    if (player.y() < 0) player.setY(0);
                    else if (player.y() +player.getHeight() > HEIGHT) player.setY(HEIGHT-player.getHeight());
                    // object collision y axis
                    collidesWith = player.collidesWith(obstacles);
                    if (collidesWith.size() > 0)
                    {
                        if (player.yVelocity() < 0) // DOWN
                        {
                            player.setY(collidesWith.get(0).getBottom());
                        }     
                        else if (player.yVelocity() > 0) // UP
                        {
                            player.setY(collidesWith.get(0).getTop() - player.getHeight());
                        }
                    }
                    player.setYVelocity(0);

                    // enemy movement and collision
                    collidesWith = player.collidesWith(enemies);
                    if (collidesWith.size() > 0)
                    {
                        Sprite enemy = collidesWith.get(0);
                        damagePlayer();
                        System.out.println("Player got hit by " + enemy + "Player Health: " + Sprite.health);
                        for (int i = 0; i < enemies.size(); i++)
                        {
                            if(enemies.get(i) == enemy) 
                            {
                                enemies.remove(i);
                                enemies.add(new Sprite(WIDTH, (int) (Math.random() * (HEIGHT - 100) + 50), 50, 50, -(int)(Math.random() *3 + 2), false));
                            }
                        }
                    }
                    // Shooter movement and shooting
                    for (Sprite s : shooters)
                    {
                        s.moveVertically();
                        if (s.y() < 0 || s.y() + s.getHeight() > HEIGHT) s.inverseYVelocity();
                        for (int i = 0; i < s.bullets.size(); i++)
                        {
                            Sprite bullet = s.bullets.get(i);
                            bullet.moveHorizontally();
                            if (bullet.x() < 0)
                            {
                                s.bullets.remove(i);
                                i--; 
                            }
                        }
                        collidesWith = player.collidesWith(s.bullets);
                        if (collidesWith.size() > 0)
                        {
                                for (int i = 0; i < s.bullets.size(); i++)
                                {
                                    if (s.bullets.get(i) == collidesWith.get(0)) s.bullets.remove(i);
                                }
                                damagePlayer();
                        }
                    }


                    // Bullet movement and collision for player
                    for (int i = 0; i < player.bullets.size(); i++)
                    {
                        player.bullets.get(i).moveHorizontally();
                        if (player.bullets.get(i).x() > WIDTH)
                        {
                            player.bullets.remove(i);
                            i--;
                        }
                        else{
                            collidesWith = player.bullets.get(i).collidesWith(shooters);
                            if (collidesWith.size() > 0)
                            {
                                player.bullets.remove(i); // removes bullet after collision
                                Sprite sprite = collidesWith.get(0);
                                sprite.spriteHealth -= 20;
                                if (sprite.spriteHealth < 0)
                                {
                                    System.out.println("SHOOTER DEAD");
                                    for (int j = 0; j < shooters.size(); j++) // removes target
                                    {
                                        if (shooters.get(j) == sprite) 
                                        {
                                            shooters.remove(j);
                                            j--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    //background
                    background.moveHorizontally();
                    background2.moveHorizontally();

                    repaint();
                    sleep(5);
                }
                
            }
            
        });
        game.start();

        Thread shootersShoot = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true){
                    for (Sprite s : shooters)
                    {
                        s.bullets.add(new Sprite(s.x() + s.getWidth(), s.y(), 20, 20, -2, true));
                    }
                    sleep(1000);
                }
                
            }
            
        });
        shootersShoot.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g); // paint background
		Graphics2D g2D = (Graphics2D) g;
        g2D.setBackground(Color.black);

        background.draw(g2D);
        background2.draw(g2D);

        player.draw(g2D);

        g2D.setColor(Color.blue);
        for (Sprite s: obstacles) s.draw(g2D);

        g2D.setColor(Color.red);
        for (Sprite s: enemies) s.draw(g2D);

        g2D.setColor(Color.red);
        g2D.fill3DRect(1040, 40, 200, 30, true);

        g2D.setColor(Color.green); // health bar
        g2D.fill3DRect(1040, 40, Sprite.health * 2, 30, true);

        g2D.setColor(Color.pink);
        for (Sprite s: shooters) s.draw(g2D);

        g2D.setColor(Color.yellow);
        for (Sprite s: player.bullets) s.draw(g2D);
        for (Sprite s: shooters){
            for (Sprite bullet: s.bullets) bullet.draw(g2D);
        }
    }

    public void sleep(int mili)
    {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void damagePlayer()
    {
        Sprite.health -= 20;
        if (Sprite.health <= 0)
        {
            System.out.println("Player dead");
            endGame();
        }
    }

    public void endGame()
    {
        System.out.println("GAME OVER");
        parentFrame.panel.setVisible(false);
        parentFrame.add(new EndScreen());
        game.stop();
    }

    public void shoot()
    {
        player.bullets.add(new Sprite(player.x() + player.getWidth(), player.y(), 20, 20, 2, true));
    }
}