package game;

import basicgraphics.BasicContainer;
import basicgraphics.BasicFrame;
import basicgraphics.Clock;
import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.Task;
import basicgraphics.sounds.ReusableClip;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author @author Aidan Eiler
 */
public class Game {

    public static SpriteComponent sc = new SpriteComponent();
    public static Dimension dimtest;
    static boolean isMovingLeft;
    static boolean isMovingRight;
    static boolean is_hurt;
    static boolean is_gone; //for blinking effect
    public static int blink_count;
    public static int hurt_count;
    public static JLabel jhp;
    public static Level1 level_1 = new Level1();
    public static Level2 level_2 = new Level2();
    public static Level3 level_3 = new Level3();
    public static boolean level_1_loaded = false; //these variables are needed to check overlap conditions because sprite.setActive(boolean) isn't deallocated. They're also used for goal
    public static boolean level_2_loaded = false;
    public static boolean level_3_loaded = false;
    public static boolean touched_ground = false;
    public static boolean game_over = false; //used to prevent multiple exit diologue boxes from showing up upon death
    public static final double move_speed = 2.5;
    final static ReusableClip basiccoin = new ReusableClip("basiccoin.wav");
    final static ReusableClip mediumcoin = new ReusableClip("mediumcoin.wav");
    final static ReusableClip bigcoin = new ReusableClip("bigcoin.wav");
    final static ReusableClip bigheal = new ReusableClip("bigheal.wav");
    final static ReusableClip smallheal = new ReusableClip("smallheal.wav");
    final static ReusableClip speedSound = new ReusableClip("speed.wav");
    final static ReusableClip slowSound = new ReusableClip("slow.wav");
    final static ReusableClip toot = new ReusableClip("toot.wav");

    public static void main(String[] args) throws IOException {

        //Initialization
        final BasicFrame bf = new BasicFrame("Super Square Man");
        final Container content = bf.getContentPane();
        final CardLayout cards = new CardLayout();
        content.setLayout(cards);
        hurt_count = 0;
        blink_count = 0;
        dimtest = new Dimension(800, 400);

        //Grouping
        BasicContainer bc1 = new BasicContainer();
        content.add(bc1, "Menu");
        final BasicContainer bc2 = new BasicContainer();
        content.add(bc2, "Game");
        //Background sprite
        sc = new SpriteComponent() {
            @Override
            public void paintBackground(Graphics g) {
                Dimension d = getSize();
                g.setColor(Color.cyan);
                g.fillRect(0, 0, d.width, d.height);
            }
        };
        sc.setPreferredSize(dimtest);

        String[][] menuLayout = {
            {"Title"},
            {"Start"},
            {"Exit"}};
        bc1.setStringLayout(menuLayout);
        JLabel jssm = new JLabel("Super Square Man");
        bc1.add("Title", jssm);
        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(content, "Game");
                // The BasicContainer bc2 must request the focus
                // otherwise, it can't get keyboard events.
                bc2.requestFocus();

                // Start the timer
                Clock.start(10);
            }
        });
        bc1.add("Start", jstart);
        JButton jexit = new JButton("Exit");
        jexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jexit, "Game has been successfully shut down.");
                System.exit(0);
            }
        });
        bc1.add("Exit", jexit);

        //game1
        String[][] gameLayout = {
            {"HP", "Score",},
            {"Game", "Game"}};

