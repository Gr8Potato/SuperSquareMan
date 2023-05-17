/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import basicgraphics.SpriteComponent;
import java.awt.Color;

/**
 *
 * @author DaUlt
 */
public class SmallCoin extends Ball{

    public SmallCoin(SpriteComponent sc, int xpos, int ypos){
    super(sc, xpos, ypos, Color.yellow, 15);
    }
}
