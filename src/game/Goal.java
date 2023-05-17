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
public class Goal extends Ground{
        public Goal(SpriteComponent sc, int xpos, int ypos, int width, int height) {
        super(sc, xpos, ypos, Color.green, width, height);

    }
}
