/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab04;

import basicgraphics.BasicFrame;
import basicgraphics.Clock;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.images.Picture;
import basicshooter.Enemy;
import basicshooter.Shooter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author aeiler3
 */
public class MarsLander {

    public static void main(String[] args) {
        BasicFrame bf = new BasicFrame("Super Good Parkour Game");
        SpriteComponent sc = new SpriteComponent(); 
        Dimension dim = new Dimension(800, 400);
        sc.setPreferredSize(dim);
        bf.createBasicLayout(sc);
        LandingPad lp = new LandingPad(sc);
        Rocket rocket = new Rocket(sc);
        rocket.setPicture(Rocket.rocket);
        Clock.addTask(sc.moveSprites());
        Clock.start(10);

        KeyAdapter k = new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent evt) {
                int foo = evt.getKeyCode();
                System.out.println("foo: " + foo);

                switch (foo) {
                    case VK_UP:
                        rocket.setVelY(rocket.getVelY() - .04);
                        rocket.setPicture(Rocket.rocketWithFlame);
                        break;
                    case VK_LEFT:
                        rocket.setVelX(rocket.getVelX() - .02);
                        break;
                    case VK_RIGHT:
                        rocket.setVelX(rocket.getVelX() + .02);
                        break;
                    default:
                        break;
                }//end of switch
            }//end of keyp

            @Override
            public void keyReleased(KeyEvent evt) {
                rocket.setPicture(Rocket.rocket);
            }

        };
        bf.addKeyListener(k);
        bf.show();

        sc.addSpriteSpriteCollisionListener(Rocket.class, LandingPad.class, new SpriteSpriteCollisionListener<Rocket, LandingPad>() {
            @Override
            public void collision(Rocket r, LandingPad l) {
                l.setActive(false);
                r.setActive(false);
                if (r.getVelX() > .1 || r.getVelX() < -.1){
                JOptionPane.showMessageDialog(sc, "X-velocity too large! :"+r.getVelX());
                System.exit(0);
                }
                if (r.getVelY() > .4){
                    JOptionPane.showMessageDialog(sc, "Y-velocity too large! :"+r.getVelY());
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(sc, "You win! :D");
                System.exit(0);
            }
        });


    }
}
