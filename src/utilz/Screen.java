package utilz;

import background.Environment;
import background.Layer1;
import background.Layer2;
import background.Layer3;
import effects.Emitter;
import gamestates.Gamestate;
import static gamestates.Gamestate.*;
import instances.Objects;
import instances.entities.Entities;
import instances.entities.Player;
import instances.manager.Spawner;
import instances.obstacles.Bird;
import instances.obstacles.FallBlock;
import instances.obstacles.Obstacles;
import instances.obstacles.Saw;
import instances.obstacles.Wall;
import java.awt.Graphics2D;
import java.util.ArrayList;
import loop.GCanvas;
import java.util.List;

import ui.GameOver;
import ui.Menu;
import ui.MultiplayerMenu;
import ui.Playing;

public class Screen { 
    /*
    Essa vai ser a classe em que eu vou manipular e desenhar todos os elementos componentes da 
    minha tela de 1 vez dentro de 1 único método, isso será feito a partir da implementação de 
    um LinkedList do tipo da superclasse abstrata "Objects", e através do polimorfismo, 
    vou chamar o método update e o render de cada componente desse list.
    
    Porque uma linkedList e não um ArrayList normal? Porque eu vou reciclar os obstáculos gerados, deixando 
    o jogo com um desempenho mais leve e amigável com a CPU.
    */
    /*------------ ATRIBUTOS ------------*/
    public GCanvas gc;
    public static List<Objects> objectsOnScreen = new ArrayList<>(); //vou usar pra dar update e render no player e nos obstaculos simultaneamente (mto amigavel com a cpu)
    
    Player player1;
    Player player2;
    Spawner spawner;
    /*--- camadas do cenário ---*/
    Layer1 layer1;
    Layer2 layer2;
    Layer3 layer3;
    Emitter snowEmitter;
    /*--- game states ---*/
    Menu menuscreen;
    GameOver gameoverscreen;
    MultiplayerMenu multmenuscreen;
    Playing playingscreen;
    //para debug
    
    /*------------ CONSTRUTOR ------------*/
    public Screen(GCanvas gc){
        this.gc = gc;
        menuscreen = new Menu();
        gameoverscreen = new GameOver();
        multmenuscreen = new MultiplayerMenu();
        playingscreen = new Playing();
        snowEmitter = new Emitter(60);    
        
        layer3 = new Layer3(this, this.gc);
        objectsOnScreen.add(layer3);
        layer2 = new Layer2(this, this.gc);
        objectsOnScreen.add(layer2);
        layer1 = new Layer1(this, this.gc);
        objectsOnScreen.add(layer1);
        spawner = new Spawner();
        for(int i = 0; i < 4; i++){ //3 por obstáculo, 9 no total. 
            objectsOnScreen.add(new Bird(this, this.gc));
            objectsOnScreen.add(new Wall(this, this.gc));
            objectsOnScreen.add(new Saw(this, this.gc));
            objectsOnScreen.add(new FallBlock(this, this.gc));
        }
        player1 = new Player(this, this.gc, 1);
        player2 = new Player(this, this.gc, 2);
        objectsOnScreen.add(player1);
        objectsOnScreen.add(player2);
    }
    
    /*------------ MÉTODO RENDER ------------*/
    public void renderAll(Graphics2D g2d) {
        switch (Gamestate.state) {
            case MENU: {
                menuscreen.render(g2d);
                break;
            }
            case PLAYING:{
                playingscreen.render(g2d);
                for (Objects obj : objectsOnScreen) {
                        if ((!(obj instanceof Environment) && obj.getX() >= -Universal.TILES_SIZE * 4 && obj.getIsActive() && obj.getX() < Universal.GAME_WIDTH + Universal.TILES_SIZE)) {
                            obj.render(g2d);
                        }
                        if (obj instanceof Environment) { 
                        obj.render(g2d);
                    }
                }
                snowEmitter.render(g2d);
                break;
            }
            case MULTIPLAYER_MENU: {
                multmenuscreen.render(g2d);
                break;
            }
            case PLAYING_ONLINE:{
                break;
            }
            case ABOUT:{
                break;
            }
            case GAME_OVER:{
                playingscreen.render(g2d);
                for (Objects obj : objectsOnScreen) {
                    if (!(obj instanceof Environment) && (obj.getX() >= -Universal.TILES_SIZE * 4 && !obj.getIsActive() && obj.getX() < Universal.GAME_WIDTH + Universal.TILES_SIZE)) {
                        obj.render(g2d);
                    }
                    if (obj instanceof Environment) {
                        obj.render(g2d);
                    }
                    
                }
                gameoverscreen.render(g2d);
                break;
            }
            case END:{
                System.exit(0);
            }
        }
    }
    
