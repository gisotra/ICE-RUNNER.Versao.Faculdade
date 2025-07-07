package ui;

import gamestates.Gamestate;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import utilz.Screen;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Spritesheet;
import utilz.Universal;

public class Menu implements ScreenStates {
    BufferedImage menuFundo;
    Spritesheet menusheet;
    Buttons[] botoesMenu = new Buttons[3];
    BufferedImage botaoOfflineSprite;
    BufferedImage botaoOnlineSprite;
    BufferedImage botaoExitSprite;
    
    public Menu(){
        initSpriteMenu();
        botoesMenu[0] = new Buttons(2 * Universal.TILES_SIZE, 1 * Universal.TILES_SIZE, 64, 48, botaoOfflineSprite, Gamestate.PLAYING_OFFLINE); //OFFLINE
        botoesMenu[1] = new Buttons(2 * Universal.TILES_SIZE, 3 * Universal.TILES_SIZE, 64, 48, botaoOnlineSprite, Gamestate.MULTIPLAYER_MENU); //ONLINE
        botoesMenu[2] = new Buttons(2 * Universal.TILES_SIZE, 5 * Universal.TILES_SIZE, 64, 48, botaoExitSprite, Gamestate.END); //EXIT
    }
    
    public void initSpriteMenu(){
        SpriteData menuData = SpriteLoader.spriteDataLoader().get("fundoMenu");
        SpriteData buttOfflineData = SpriteLoader.spriteDataLoader().get("menu_playOfflineButton");
        SpriteData buttOnlineData = SpriteLoader.spriteDataLoader().get("menu_playOnlineButton");
        SpriteData buttExitData = SpriteLoader.spriteDataLoader().get("menu_exitButton");
        
        try {
            menuFundo = ImageIO.read(getClass().getResource(menuData.getPath()));
            botaoOfflineSprite = ImageIO.read(getClass().getResource(buttOfflineData.getPath()));
            botaoOnlineSprite = ImageIO.read(getClass().getResource(buttOnlineData.getPath()));
            botaoExitSprite = ImageIO.read(getClass().getResource(buttExitData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.menusheet = new Spritesheet(menuFundo,288, 512, 0.0, Universal.SCALE); 
    }

    /*-------------- MÉTODOS HERDADOS --------------*/
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2D) {
        menusheet.render(g2D, 0, 0);
        for (Buttons but : botoesMenu) {
            but.render(g2D);
        }
    }

    @Override
    public boolean isIn(MouseEvent e, Buttons mb) {
        return mb.getDimensoes().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                but.setCursorPressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                if (but.isCursorPressed()) {
                    if (but.getState() == Gamestate.PLAYING_OFFLINE) {
                        but.applyGamestate();
                        Screen.resetCoordenates();
                        Screen.startCoordenates();
                    } else {
                        but.applyGamestate();
                    }
                }
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        for (Buttons but : botoesMenu) {
            but.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            but.setCursorOver(false);
        }

        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                but.setCursorOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
