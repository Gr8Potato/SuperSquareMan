/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.Dimension;
import java.util.Random;
import static game.Game.*;


/**
 *
 * @author Aidan Eiler
 */
public class Cloud extends Sprite{
    
    public static Picture cloud;
    
    public Cloud(SpriteComponent sc) {
        super(sc);
        cloud = new Picture("cloud.png");
        setPicture(cloud);
        Random rand = new Random();
        
        //Dimension dim = sc.getSize();
        int d = rand.nextInt(dimtest.width-1)+1;
        int w = rand.nextInt(dimtest.height-250)+1;
        assert d > 0;
        assert w > 0;
        setX(d);
        setY(w);
        
        setVelX(1);
        setVelY(0);
    }
    
     @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setX(sc.getSize().width - getWidth());
        }
        if (se.xhi) {
            setX(0);
        }
    }
    
}
