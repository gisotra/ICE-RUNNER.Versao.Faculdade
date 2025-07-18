package utilz;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Universal {
     
    /*
    largura: 8 * 32 = 256 | OU | 16 * 32 = 512
    altura: 7 * 32 = 224
    */
    public static int SCORE = 0; 
    public static long globalCooldown = 4000;
    public static boolean bothPlayingLocal = false;
    public static boolean youAreAHost = false;
    public static boolean youAreAClient = false;
    
    /*configuração de fps*/
    public static final int FPS_SET = 60;
    
    /*Configurações de resolução da tela*/
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2.0f;
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
    public static int obstSpawnIndex = 0;
    
    /*-------------- GAME LOOP ---------------*/
    /*-----------------------------------------*/
    /*-----------------------------------------*/
    
    public static void resetGameValues(){
        BASE_SPEED = -100f * SCALE;
        globalCooldown = 2000;
        SCORE = 0;
        lastSpeedUpScore = 0;
        speedUpgrades = 0;
    }
    
    public static void increaseAllSpeed(){
        if (speedUpgrades < MAX_SPEED_UPGRADES) {
            BASE_SPEED -= 10f;
            speedUpgrades++;
        }
    }
    
    public static void resetBooleans(){
        Universal.bothPlayingLocal = false;
        Universal.youAreAHost = false;
        Universal.youAreAClient = false;
    }
    
    /*Sockets Classe universal*/   
    public static InetAddress ip;
    public static String IPString = initIP();
    
    public static String initIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // ignora interfaces que estão desligadas ou loopback
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress(); // Aqui está o IP local correto
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "IP não encontrado";
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
