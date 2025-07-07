package ui;

import gamestates.Gamestate;
import java.awt.Graphics2D;
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


public class POffline implements ScreenStates {
    BufferedImage menuFundo;
    Spritesheet menusheet;
    
    public POffline(){
        initSpriteMenu();
    }
    
    public void initSpriteMenu(){
        SpriteData menuData = SpriteLoader.spriteDataLoader().get("backgroundDay");
        
        try {
            menuFundo = ImageIO.read(getClass().getResource(menuData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.menusheet = new Spritesheet(menuFundo, 288, 512, 0.0, Universal.SCALE); 
    }

    /*-------------- MÉTODOS HERDADOS --------------*/
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2D) {
        menusheet.render(g2D, 0, 0);
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
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    private void resetButtons() {
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
