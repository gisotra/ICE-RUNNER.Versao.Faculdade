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

public class MultiplayerMenu implements ScreenStates {
    BufferedImage multMenuFundo;
    Spritesheet multMenusheet;
    Buttons[] botoesMenu = new Buttons[3];
    BufferedImage botaoAsServer;
    BufferedImage botaoAsClient;
    BufferedImage botaoExit;
    
    public MultiplayerMenu(){
        initSpriteMenu();
        botoesMenu[0] = new Buttons(4*Universal.TILES_SIZE, 3*Universal.TILES_SIZE + (Universal.TILES_SIZE/4), 48, 48, botaoAsServer, Gamestate.SERVER_HOSTING); //servidor
        botoesMenu[1] = new Buttons(8*Universal.TILES_SIZE + (Universal.TILES_SIZE/2)  , 3*Universal.TILES_SIZE + (Universal.TILES_SIZE/4), 48, 48, botaoAsClient, Gamestate.CLIENT_CONNECTING); //cliente
        botoesMenu[2] = new Buttons(20, 20, 48, 48, botaoExit, Gamestate.MENU); //voltar
    }
    
    public void initSpriteMenu(){
        SpriteData menuData = SpriteLoader.spriteDataLoader().get("fundoMenu");
        SpriteData asClientData = SpriteLoader.spriteDataLoader().get("asServerButton");
        SpriteData asServerData = SpriteLoader.spriteDataLoader().get("asClientButton");
        SpriteData exitData = SpriteLoader.spriteDataLoader().get("exitbutton");
        
        try {
            multMenuFundo = ImageIO.read(getClass().getResource(menuData.getPath()));
            botaoAsServer = ImageIO.read(getClass().getResource(asClientData.getPath()));
            botaoAsClient = ImageIO.read(getClass().getResource(asServerData.getPath()));
            botaoExit = ImageIO.read(getClass().getResource(exitData.getPath()));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.multMenusheet = new Spritesheet(multMenuFundo, 288, 512, 0.0, Universal.SCALE); 
    }

    /*-------------- MÉTODOS HERDADOS --------------*/
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2D) {
        multMenusheet.render(g2D, 0, 0);
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
                        but.applyGamestate();
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