//  SPRITES
        final SuperSquareMan ssm = new SuperSquareMan(sc);
        Goal goal = new Goal(sc, 700, 40, 40, 70);
        Ground g1 = new Ground(sc, 0, 400 - 20, Color.black, 800, 20);
        Ground g2 = new Ground(sc, 0, 225, Color.black, 150, 20);
        Ground g3 = new Ground(sc, 150, 100, Color.black, 200, 20);
        Ground g4 = new Ground(sc, 200, 380 - 63, Color.black, 50, 3);
        Ground g5 = new Ground(sc, 300, 380 - 63, Color.black, 50, 3);
        Ground g6 = new Ground(sc, 400, 380 - 63, Color.black, 50, 3);
        Ground g7 = new Ground(sc, 500, 380 - 63, Color.black, 50, 3);
        Ground g8 = new Ground(sc, 600, 380 - 63, Color.black, 50, 3);
        Ground g9 = new Ground(sc, 700, 260, Color.black, 100, 20);
        Ground g10 = new Ground(sc, 350, 225, Color.black, 150, 20);
        Ground g11 = new Ground(sc, 0, 140, Color.black, 100, 20);
        Ground g12 = new Ground(sc, 550, 100, Color.black, 250, 20);
        Ceiling c1 = new Ceiling(sc, 150, 100 + 20, 650, 5);
        Ceiling c2 = new Ceiling(sc, 0, 225 + 20, 650, 5);
        Ceiling c3 = new Ceiling(sc, 700, 260 + 20, 100, 5);
        Ceiling c4 = new Ceiling(sc, 0, 140 + 20, 100, 5);
        Wall w1 = new Wall(sc, 200, 380 - 60, Color.black, 50, 60);
        Wall w2 = new Wall(sc, 300, 380 - 60, Color.black, 50, 60);
        Wall w3 = new Wall(sc, 400, 380 - 60, Color.black, 50, 60);
        Wall w4 = new Wall(sc, 500, 380 - 60, Color.black, 50, 60);
        Wall w5 = new Wall(sc, 600, 380 - 60, Color.black, 50, 60);
        SpeedGround spg1 = new SpeedGround(sc, 150, 225, 200, 20);
        SlowGround sg1 = new SlowGround(sc, 450, 225, 200, 20);
        HurtGround hg1 = new HurtGround(sc, 350, 100, 200, 20);
        SmallCoin sc1 = new SmallCoin(sc, 218, 290);
        SmallCoin sc2 = new SmallCoin(sc, 618, 290);
        MediumCoin mc1 = new MediumCoin(sc, 318, 290);
        MediumCoin mc2 = new MediumCoin(sc, 518, 290);
        MediumCoin mc3 = new MediumCoin(sc, 710, 230);
        BigCoin bco1 = new BigCoin(sc, 413, 290);
        BigCoin bco2 = new BigCoin(sc, 390, 195);
        BigCoin bco3 = new BigCoin(sc, 60, 110);
        SmallHealingOrb smo1 = new SmallHealingOrb(sc, 600, 70);
        level_1.add(g1);
        level_1.add(g2);
        level_1.add(g3);
        level_1.add(g4);
        level_1.add(g5);
        level_1.add(g6);
        level_1.add(g7);
        level_1.add(g8);
        level_1.add(g9);
        level_1.add(g10);
        level_1.add(g11);
        level_1.add(g12);
        level_1.add(w1);
        level_1.add(w2);
        level_1.add(w3);
        level_1.add(w4);
        level_1.add(w5);
        level_1.add(c1);
        level_1.add(c2);
        level_1.add(c3);
        level_1.add(c4);
        level_1.add(spg1);
        level_1.add(sg1);
        level_1.add(hg1);
        level_1.add(sc1);
        level_1.add(sc2);
        level_1.add(mc1);
        level_1.add(mc2);
        level_1.add(mc3);
        level_1.add(bco1);
        level_1.add(bco2);
        level_1.add(bco3);
        level_1.add(smo1);
        
        Ground g13 = new Ground(sc, 0, 400 - 20, Color.black, 125, 20);
        Ground g14 = new Ground(sc, 0, 275, Color.black, 650, 20);
        Ground g15 = new Ground(sc, 150, 150, Color.black, 650, 20);
        Ground g16 = new Ground(sc, 700, 325, Color.black, 100, 20);
        Ground g17 = new Ground(sc, 0, 200, Color.black, 100, 20);
        Ground g18 = new Ground(sc, 155, 380, Color.black, 30, 20);
        Ground g19 = new Ground(sc, 215, 380, Color.black, 30, 20);
        Ground g20 = new Ground(sc, 275, 380, Color.black, 30, 20);
        Ground g21 = new Ground(sc, 335, 380, Color.black, 30, 20);
        Ground g22 = new Ground(sc, 395, 380, Color.black, 30, 20);
        Ground g23 = new Ground(sc, 455, 380, Color.black, 30, 20);
        Ground g24 = new Ground(sc, 515, 380, Color.black, 30, 20);
        Ground g25 = new Ground(sc, 575, 380, Color.black, 30, 20);
        Ground g26 = new Ground(sc, 635, 380, Color.black, 30, 20);
        Ground g27 = new Ground(sc, 695, 380, Color.black, 105, 20);
        Ground g28 = new Ground(sc, 200, 225, Color.black, 40, 3);
        Ground g29 = new Ground(sc, 290, 225, Color.black, 40, 3);
        Ground g30 = new Ground(sc, 380, 225, Color.black, 40, 3);
        Ground g31 = new Ground(sc, 470, 225, Color.black, 40, 3);
        Ground g32 = new Ground(sc, 560, 225, Color.black, 40, 3);
        Ground g33 = new Ground(sc, 216 + 50, 218 - 125, Color.black, 7, 7);
        Ground g34 = new Ground(sc, 306 + 50, 218 - 125, Color.black, 7, 7);
        Ground g35 = new Ground(sc, 396 + 50, 218 - 125, Color.black, 7, 7);
        Ground g36 = new Ground(sc, 486 + 50, 218 - 125, Color.black, 7, 7);
        Ground g37 = new Ground(sc, 576 + 50, 218 - 125, Color.black, 7, 7);
        Ground g38 = new Ground(sc, 150 + 50, 225 - 95, Color.black, 50, 20);
        Ceiling c5 = new Ceiling(sc, 150, 150 + 20, 650, 5);
        Ceiling c6 = new Ceiling(sc, 0, 275 + 20, 650, 5);
        Ceiling c7 = new Ceiling(sc, 700, 325 + 20, 100, 5);
        Ceiling c8 = new Ceiling(sc, 0, 175 + 40, 100, 5);
        HurtGround hg2 = new HurtGround(sc, 125, 380, 30, 20);
        HurtGround hg3 = new HurtGround(sc, 185, 380, 30, 20);
        HurtGround hg4 = new HurtGround(sc, 245, 380, 30, 20);
        HurtGround hg5 = new HurtGround(sc, 305, 380, 30, 20);
        HurtGround hg6 = new HurtGround(sc, 365, 380, 30, 20);
        HurtGround hg7 = new HurtGround(sc, 425, 380, 30, 20);
        HurtGround hg8 = new HurtGround(sc, 485, 380, 30, 20);
        HurtGround hg9 = new HurtGround(sc, 545, 380, 30, 20);
        HurtGround hg10 = new HurtGround(sc, 605, 380, 30, 20);
        HurtGround hg11 = new HurtGround(sc, 665, 380, 30, 20);
        Wall w6 = new Wall(sc, 200, 225, Color.black, 40, 50);
        Wall w7 = new Wall(sc, 290, 225, Color.black, 40, 50);
        Wall w8 = new Wall(sc, 380, 225, Color.black, 40, 50);
        Wall w9 = new Wall(sc, 470, 225, Color.black, 40, 50);
        Wall w10 = new Wall(sc, 560, 225, Color.black, 40, 50);
        Wall w11 = new Wall(sc, 200 + 50, 225 - 125, Color.red, 40, 50);
        Wall w12 = new Wall(sc, 290 + 50, 225 - 125, Color.red, 40, 50);
        Wall w13 = new Wall(sc, 380 + 50, 225 - 125, Color.red, 40, 50);
        Wall w14 = new Wall(sc, 470 + 50, 225 - 125, Color.red, 40, 50);
        Wall w15 = new Wall(sc, 560 + 50, 225 - 125, Color.red, 40, 50);
        BigHealingOrb bho1 = new BigHealingOrb(sc, 700, 290);
        BigHealingOrb bho2 = new BigHealingOrb(sc, 80, 170);
        BigCoin bco4 = new BigCoin(sc, 213, 100);
        BigCoin bco5 = new BigCoin(sc, 80, 240);
        MediumCoin mc4 = new MediumCoin(sc, 615, 250);
        HurtGround hg12 = new HurtGround(sc, 150, 255, 50, 20);
        HurtGround hg13 = new HurtGround(sc, 240, 255, 50, 20);
        HurtGround hg14 = new HurtGround(sc, 330, 255, 50, 20);
        HurtGround hg15 = new HurtGround(sc, 420, 255, 50, 20);
        HurtGround hg16 = new HurtGround(sc, 510, 255, 50, 20);
        HurtGround hg17 = new HurtGround(sc, 216, 218, 7, 7);
        HurtGround hg18 = new HurtGround(sc, 306, 218, 7, 7);
        HurtGround hg19 = new HurtGround(sc, 396, 218, 7, 7);
        HurtGround hg20 = new HurtGround(sc, 486, 218, 7, 7);
        HurtGround hg21 = new HurtGround(sc, 576, 218, 7, 7);
        HurtGround hg22 = new HurtGround(sc, 240 + 50, 255 - 125, 50, 20);
        HurtGround hg23 = new HurtGround(sc, 330 + 50, 255 - 125, 50, 20);
        HurtGround hg24 = new HurtGround(sc, 420 + 50, 255 - 125, 50, 20);
        HurtGround hg25 = new HurtGround(sc, 510 + 50, 255 - 125, 50, 20);
        HurtGround hg26 = new HurtGround(sc, 200 + 50, 225 - 125, 40, 3);
        HurtGround hg27 = new HurtGround(sc, 290 + 50, 225 - 125, 40, 3);
        HurtGround hg28 = new HurtGround(sc, 380 + 50, 225 - 125, 40, 3);
        HurtGround hg29 = new HurtGround(sc, 470 + 50, 225 - 125, 40, 3);
        HurtGround hg30 = new HurtGround(sc, 560 + 50, 225 - 125, 40, 3);


        //LEVEL_3
        Wall w16 = new Wall(sc, 0, 200, Color.black, 100, 200);
        Wall w17 = new Wall(sc, 700, 200, Color.black, 100, 200);
        Wall w18 = new Wall(sc, 325, 200, Color.black, 25, 200);
        Wall w19 = new Wall(sc, 580, 200, Color.black, 25, 200);
        HurtGround hg31 = new HurtGround(sc, 0, 380, 800, 20);
        hg31.setDrawingPriority(-4);
        SlowGround sg2 = new SlowGround(sc, 0, 197, 100, 3);
        Ground g39 = new Ground(sc, 700, 197, Color.white, 100, 3);
        SpeedGround spg2 = new SpeedGround(sc, 325, 197, 25, 3);
        SpeedGround spg3 = new SpeedGround(sc, 580, 197, 25, 3);
        BigCoin bco6 = new BigCoin(sc, 325, 165);
        BigCoin bco7 = new BigCoin(sc, 580, 165);
        level_3.add(g39);
        level_3.add(w16);
        level_3.add(w17);
        level_3.add(w18);
        level_3.add(w19);
        level_3.add(hg31);
        level_3.add(bco6);
        level_3.add(bco7);
        level_3.add(sg2);
        level_3.add(spg2);
        level_3.add(spg3);
        level_1.load();
        
        //make clouds
        final int CLOUD_COUNT = 10;
        Dimension d = sc.getSize();
        Random rand = new Random();
        rand.setSeed(0);
        for (int i = 0; i <= CLOUD_COUNT; i++) {
            Cloud cloud = new Cloud(sc);
            cloud.setDrawingPriority(-5);
            cloud.setPicture(Cloud.cloud);

        }

        jhp = new JLabel(String.format("Health: %d", ssm.getHp()));
        JLabel jscore = new JLabel(String.format("Score: %d", ssm.getScore()));

        bc2.setStringLayout(gameLayout);
        bc2.add("Game", sc);
        bc2.add("HP", jhp);
        bc2.add("Score", jscore);
        bf.show();

        //game stuff
        Task blinking_sprites;
        blinking_sprites = new Task() {
            @Override
            public void run() {
                blink_count++;
                if (blink_count == Integer.MAX_VALUE) {
                    blink_count = 0;
                }
                if (blink_count % 100 == 10) //MAKE ALL COINS AND HEALTH BLINK
                {
                    if (!is_gone) {
                        // sc1.setPicture(sc1.gone);
                        is_gone = true;
                    } else {
                        // sc1.setPicture(sc1.main);
                        is_gone = false;
                    }
                }
            }

        };

        Task check_health;
        check_health = new Task() {
            @Override
            public void run() {
                if (ssm.getHp() == 0 && !game_over) {
                    game_over = true;
                    terminate();
                    ssm.setActive(false);
                    ssm.resetScore();
                    JOptionPane.showMessageDialog(jexit, "Your HP went to zero. You lost!");
                    System.exit(0);
                }
            }

        };

        Task ouch;
        ouch = new Task() {
            @Override
            public void run() {
                if (ssm.isHurt()) {

                    ssm.setPicture(ssm.hurt);
                    hurt_count++;
                }
                if (hurt_count == Integer.MAX_VALUE) {
                    hurt_count = 0;
                }
                if (hurt_count % 100 == 99) //MAKE ALL COINS AND HEALTH BLINK
                {
                    ssm.setPicture(ssm.main);
                    ssm.setHurt(false);
                }
            }

        };

        Task overlap;
        overlap = new Task() {
            @Override
            public void run() {
                double curspeed = ssm.getVelX();
                if (level_1_loaded && Sprite.overlap(ssm, sg1)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed / 2);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed / 2);
                    }
                } else if (level_3_loaded && Sprite.overlap(ssm, sg2)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed / 2);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed / 2);
                    }
                } else if (level_1_loaded && Sprite.overlap(ssm, spg1)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed * 2);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed * 2);
                    }
                } else if (level_3_loaded && Sprite.overlap(ssm, spg2)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed * 2);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed * 2);
                    }
                } else if (level_3_loaded && Sprite.overlap(ssm, spg3)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed * 2);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed * 2);
                    }
                } else if (level_1_loaded && Sprite.overlap(ssm, hg1)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg2)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg3)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg4)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg5)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg6)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg7)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg8)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg9)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg10)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg11)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg12)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg13)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg14)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg15)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg16)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg17)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg18)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg19)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg20)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg21)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg22)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg23)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg24)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg25)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg26)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg27)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg28)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg29)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_2_loaded && Sprite.overlap(ssm, hg30)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_3_loaded && Sprite.overlap(ssm, hg31)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                    ssm.hurt(1);
                } else if (level_1_loaded && Sprite.overlap(ssm, g1)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g2)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g3)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g4)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g5)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g6)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g7)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g8)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g9)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g10)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g11)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, g12)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g13)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g14)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g15)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g16)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g17)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g18)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g19)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g20)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g21)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g22)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g23)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g24)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g25)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g26)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g27)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g28)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g29)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g30)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g31)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g32)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g33)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g34)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g35)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g36)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g37)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_2_loaded && Sprite.overlap(ssm, g38)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_3_loaded && Sprite.overlap(ssm, g39)) {
                    touched_ground = true;
                    ssm.gravityOn = false;
                } else if (level_1_loaded && Sprite.overlap(ssm, w1)) {
                    ssm.setVelX(0);
                } else if (level_1_loaded && Sprite.overlap(ssm, w2)) {
                    ssm.setVelX(0);
                } else if (level_1_loaded && Sprite.overlap(ssm, w3)) {
                    ssm.setVelX(0);
                } else if (level_1_loaded && Sprite.overlap(ssm, w4)) {
                    ssm.setVelX(0);
                } else if (level_1_loaded && Sprite.overlap(ssm, w5)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w6)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w7)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w8)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w9)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w10)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w11)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w12)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w13)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w14)) {
                    ssm.setVelX(0);
                } else if (level_2_loaded && Sprite.overlap(ssm, w15)) {
                    ssm.setVelX(0);
                } else if (level_3_loaded && Sprite.overlap(ssm, w16)) {
                    ssm.setVelX(0);
                } else if (level_3_loaded && Sprite.overlap(ssm, w17)) {
                    ssm.setVelX(0);
                } else if (level_3_loaded && Sprite.overlap(ssm, w18)) {
                    ssm.setVelX(0);
                } else if (level_3_loaded && Sprite.overlap(ssm, w19)) {
                    ssm.setVelX(0);
                } else if (level_1_loaded && Sprite.overlap(ssm, c1)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_1_loaded && Sprite.overlap(ssm, c2)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_1_loaded && Sprite.overlap(ssm, c3)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_1_loaded && Sprite.overlap(ssm, c4)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_2_loaded && Sprite.overlap(ssm, c5)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_2_loaded && Sprite.overlap(ssm, c6)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_2_loaded && Sprite.overlap(ssm, c7)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (level_2_loaded && Sprite.overlap(ssm, c8)) {
                    ssm.setVelY(ssm.getVelY());
                    ssm.gravityOn = true;
                } else if (touched_ground) {
                    ssm.gravityOn = true;
                    if (curspeed > 0) {
                        ssm.setVelX(move_speed);
                    } else if (curspeed < 0) {
                        ssm.setVelX(-move_speed);
                    }
                } else {
                    touched_ground = false;
                    ssm.gravityOn = true;
                }
                if (level_2_loaded && Sprite.overlap(ssm, goal)) {
                    if (level_1_loaded) {
                        level_1.unload();
                        level_2.load();
                        ssm.setX(50);
                        ssm.setY(350 - ssm.w);
                        goal.setY(80);
                        toot.play();
                    } else if (level_2_loaded) {
                        level_2.unload();
                        level_3.load();
                        ssm.setX(40);
                        ssm.setY(160);
                        ssm.setHp(1);
                        goal.setX(725);
                        goal.setY(200 - 73);
                        toot.play();
                    } else if (level_2_loaded) {
                        level_3.unload();
                        toot.play();
                        JOptionPane.showMessageDialog(jexit, "A winner is you!");
                        System.exit(0);
                    }
                }
                if (ssm.getSpriteComponent().getSize().height < ssm.getY() + ssm.getPicture().getHeight()) {
                    ssm.gravityOn = false;
                }
            }

        };
        Clock.addTask(overlap);

        Clock.addTask(blinking_sprites);

        Clock.addTask(ouch);

        Clock.addTask(check_health);

        sc.addSpriteSpriteCollisionListener(Ground.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<Ground, SuperSquareMan>() {
            @Override
            public void collision(Ground sp1, SuperSquareMan sp2) {
                sp2.setVelY(0);
                sp2.gravityOn = false;
            }
        });
        sc.addSpriteSpriteCollisionListener(SpeedGround.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<SpeedGround, SuperSquareMan>() {
            @Override
            public void collision(SpeedGround sp1, SuperSquareMan sp2) {
                sp2.setVelY(0);
                sp2.gravityOn = false;
                speedSound.play();
            }
        });
        sc.addSpriteSpriteCollisionListener(HurtGround.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<HurtGround, SuperSquareMan>() {
            @Override
            public void collision(HurtGround sp1, SuperSquareMan sp2) {
                sp2.setVelY(0);
                sp2.gravityOn = false;
            }

        });
        sc.addSpriteSpriteCollisionListener(SlowGround.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<SlowGround, SuperSquareMan>() {
            @Override
            public void collision(SlowGround sp1, SuperSquareMan sp2) {
                sp2.setVelY(0);
                sp2.gravityOn = false;
                slowSound.play();
            }
        });
        sc.addSpriteSpriteCollisionListener(Wall.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<Wall, SuperSquareMan>() {
            @Override
            public void collision(Wall sp1, SuperSquareMan sp2) {
                sp2.setVelX(0);
            }
        });
        sc.addSpriteSpriteCollisionListener(Ceiling.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<Ceiling, SuperSquareMan>() {
            @Override
            public void collision(Ceiling sp1, SuperSquareMan sp2) {
                ssm.setVelY(0);
                sp2.gravityOn = true;
            }
        });
        sc.addSpriteSpriteCollisionListener(BigHealingOrb.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<BigHealingOrb, SuperSquareMan>() {
            @Override
            public void collision(BigHealingOrb sp1, SuperSquareMan sp2) {
                sp2.fullHeal();
                jhp.setText(String.format("Health: %d", ssm.getHp()));
                sp1.setActive(false);
                bigheal.play();
            }
        });
        sc.addSpriteSpriteCollisionListener(SmallHealingOrb.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<SmallHealingOrb, SuperSquareMan>() {
            @Override
            public void collision(SmallHealingOrb sp1, SuperSquareMan sp2) {
                sp2.heal(1);
                jhp.setText(String.format("Health: %d", ssm.getHp()));
                sp1.setActive(false);
                smallheal.play();
            }
        });
        sc.addSpriteSpriteCollisionListener(SmallCoin.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<SmallCoin, SuperSquareMan>() {
            @Override
            public void collision(SmallCoin sp1, SuperSquareMan sp2) {
                sp2.addScore(5);
                jscore.setText(String.format("Score: %d", ssm.getScore()));
                basiccoin.play();
                sp1.setActive(false);
            }
        });
        sc.addSpriteSpriteCollisionListener(MediumCoin.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<MediumCoin, SuperSquareMan>() {
            @Override
            public void collision(MediumCoin sp1, SuperSquareMan sp2) {
                sp2.addScore(20);
                jscore.setText(String.format("Score: %d", ssm.getScore()));
                mediumcoin.play();
                sp1.setActive(false);
            }
        });
        sc.addSpriteSpriteCollisionListener(BigCoin.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<BigCoin, SuperSquareMan>() {
            @Override
            public void collision(BigCoin sp1, SuperSquareMan sp2) {
                sp2.addScore(50);
                jscore.setText(String.format("Score: %d", ssm.getScore()));
                bigcoin.play();
                sp1.setActive(false);
            }
        });
        sc.addSpriteSpriteCollisionListener(Goal.class, SuperSquareMan.class, new SpriteSpriteCollisionListener<Goal, SuperSquareMan>() {
            @Override
            public void collision(Goal sp1, SuperSquareMan sp2) {
                if (level_1_loaded) {
                    level_1.unload();
                    level_2.load();
                    ssm.setX(50);
                    ssm.setY(350 - ssm.w);
                    goal.setY(80);
                    toot.play();
                } else if (level_2_loaded) {
                    level_2.unload();
                    level_3.load();
                    ssm.setX(40);
                    ssm.setY(160);
                    ssm.setHp(1);
                    goal.setX(725);
                    goal.setY(200 - 73);
                    toot.play();
                } else {
                    level_3.unload();
                    toot.play();
                    JOptionPane.showMessageDialog(jexit, "A winner is you!");
                    System.exit(0);
                }
            }
        });
        // Note: Adding the listener to basic container 2.
        bc2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT && !isMovingRight) {
                    isMovingRight = true;
                    ssm.setVelX(move_speed);
                } else if (ke.getKeyCode() == KeyEvent.VK_LEFT && !isMovingLeft) {
                    isMovingLeft = true;
                    ssm.setVelX(-move_speed);
                } else if (ke.getKeyChar() == ' ') {
                    ssm.jump();

                }
            }
        });
        bc2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isMovingRight = false;
                    if (isMovingLeft) {
                        ssm.setVelX(-2.5);
                    } else {
                        ssm.setVelX(0);
                    }

                }
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    isMovingLeft = false;
                    if (isMovingRight) {
                        ssm.setVelX(2.5);
                    } else {
                        ssm.setVelX(0);
                    }
                }
            }
        });

        Clock.addTask(sc.moveSprites());

    }

    public static void terminate() {
        level_1.unload();
        level_2.unload();
        level_3.unload();
    }

    public static void print(String str) { //for debugging
        System.out.println(str);
    }
}
