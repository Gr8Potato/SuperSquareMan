/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author DaUlt
 */
public class Ground extends Sprite{
    
    private BufferedImage img;
    private Graphics graphics;
    private Picture picture;
    
    public Ground(SpriteComponent sc, int xpos, int ypos, Color color, int width, int height){
        super(sc);
        img = BasicFrame.createImage(width, height);
        graphics = img.getGraphics();
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);
        this.setX(xpos);
        this.setY(ypos);
        picture = new Picture(img);
        this.setPicture(picture);
        
    }

}
