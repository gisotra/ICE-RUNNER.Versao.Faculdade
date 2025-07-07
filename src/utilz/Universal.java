package utilz;

public class Universal {
     
    /*
    largura: 8 * 32 = 256 | OU | 16 * 32 = 512
    altura: 7 * 32 = 224
    */
    public static int SCORE = 0; 
    public static long globalCooldown = 4000;
    
    /*configuração de fps*/
    public static final int FPS_SET = 60;
    
    /*Configurações de resolução da tela*/
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 3.0f;
    public final static int TILES_IN_WIDTH = 16;  //512px de COMPRIMENTO
    public final static int TILES_IN_HEIGHT = 9;  //288px ALTURA
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public final static int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;
    
    /*Opções para Debug*/
    public static boolean showGrid = false;
    
    /*
    Lógica utilizada para determinar a altura de spawn dos elementos:
    AlturaSpawnY = ValorYDoChão - AlturaDaHitboxDoElemento;
    */
    public static final float groundY = GAME_HEIGHT - (2 * TILES_SIZE); //usado para achar a posição Y em que o player tá "no chão"
    
    /*---------------- PLAYER1 ----------------*/
    /*-----------------------------------------*/
    /*-----------------------------------------*/
    /*Índices das animações dos sprites do player (vertical)*/
    public static final int IDLE = 0;
    public static final int RUNNING = 0;
    public static final int JUMP = 1;
    public static final int LANDING = 2;
    public static final int IS_FALLING = 2;
    public static final int IS_DEAD = 3;
    
    /*flags de direção para usar no player e na classe KeyInputs*/
    public static boolean right = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean jump = false;
    public static boolean dead = false;

    /*Direções de movimento para o SPRITE do player*/
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    
    /*-------------- OBSTÁCULOS ---------------*/
    /*-----------------------------------------*/
    /*-----------------------------------------*/
    
    /*Posição de spawn dos obstáculos do player2 + flags de spawn no KeyInputs*/
    // =============== Wall =============== 
    public static float OBST_SPAWN_X = GAME_WIDTH + TILES_SIZE; 
    public static final int WALL_WIDTH = 70;
    public static final int WALL_HEIGHT = 120;
    public static boolean wall = false; //flag de spawn
    public static final float WALL_HITBOX_WIDTH = 0.7f * TILES_SIZE;
    public static final float WALL_HITBOX_HEIGHT = 0.67f * Universal.TILES_SIZE;
    public static final int WALL_SPAWN_Y = GAME_HEIGHT - (2 * TILES_SIZE + (int)WALL_HITBOX_HEIGHT) + 40;

    // =============== Bird =============== 
    public static final int BIRD_WIDTH = 120;
    public static final int BIRD_HEIGHT = 40;
    public static boolean bird = false; //flag de spawn 
    public static final float BIRD_HITBOX_WIDTH = 0.7f * TILES_SIZE;
    public static final float BIRD_HITBOX_HEIGHT = 0.3f * Universal.TILES_SIZE;
    public static final int BIRD_SPAWN_Y = GAME_HEIGHT - (3 * TILES_SIZE + (int)BIRD_HITBOX_HEIGHT) + 40;
    
    // =============== Saw =============== 
    public static final int SAW_WIDTH = 120;
    public static final int SAW_HEIGHT = 40;
    public static final float SAW_HITBOX_WIDTH = 0.8f*TILES_SIZE;
    public static final float SAW_HITBOX_HEIGHT = 0.65f*Universal.TILES_SIZE;
    public static final int SAW_SPAWN_Y = GAME_HEIGHT - (2 * TILES_SIZE + (int)SAW_HITBOX_HEIGHT) + 40;    
    public static boolean saw = false; //flag de spawn
    
    // =============== Fall Block ===============
    public static final float BLOCK_HITBOX_WIDTH = 1.05f*TILES_SIZE;
    public static final float BLOCK_HITBOX_HEIGHT = 1.3f*TILES_SIZE;
    public static final int BLOCK_SPAWN_Y = GAME_HEIGHT - (2 * TILES_SIZE + (int) BLOCK_HITBOX_HEIGHT) + 40;
    public static final int BLOCK_SKY_LEVEL = 2 * Universal.TILES_SIZE - 64;
    public static boolean block = false; //flag de spawn 
    
    // =============== Geral =============== 
    public static float BASE_SPEED = 0;
    public static int lastSpeedUpScore = 0;
    public static int speedUpgrades = 0;
    public static final int MAX_SPEED_UPGRADES = 7;
    
    /*Método que retorna quantos frames cada ação possui*/
    public static int GetSpriteAmount(int player_action) {
        switch (player_action) {

            case RUNNING:
                return 2;
            case IS_FALLING:
                return 1;
            case IS_DEAD:
                return 1;
            case JUMP:
                return 3;
            default:
                return 1;
        }
    }
    
    /*-------------- GAME LOOP ---------------*/
    /*-----------------------------------------*/
    /*-----------------------------------------*/
    
    public static void resetGameValues(){
        BASE_SPEED = -100f * SCALE;
        globalCooldown = 2000;
        SCORE = 0;
        dead = false;
        lastSpeedUpScore = 0;
        speedUpgrades = 0;
    }
    
    public static void increaseAllSpeed(){
        if (speedUpgrades < MAX_SPEED_UPGRADES) {
            BASE_SPEED -= 10f;
            speedUpgrades++;
        }
    }
    
        
    /*-------------- SPRITES CENÁRIO ----------*/
    /*-----------------------------------------*/
    /*-----------------------------------------*/
    
    public final static int spriteEnviroWidth = 1024;
    public final static int spriteEnviroWidthSLICED = 128;
    public final static int spriteEnviroWidthSLICEDSCALED = Universal.spriteEnviroWidthSLICED * (int)Universal.SCALE;
    public final static int numHorizontalTiles = 8;
    
    public final static float layer1YOffset = Universal.GAME_HEIGHT - 98 * Universal.SCALE;
    public final static float layer2YOffset = Universal.GAME_HEIGHT - 130 * Universal.SCALE;
    public final static float layer3YOffset = Universal.GAME_HEIGHT - 125 * Universal.SCALE;
    //a altura de cada um vai ser variável -> evitar colocar a mesma altura que a tela, pesa em processamento
    
}
