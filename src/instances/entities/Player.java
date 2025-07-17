package instances.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import loop.GCanvas;
import utilz.AnimationType;
import utilz.Screen;
import utilz.Sprite;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Universal;

public class Player extends Entities{
    
    //https://www.youtube.com/watch?v=rTVoyWu8r6g
    /*------------ ATRIBUTOS ------------*/
    private int playerIndex;
    private boolean isDummy = false;
    private Movement movement;
    private Collider collider;
    private BufferedImage playerSpriteSheet;
    private BufferedImage shadow;
    private BufferedImage floormark;
    private SpriteData playerData;
    
    /*Controle de Dash*/
    private long lastDash = 0;
    private long dashCooldown = 250; //evita spam
    
    /*Flags booleanas de Movimento*/
    public boolean right = false;
    public boolean left = false;
    public boolean up = false;
    public boolean down = false;
    public boolean dead = false;
    public boolean dash = false;
    public boolean jump = false;
    
    /*Sprites*/
    private Sprite<PlayerAnimation> playerSprite;
    private Sprite<ShadowAnimation> shadowSprite;
    private Sprite<MarkAnimation> markSprite;
    public PlayerAnimation playerAction = PlayerAnimation.IDLE;
    private ScarfRope scarf;
    
    /*Power Ups*/
    private boolean isPowered = false;
    private boolean sword = false;
    private boolean marioCap = false;
    private boolean shielded = false;
    
    
    
    public Player(Screen screen, GCanvas gc, int playerCode, boolean isDummy){
        super(screen, gc);
        this.playerIndex = playerCode;
        setPlayerIndex(playerCode);
        setIsDummy(isDummy);
        movement = new Movement(this);
        collider = new Collider(this);
        initSprite();
        setX(120 * playerCode);
        setY(360);
        movement.setIsJumping(true); //para ele cair logo de primeira
        scarf = new ScarfRope(this, 1.5f * Universal.SCALE);
        setIsActive(true);
    }     
   
    public synchronized void updateNetworkState(float x, float y, PlayerAnimation anim) {
        this.x = x;
        this.y = y;
        this.playerAction = anim;
    }
    
    public void initSprite(){
        if(playerIndex == 1){
        playerData = SpriteLoader.spriteDataLoader().get("player1");
        } else if (playerIndex == 2){
        playerData = SpriteLoader.spriteDataLoader().get("player2");    
        }
        SpriteData shadowData = SpriteLoader.spriteDataLoader().get("shadow");
        SpriteData markData = SpriteLoader.spriteDataLoader().get("mark");
        try {
            playerSpriteSheet = ImageIO.read(getClass().getResource(playerData.getPath()));
            shadow = ImageIO.read(getClass().getResource(shadowData.getPath()));
            floormark = ImageIO.read(getClass().getResource(markData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        setWidth(32);
        setHeight(32);
        /*Inicio as Sprites*/
        playerSprite = new Sprite<>(playerSpriteSheet, this.heightO, this.widthO, PlayerAnimation.class, 15);
        shadowSprite = new Sprite<>(shadow, 32, 32, ShadowAnimation.class, 1);
        markSprite = new Sprite<>(floormark, 32, 32, MarkAnimation.class, 1);
    }
    
    @Override
    public void update(float deltaTime){
        long currentTime = System.currentTimeMillis();
        if(dash 
                && movement.isCanDash() 
                    && !movement.isIsDashing() 
                        && currentTime - lastDash >= dashCooldown){
            movement.Dash();
            dash = false;
            lastDash = currentTime;
        }
        movement.updateMovement(deltaTime);
        collider.updateCollisionArea();
        
        if(collider.verifyNearby()){ //somente se HÁ um obstáculo dedd asdasdas das dantro da minha range de colisão 
            collider.verifyCollission();
        }
        scarf.update(deltaTime);
        updateHitbox();
    }

    @Override
    public void render(Graphics2D g2d){
        playerSprite.setAction(playerAction);
        playerSprite.update(); //altero o state da minha animacao
        
        if(!marioCap){
        playerSprite.render(g2d, (int) getX() - 12, (int) getY());
        scarf.render(g2d);
        } else {
            //renderizo com 50% de transparencia
        }
        //Renderizo a sombra
        if(!dead){    
            shadowSprite.render(g2d, (int)getX() - 21, (int) Universal.groundY - (Universal.TILES_SIZE / 6) + 40);
        }
        
        if(Universal.showGrid){
            drawHitbox(g2d);
            collider.drawCollisionArea(g2d);
            markSprite.render(g2d, (int) getX() - 21, (int) Universal.groundY - (Universal.TILES_SIZE / 6) + 40);
        }
    }

    /*------------ GETTERS AND SETTERS ------------*/
    public synchronized float getX() {
        return x;
    }
    public synchronized float getY() {
        return y;
    }

    public synchronized void setX(float x) {
        this.x = x;
    }

    public synchronized void setY(float y) {
        this.y = y;
    }

    public synchronized boolean isDead() {
        return dead;
    }

    public synchronized void setDead(boolean dead) {
        this.dead = dead;
    }

    public synchronized int getHeight() {
        return heightO;
    }

    public synchronized int getPlayerIndex(){
        return playerIndex;
    }

    public synchronized void setPlayerIndex(int playerIndex){
        this.playerIndex = playerIndex;
    }

    public synchronized Movement getMovement(){
        return movement;
    }

    public boolean isMarioCap() {
        return marioCap;
    }

    public void setMarioCap(boolean marioCap) {
        this.marioCap = marioCap;
    }

    public boolean isDummy(){
        return isDummy;
    }

    public void setIsDummy(boolean isDummy){
        this.isDummy = isDummy;
    }
    
    
    /*========== Classes internas Para os Sprites ==========*/ 
    /*Player*/
    public enum PlayerAnimation implements AnimationType{
        IDLE(0, 2),
        RUNNING(0, 2),
        JUMP(1, 3),
        FALLING(2, 1),
        DEAD(3, 1);
        //DASH(4, 2);

        private final int index;
        private final int frameCount;
    
        PlayerAnimation(int index, int frameCount){
            this.index = index;
            this.frameCount = frameCount;
        }

        @Override
        public int getIndex(){
            return index;
        }
    
        @Override
        public int getFrameCount(){
            return frameCount;
       }
    }
    
    /*Sombra*/
    public enum ShadowAnimation implements AnimationType{
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
    
    /*FloorMark*/
    public enum MarkAnimation implements AnimationType{
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
