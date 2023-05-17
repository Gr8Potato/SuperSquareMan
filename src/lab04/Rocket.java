/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab04;

import basicgraphics.BasicFrame;
import basicgraphics.Clock;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.Task;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author aeiler3
 */
public class Rocket extends Sprite {

    private BufferedImage img;
    private Graphics2D graphics;
    private int w;
    private int h;
    public static Picture rocket;

    public static Picture rocketWithFlame;

    public Rocket(SpriteComponent sc) {
        super(sc);
        img = BasicFrame.createImage(20, 30);
        graphics = (Graphics2D) img.getGraphics();
        w = img.getWidth();
        h = img.getHeight();
        graphics.setColor(java.awt.Color.blue);

        int[] xArray = {w / 2, w, w, 0, 0};
        int[] yArray = {0, h / 3, 2 * h / 3, 2 * h / 3, h / 3};
        graphics.fillPolygon(xArray, yArray, 5);

        graphics.drawLine(0, 2 * h / 3, 0, h);
        graphics.drawLine(w - 1, 2 * h / 3, w - 1, h);

        rocket = new Picture(img);
        this.setPicture(rocket);
        setX(w / 2);
        setY(h / 2);

        //fire
        img = BasicFrame.createImage(20, 30);
        graphics = (Graphics2D) img.getGraphics();
        w = img.getWidth();
        h = img.getHeight();
        graphics.setColor(java.awt.Color.blue);

        int[] xArray2 = {w / 2, w, w, 0, 0};
        int[] yArray2 = {0, h / 3, 2 * h / 3, 2 * h / 3, h / 3};
        graphics.fillPolygon(xArray2, yArray2, 5);

        graphics.drawLine(0, 2 * h / 3, 0, h);
        graphics.drawLine(w - 1, 2 * h / 3, w - 1, h);

        graphics.setColor(java.awt.Color.orange);
        int[] xArray3 = {w / 4, w / 2, 3 * w / 4};
        int[] yArray3 = {2 * h / 3, h, 2 * h / 3};

        graphics.fillPolygon(xArray3, yArray3, 3);
        rocketWithFlame = new Picture(img);
        this.setPicture(rocketWithFlame);
        //idk setX(w / 2);
        //idk setY(h / 2);
        Task t = new Task() {
            public void run() {
                double y = getVelY();
                setVelY(y + .002);

            }

        };
        Clock.addTask(t);
    }

    public void processEvent(SpriteCollisionEvent sce) {
        SpriteComponent sc = getSpriteComponent();
        if (sce.xlo) {
            setX(sc.getSize().width-getWidth());
        }
        if (sce.xhi) {
            setX(0);
        }
        if (sce.yhi) {
            JOptionPane.showMessageDialog(sc, "You flew off into space");
            System.exit(0);
        }
        if (sce.ylo) {
            JOptionPane.showMessageDialog(sc, "You missed the landing pad.");
            System.exit(0);
        }

    }

}