    /*------------ MÉTODO UPDATE ------------*/
    public void updateAll(float variacaoTempo) {
        switch(Gamestate.state){
            case MENU:{
            break;
            }
            case PLAYING:{
                for(Objects obj : objectsOnScreen){
                    if(!obj.getIsActive()){
                        continue; //se estiver desativado, nada acontece, nao é atualizado
                    }
                    if (obj instanceof Environment) {
                        obj.update(variacaoTempo);
                    }
                    if (!(obj instanceof Environment) &&obj.getX() < -Universal.TILES_SIZE * 4) {
                        obj.setIsActive(false);
                        continue;
                    }
                    
                    obj.update(variacaoTempo);
                }
                spawner.play();
                snowEmitter.update(variacaoTempo);
                if(player1.dead || player2.dead){
                    
                    for (Objects obj : objectsOnScreen){
                        if(obj instanceof Entities && obj.getY() > Universal.GAME_HEIGHT){
                            Gamestate.state = GAME_OVER;
                        }
                    }
                    break;
                }
            }break;
            case LOCAL_MULTIPLAYER:{
                for(Objects obj : objectsOnScreen){
                    if(!obj.getIsActive()){
                        continue; //se estiver desativado, nada acontece, nao é atualizado
                    }
                    if (obj instanceof Environment) {
                        obj.update(variacaoTempo);
                    }
                    if (!(obj instanceof Environment) &&obj.getX() < -Universal.TILES_SIZE * 4) {
                        obj.setIsActive(false);
                        continue;
                    }
                    obj.update(variacaoTempo);
                }
                spawner.play();
                if(player1.dead){
                    
                    for (Objects obj : objectsOnScreen){
                        if(obj instanceof Entities && obj.getY() > Universal.GAME_HEIGHT){
                            Gamestate.state = GAME_OVER;
                        }
                    }
                    break;
                }
            }break;
            case GAME_OVER:{
                for (Objects obj : objectsOnScreen) {
                        obj.setIsActive(false);
                }
                break;
            }
            default:{
                break;
            }
            
        }
        
    }   
    
    /*------------ MÉTODO QUE RESETA AS COORDENADAS DAS INSTANCIAS NA TELA ------------*/
    public static void startCoordenates(){
        for (Objects obj : objectsOnScreen) {
            if(!Universal.bothPlaying){ //Jogador sozinho
                if (obj instanceof Entities) {
                    if(((Player)obj).getPlayerIndex() == 2){ //player 2 inativado
                        obj.setIsActive(false);
                    } else { //player 1 continua ativo
                        obj.setIsActive(true);
                        obj.setX(120 * ((Player) obj).getPlayerIndex());
                        obj.setY(360);
                        ((Player) obj).movement.isJumping = true;
                        ((Player) obj).dead = false;
                    }
                } 
            } else { //Jogando com um amigo localmente
                if (obj instanceof Entities) {
                    obj.setIsActive(true);
                    obj.setX(120 * ((Player)obj).getPlayerIndex());
                    obj.setY(360);
                    ((Player)obj).movement.isJumping = true;
                    ((Player)obj).dead = false;
                }
            }
            if(obj instanceof Environment){
                obj.setIsActive(true);
            }
            
            if(obj instanceof Obstacles){
                ((Obstacles)obj).updateObstHitbox();
            }
        }
    }
    
    public static void resetCoordenates(){
        for (Objects obj : objectsOnScreen) {
                if (obj instanceof Environment) {
                    continue;
                }
                obj.setIsActive(false);
                if(obj instanceof Obstacles){
                    obj.setX(Universal.OBST_SPAWN_X);
                }
        }
        Universal.resetGameValues();
    }
    
    /*------------- GETTERS E SETTERS QUE FORAM NECESSARIOS NA JORNADA -------------*/
    public Menu getMenu(){
        return menuscreen;
    }
    
    public GameOver getGameOver(){
        return gameoverscreen;
    }

    public MultiplayerMenu getMultMenu(){
        return multmenuscreen;
    }
    
}