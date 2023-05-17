/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author @author Aidan Eiler
 */
public class Ball extends Sprite {

    private BufferedImage img;
    private BufferedImage img2;
    private Graphics g;
    public Picture main;
    public Picture gone;

    public Picture makeBall(int xpos, int ypos, Color color, int size) {
        img = BasicFrame.createImage(size, size);
        g = img.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        this.setX(xpos);
        this.setY(ypos);
        main = new Picture(img);
        img2 = BasicFrame.createImage(size, size);;
        g = img2.getGraphics();
        g.setColor(Color.cyan);
        g.fillOval(0, 0, size, size);
        gone = new Picture(img2);
        return main;
    }

    public Ball(SpriteComponent sc, int xpos, int ypos, Color color, int size) {
        super(sc);
        setPicture(makeBall(xpos, ypos, color, size));
        setDrawingPriority(-2);
    }

}
