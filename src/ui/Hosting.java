package ui;

import gamestates.Gamestate;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import utilz.AnimationType;
import utilz.Screen;
import utilz.Sprite;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Universal;

public class Hosting implements ScreenStates{
    
    private BufferedImage multMenuFundo;
    private Sprite<MultiplayerMenu.MultiplayerMenuAnimation> multMenuSprite;
    //adicionar o sprite pro loading 
    private Buttons[] botoesMenu = new Buttons[2];
    private BufferedImage botaoStartServer;
    private BufferedImage botaoExit;

    public Hosting(){
        
    }
    
    public void initSpriteMenu() {
        SpriteData menuData = SpriteLoader.spriteDataLoader().get("fundoMenu");
        SpriteData exitData = SpriteLoader.spriteDataLoader().get("exitbutton");

        try {
            
            multMenuFundo = ImageIO.read(getClass().getResource(menuData.getPath()));
            botaoExit = ImageIO.read(getClass().getResource(exitData.getPath()));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.multMenuSprite = new Sprite<>(multMenuFundo, 288, 512, MultiplayerMenu.MultiplayerMenuAnimation.class, 1);
    }
    
    /*-------------- MÉTODOS HERDADOS --------------*/
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2D) {
        multMenuSprite.render(g2D, 0, 0);
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
                    if (but.getState() == Gamestate.PLAYING) {
                        Universal.bothPlaying = true;
                        but.applyGamestate();
                        Screen.resetCoordenates();
                        Screen.startCoordenates();
                    } else {
                        Universal.bothPlaying = true;
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

    /*========== Classe interna Para o Sprite ==========*/
    public enum HostingAnimation implements AnimationType {
        STATIC;

        @Override
        public int getIndex() {
            return 0;
        }

        @Override
        public int getFrameCount() {
            return 1;
        }
    }
}
