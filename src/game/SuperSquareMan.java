/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import basicgraphics.BasicFrame;
import basicgraphics.Clock;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.Task;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;
import static game.Game.touched_ground;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aidan Eiler
 */
public class SuperSquareMan extends Sprite {

    //for sprite creation
    private final BufferedImage img;
    private final BufferedImage img2;
    private Graphics2D graphics;
    public final int w;
    private final int h;
    public static int[] SRITE_SIZE = new int[1];
    public static Picture main;
    public static Picture hurt;
    private int score;
    private boolean is_hurt = false;
    private int hp;
    private final int MAX_HP = 3;
    public final ReusableClip gamejump = new ReusableClip("gamejump.wav");
    final static ReusableClip ouch = new ReusableClip("ouch.wav");
    boolean gravityOn = true;

    @Override
    public void setY(double d) {
        super.setY(d);
    }

    @Override
    public void setVelY(double d) {
        if (!gravityOn) {
            super.setVelY(0);
        } else {
            super.setVelY(d);
        }
    }

    /**
     * Initializes the sprite, setting its picture, position, and speed. It also
     * adds it to the SpriteComponent.
     *
     * @param sc
     */
    public SuperSquareMan(SpriteComponent sc) {
        super(sc);

        img = BasicFrame.createImage(20, 20);
        graphics = (Graphics2D) img.getGraphics();//Initialization
        w = img.getWidth();
        h = img.getHeight();
        SRITE_SIZE[0] = w;
        int[] xArray = {0, w, w, 0};
        int[] yArray = {0, 0, h, h};
        graphics.setColor(java.awt.Color.black);
        graphics.fillPolygon(xArray, yArray, 4);
        main = new Picture(img);
        img2 = BasicFrame.createImage(20, 20);
        graphics = (Graphics2D) img2.getGraphics();//Initialization
        graphics.setColor(java.awt.Color.red);
        graphics.fillPolygon(xArray, yArray, 4);
        hurt = new Picture(img2);
        setPicture(main);
        setVelX(0);
        setVelY(0);
        setX(50);
        setY(350 - w);
        hp = MAX_HP;

        //gravity
        Task t;
        t = new Task() {
            @Override
            public void run() {
                double y = getVelY();
                if (gravityOn) {
                    setVelY(y + .1);
                }

            }

        };
        Clock.addTask(t);
    }

    public void setHurt(boolean bool) {
        is_hurt = bool;
    }

    public boolean isHurt() {
        return is_hurt;
    }

    public int getScore() {
        return score;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int a) {
        if (a <= MAX_HP) {
            hp = a;
        }
    }

    public void addScore(int a) {
        score += a;
    }

    public void resetScore() {
        score = 0;
    }

    public void fullHeal() {
        hp = MAX_HP;
    }

    public void heal(int a) {
        if (hp + a <= MAX_HP) {
            hp += a;
        }
    }

    public void jump() {
        double y = getVelY();
        if (y == 0) {
            gravityOn = true;
            setVelY(-4);
            gamejump.play();
        }
    }

    public void hurt(int a) {
        if (!isHurt()) {
            setHurt(true);
            hp -= a;
            ouch.play();
            Game.jhp.setText(String.format("Health: %d", getHp()));
        }
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setVelX(0);
            setX(0);
        }
        if (se.xhi) {
            setVelX(0);
            setX(800 - SRITE_SIZE[0]);
        }
        if (se.ylo) {
            gravityOn = true;
            setVelY(-getY());

        }
        if (se.yhi) {
            setVelY(0);
            Game.touched_ground = true;
            gravityOn = false;
        }
    }

}
