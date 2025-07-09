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

public class GameOverLocal implements ScreenStates{
    /*Imagens do fundo da tela de Game Over*/
    BufferedImage gameOverFundo;
    Spritesheet gameoversheet;
    /*Botões e seus respectivos sprites*/
    Buttons[] botoes = new Buttons[2];
    BufferedImage botaoMenuSprite;
    BufferedImage botaoRestartSprite;
    
    
    public GameOverLocal(){
        initSpriteGameOver();
        botoes[0] = new Buttons(5*Universal.TILES_SIZE, 4*Universal.TILES_SIZE - (Universal.TILES_SIZE/4), 48, 48, botaoMenuSprite, Gamestate.MENU); //botão de voltar ao menu
        botoes[1] = new Buttons(9*Universal.TILES_SIZE + (Universal.TILES_SIZE/2)  , 4*Universal.TILES_SIZE - (Universal.TILES_SIZE/4), 48, 48, botaoRestartSprite, Gamestate.LOCAL_MULTIPLAYER); //botao de voltar ao loop do jogo
    }
    
    public void initSpriteGameOver(){
        SpriteData gameoverData = SpriteLoader.spriteDataLoader().get("fundoGameOver");
        SpriteData buttMenuData = SpriteLoader.spriteDataLoader().get("gameover_menuButton");
        SpriteData buttRestartData = SpriteLoader.spriteDataLoader().get("gameover_restartButton");

        try {
            gameOverFundo = ImageIO.read(getClass().getResource(gameoverData.getPath()));
            botaoMenuSprite = ImageIO.read(getClass().getResource(buttMenuData.getPath()));
            botaoRestartSprite = ImageIO.read(getClass().getResource(buttRestartData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.gameoversheet = new Spritesheet(gameOverFundo, 288, 512, 0.0, Universal.SCALE);
    }
    
    /*-------------- MÉTODOS HERDADOS --------------*/
    
    @Override
    public void update() {
        for(Buttons but : botoes){
            if(but.cursorOver){
                //depois eu quero fazer um efeito de scale quando der hover nele, sei la
            } 
        }
    }

    @Override
    public void render(Graphics2D g2D) {
        gameoversheet.render(g2D, 0, 0);
        for(Buttons but : botoes){
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
        for(Buttons but : botoes){
            if(isIn(e, but)) {
                but.setCursorPressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(Buttons but : botoes){
            if(isIn(e, but)) {
                if(but.isCursorPressed()){
                    if(but.getState() == Gamestate.PLAYING_OFFLINE){
                        but.applyGamestate();
                        Screen.resetCoordenates();
                        Screen.startCoordenates();
                    } else {
                        but.applyGamestate();
                    }
                    break;
                }
            }
        }
        
        resetButtons();
    }

    private void resetButtons(){
        for(Buttons but : botoes){
            but.resetBooleans();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        for(Buttons but : botoes){
            but.setCursorOver(false);
        }
        
        for(Buttons but : botoes){
            if(isIn(e, but)) {
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
