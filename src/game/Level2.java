/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import basicgraphics.Sprite;
import static game.Game.level_2;
import static game.Game.level_2_loaded;
import java.util.ArrayList;

/**
 *
 * @author DaUlt
 */
public class Level2 extends ArrayList<Sprite> {

    public void load() {
        for (Sprite sprite : level_2) {
            sprite.setActive(true);
            level_2_loaded = true;

        }
    }

    public void unload() {
        for (Sprite sprite : level_2) {
            sprite.setActive(false);
            level_2_loaded = false;

        }
    }
}
