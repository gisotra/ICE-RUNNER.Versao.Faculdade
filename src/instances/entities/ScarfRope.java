package instances.entities;

import java.awt.Graphics2D;

public class ScarfRope {
    //vai receber como parâmetro: o numero de segmentos a serem adicionados, o espaçamento de cada um
    //na hora de renderizar, considerar a ancora como o centro do frame 
    //vai possuir um arrayList de tamanho condizente com o passado no construtor
    private Player player; //a partir disso vai saber se é o P1 ou P2 atraves do playerIndex
    private ScarfSegment[] scarf;
    private float distanceX;
    private float distanceY;
    private float anchorX;
    private float anchorY;

    public ScarfRope(Player player, float distanceBetween) {
        this.player = player;
        this.scarf = new ScarfSegment[12];
        this.distanceX = distanceBetween;
        this.distanceY = distanceBetween;
        this.anchorX = player.getX() + 15;
        this.anchorY = player.getY() + 17;
        initScarf();
    }
    
    public void initScarf(){
        scarf[0] = new ScarfSegment(player.getX() + 7, player.getY() + 20, this.player);
        for (int i = 1; i < scarf.length; i++) {
            scarf[i] = new ScarfSegment(scarf[i - 1].getX(), scarf[i - 1].getY() + distanceY, this.player);
        }
    }
    
    public void update(float deltaTime) {
        // âncora segue o jogador
        scarf[0].setX(player.getX() + 7);
        scarf[0].setY(player.getY() + 20);

        // atualiza todos os outros segmentos
        //for (int i = 1; i < scarf.length; i++) {
        //    scarf[i].update(deltaTime);
        //}

        // aplica múltiplas iterações de constraint
        //int constraintIterations = 4;
        //for (int j = 0; j < constraintIterations; j++) {
            for (int i = 1; i < scarf.length; i++) {
                scarf[i].applyConstraint(scarf[i - 1], distanceX);
            }
        //}
    }
    
    public void render(Graphics2D g2d){
        for(int i = 0; i < 8; i++){
            scarf[i].render(g2d);
        }
    }
    
    
}
