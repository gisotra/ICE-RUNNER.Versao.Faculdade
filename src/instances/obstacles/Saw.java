package instances.obstacles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import loop.GCanvas;
import utilz.AnimationType;
import utilz.Screen;
import utilz.Sprite;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Sprite;
import utilz.Universal;

public class Saw extends Obstacles{ //extends Obstacles, que extende objects
    /*------------ ATRIBUTOS ------------*/
    BufferedImage sawSpriteSheet;
    Sprite<SawAnimation> sawSprite;
    
    /*------------ CONSTRUTOR ------------*/
    public Saw(Screen screen, GCanvas gc) {
        super(screen, gc);
        setY(Universal.SAW_SPAWN_Y);
        setX(Universal.OBST_SPAWN_X); //fora da tela na direita
        initSprite();
        initObstHitbox();
        setIsActive(false);
    }
    
    public void initSprite() {
        SpriteData sawData = SpriteLoader.spriteDataLoader().get("saw");
        try {
            sawSpriteSheet = ImageIO.read(getClass().getResource(sawData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        setWidth(80); //largura em px do FRAME ORIGINAL 
        setHeight(64); //altura em px do FRAME ORIGINAL
        sawSprite = new Sprite<>(sawSpriteSheet, this.heightO, this.widthO, SawAnimation.class, 1);
        
    }
    
    @Override
    protected void drawObstHitbox(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int) obs_hitbox.x, (int) obs_hitbox.y, (int) obs_hitbox.width, (int) obs_hitbox.height);
    }
    
    @Override
    public void initObstHitbox() { //x, y, largura, altura
        this.obs_hitbox = new Rectangle2D.Float(getX(), getY(), Universal.SAW_HITBOX_WIDTH, Universal.SAW_HITBOX_HEIGHT); //metade do tamanho
    }
    
    @Override
    public void render(Graphics2D g2d){
        sawSprite.render(g2d, (int) getX() - 70, (int) getY() - 50);
            if(Universal.showGrid){
                drawObstHitbox(g2d);
            }
    }
    
    /*========== Classe interna Para os Sprites ==========*/ 
    public enum SawAnimation implements AnimationType{
        STATIC;
        
        @Override
        public int getIndex(){
            return 0;
        }
        
        @Override
        public int getFrameCount(){
            return 1;
        }
    }

    
}