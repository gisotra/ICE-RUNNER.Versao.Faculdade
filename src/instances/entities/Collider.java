package instances.entities;

import instances.Objects;
import instances.obstacles.Obstacles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import utilz.Screen;
import utilz.Universal;

public class Collider {
    Player player;
    protected Rectangle2D.Float collisionArea;
    public float collAreaWidth;
    public float collAreaHeight;
    public float areaXOffset;
    public float areaYOffset;
    
    public Collider(Player player1) {
        this.player = player1;
        this.collAreaWidth = player1.getHitboxWidth() * 1.2f;
        this.collAreaHeight = player1.getHitboxHeight() * 1.4f;
        this.areaXOffset = Universal.TILES_SIZE * 0.23f;
        this.areaYOffset = Universal.TILES_SIZE * 0.15f;
        collisionArea = new Rectangle2D.Float(player1.x, player1.y, collAreaWidth, collAreaHeight);
    }
    
    public void drawCollisionArea(Graphics g2d){
        /*------------ MÉTODOS ------------*/
        //para testar a hitbox
        g2d.setColor(Color.ORANGE);
        g2d.drawRect((int) collisionArea.x, (int) collisionArea.y, (int) collisionArea.width, (int) collisionArea.height);
    }
    
    public void initCollisionArea(float x, float y, float width, float height) {
        collisionArea = new Rectangle2D.Float(player.x - this.areaXOffset, player.y - this.areaYOffset, collAreaWidth, collAreaHeight);
    }
    
    public void updateCollisionArea() {
        collisionArea.x = player.x - this.areaXOffset; //atualizo a posição horizontal
        collisionArea.y = player.y - this.areaYOffset; //atualizo a posição vertical
    }
    
    public boolean verifyNearby(){
        /*
        Se algum obstáculo estiver dentro da minha área de colisão (que NÃO É 
        minha hitbox, é só um "campo de observação", eu passo a verificar a colisão
        com o obstáculo, poupando memória
        */
        for(Objects obj : Screen.objectsOnScreen){
            if(obj instanceof Obstacles){
                Obstacles obstacle = (Obstacles) obj;
                
                if(collisionArea.intersects(obstacle.obs_hitbox)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void verifyCollission(){
        for (Objects obj : Screen.objectsOnScreen) {
            if (obj instanceof Obstacles) {
                Obstacles obstacle = (Obstacles) obj;

                if (player.getHitbox().intersects(obstacle.obs_hitbox)) {
                    player.dead = true; //MORTE DO PLAYER1 r.i.p
                }
            }
        }
        
    }
    
    
}
