/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab04;


import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author aeiler3
 */
public class LandingPad extends Sprite{
    
    private BufferedImage img;
    private Graphics graphics;
    private Picture picture;
    
    public LandingPad(SpriteComponent sc){
        super(sc);
        img = BasicFrame.createImage(100, 20);
        graphics = img.getGraphics();
        graphics.setColor(java.awt.Color.orange);
        graphics.fillRect(0, 0, 100, 20);
        this.setX(350);
        this.setY(380);
        picture = new Picture(img);
        this.setPicture(picture);
        
        
    }
    
}
