package instances.obstacles;

import instances.Objects;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import loop.GCanvas;
import utilz.Screen;
import utilz.Spritesheet;
import utilz.Universal;

public abstract class Obstacles extends Objects{ //muito similiar a classe Entities, porém direcionada unicamente aos obstáculos
    /*------------ ATRIBUTOS ------------*/
    public float speed; //ele vai sempre vir pra esquerda
    public Rectangle2D.Float obs_hitbox; //desenvolver na criação dos objetos wall, saw, bird, etc

    /*------------ CONSTRUTOR ------------*/
    public Obstacles(Screen screen, GCanvas gc) {
        super(screen, gc);
    }

    /*------------ MÉTODOS HERDADOS ------------*/
    protected abstract void drawObstHitbox(Graphics2D g2d); // método para debug
    {/*
    protected abstract void drawObstHitbox(Graphics g) {
        //para testar a hitbox
        g.setColor(Color.BLUE);
        g.drawRect((int) obs_hitbox.x, (int) obs_hitbox.y, (int) obs_hitbox.width, (int) obs_hitbox.height);
    }        
    
    */
    }
    
    /*transformar esse método em um abstrato, e modificar ele pra cada classe subsequente*/
    protected abstract void initObstHitbox();
    
    {/* versão desenvolvida do initObst
        public void initObstHitbox(float x, float y, float width, float height) {
        obs_hitbox = new Rectangle2D.Float(x, y, width, height);
    }
    */}
    
    public void updateObstHitbox(){ //pode tirar e definir no obstacles
        obs_hitbox.x = getX(); //atualizo a posição horizontal
    }
    
    public abstract void setSpritesheet(BufferedImage spritesheet, float renderScale);
    
    @Override
    public void update(float deltaTime) {
        if(this.isActive){ // se estiver ativo
        this.setX(this.getX() + Universal.BASE_SPEED * deltaTime); //atualizo a speed
        updateObstHitbox();
            //System.out.printf("X: %.2f, Speed: %.2f, dT: %.5f\n", getX(), Universal.OBST_SPEED, deltaTime);
        }
    }
    
    @Override
    public void render(Graphics2D g2d){
    spritesheet.render(g2d, (int) getX(), (int) getY()); 
        if(Universal.showGrid){
            drawObstHitbox(g2d);
        }
    }
    
    public Rectangle2D.Float getObstHitbox(){
        return obs_hitbox;
    }
}

