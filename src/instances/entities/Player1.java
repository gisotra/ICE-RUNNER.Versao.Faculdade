package instances.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import loop.GCanvas;
import utilz.Screen;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Spritesheet;
import utilz.Universal;

public class Player1 extends Entities{
    
    //https://www.youtube.com/watch?v=rTVoyWu8r6g
    /*------------ ATRIBUTOS ------------*/
    private int playerCode;
    public Movement movement;
    public Collider collider;
    BufferedImage playerSpriteSheet;
    BufferedImage shadow;
    BufferedImage floormark;
    Spritesheet shadowsprite;
    Spritesheet floormarksprite;
    public int playerAction = Universal.IDLE;
    
    public Player1(Screen screen, GCanvas gc, int playerCode){
        super(screen, gc);
        this.playerCode = playerCode;
        try{
            setPlayerCode(playerCode);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        movement = new Movement(this);
        collider = new Collider(this);
        initSprite();
        setX(120);
        setY(360);
        movement.isJumping = true; //para ele cair logo de primeira
        setIsActive(true);
    }     
   
    public void initSprite(){
        SpriteData playerData = SpriteLoader.spriteDataLoader().get("player1");
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
        setSpritesheet(playerSpriteSheet, Universal.SCALE);
        shadowsprite = new Spritesheet(shadow, 32, 32, 0, Universal.SCALE);
        floormarksprite = new Spritesheet(floormark, 32, 32, 0, Universal.SCALE);
    }
    
    @Override
    public void update(float deltaTime){
        if(Universal.dash){
            movement.Dash();
        }
        movement.updatePosY(deltaTime);
        movement.updatePosX(deltaTime);
        collider.updateCollisionArea();
        
        if(collider.verifyNearby()){ //somente se HÁ um obstáculo dedd asdasdas das dantro da minha range de colisão 
            collider.verifyCollission();
        }
        updateHitbox();
    }

    @Override
    public void render(Graphics2D g2d){
        spritesheet.setAtion(playerAction); // altero ou mantenho a linha do spritesheet
        shadowsprite.render(g2d, (int) getX() - 21, (int) Universal.groundY - (Universal.TILES_SIZE / 6) + 40);
        spritesheet.render(g2d, (int) getX() - 12, (int) getY());
        
        if(Universal.showGrid){
            drawHitbox(g2d);
            collider.drawCollisionArea(g2d);
            floormarksprite.render(g2d, (int) getX() - 21, (int) Universal.groundY - (Universal.TILES_SIZE / 6) + 40);
        }
    }
    

    /*------------ GETTERS AND SETTERS ------------*/
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return heightO;
    }

    public int getPlayerCode(){
        return playerCode;
    }

    public void setPlayerCode(int playerCode) throws Exception{
        if(playerCode >= 1 && playerCode <= 2){
            this.playerCode = playerCode;
        } else {
            throw new Exception("Nao pode haver mais de 2 players");
        }
    }

    public Movement getMovement(){
        return movement;
    }
}
    
    



