package instances.manager;

import java.util.Random;
import utilz.Universal;

public class Spawner{
    /*
    O que vai ser o player 2:
    Ele vai ter cooldowns próprios para cada obstáculo.
    */
    private SpawnManager spm = new SpawnManager();
    private Random r = new Random();
    private int spawnpoint;
    
    // Cooldown global entre spawns (anti-spam)
    private long lastGlobalSpawn = 0;
    
    
    public void play(){ //(currentTime - lastSpawn) >= SpawnWall )
        long currentTime = System.currentTimeMillis();
        
        if(currentTime - lastGlobalSpawn < Universal.globalCooldown){ //evita spam 
            return;
        }
        spawnpoint = r.nextInt(9);
        switch(spawnpoint){
            case 1:
                Universal.wall = true;
                break;
            case 2:
                Universal.saw = true;
                break;
            case 3:
                Universal.bird = true;
                break;
            case 4:
                Universal.block = true;
                break;
            default:
                break;
        }
        
        if(Universal.wall){
            spm.spawnWall();
            
            lastGlobalSpawn = currentTime;
            Universal.wall = false;
            return;
        }
        
        if(Universal.saw){
            spm.spawnSaw();
            
            lastGlobalSpawn = currentTime;
            Universal.saw = false;
            return;
        }
        if(Universal.bird){
            spm.spawnBird();
            
            lastGlobalSpawn = currentTime;
            Universal.bird = false;
            return;
        }
        
        if(Universal.block){
            spm.spawnBlock();
            
            lastGlobalSpawn = currentTime;
            Universal.block = false;
            return;
        }
        
    }
    
    

}
