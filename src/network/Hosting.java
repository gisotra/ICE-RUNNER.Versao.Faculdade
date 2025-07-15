package network;

import gamestates.Gamestate;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

import ui.Buttons;
import ui.MultiplayerMenu;
import ui.ScreenStates;
import utilz.AnimationType;
import utilz.Screen;
import utilz.Sprite;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Universal;

public class Hosting implements ScreenStates {

    /*Parte gráfica*/
    private BufferedImage multMenuFundo;
    private Sprite<MultiplayerMenu.MultiplayerMenuAnimation> multMenuSprite;
    //adicionar o sprite pro loading 
    private Buttons[] botoesMenu = new Buttons[2];
    private BufferedImage botaoStartServer;
    private BufferedImage botaoExit;
    private boolean waitingConnection = false;

    /*Sockets*/
    private int porta = 1000;
    private ServerSocket gameserver;

    public Hosting(){
        /*conceito:
        quando o host iniciar o server e entrar na tela de espera, eu vou printar o ip dele na tela, e ele fala pro outro cara no outro pc
        serverSocket(porta)
        Socket(ip, porta)
        */
        initSpriteMenu();
        botoesMenu[0] = new Buttons(20, 20, 48, 48, botaoExit, Gamestate.MULTIPLAYER_MENU); 
        botoesMenu[1] = new Buttons(7*Universal.TILES_SIZE + (Universal.TILES_SIZE/4)  , 3*Universal.TILES_SIZE + (Universal.TILES_SIZE/4), 48, 48, botaoStartServer, null);
    }
    
    public void initGameServer(){
        if (gameserver != null && !gameserver.isClosed()) {
            return;
        }
        Thread server = new Thread(() -> {
            try{    
                gameserver = new ServerSocket(porta);
                this.waitingConnection = true;
                Socket clientSocket = gameserver.accept();    
                Universal.youAreAHost = true;
                Gamestate.state = Gamestate.PLAYING;
            } catch (IOException ex){
                ex.printStackTrace();
            }
            
        });
        server.start();
    }

    public void initSpriteMenu() {
        SpriteData menuData = SpriteLoader.spriteDataLoader().get("fundoMenu");
        SpriteData serverData = SpriteLoader.spriteDataLoader().get("hosting");
        SpriteData exitData = SpriteLoader.spriteDataLoader().get("exitbutton");

        try {
            
            multMenuFundo = ImageIO.read(getClass().getResource(menuData.getPath()));
            botaoStartServer = ImageIO.read(getClass().getResource(serverData.getPath()));
            botaoExit = ImageIO.read(getClass().getResource(exitData.getPath()));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //inicio as propriedades do meu sprite player
        this.multMenuSprite = new Sprite<>(multMenuFundo, 288, 512, MultiplayerMenu.MultiplayerMenuAnimation.class, 1);
    }

    /*-------------- MÉTODOS HERDADOS --------------*/
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2D){
        multMenuSprite.render(g2D, 0, 0);
        for (Buttons but : botoesMenu) {
            but.render(g2D);
        }
        if(waitingConnection){
                    g2D.setColor(Color.WHITE);
                    g2D.drawString("ESPERANDO", Universal.GAME_WIDTH / 2 - 110, 700);
                    g2D.drawString("CONEXAO", Universal.GAME_WIDTH / 2 - 20, 700);
                    
        }
    }

    @Override
    public boolean isIn(MouseEvent e, Buttons mb) {
        return mb.getDimensoes().contains(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                but.setCursorPressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                if (but.isCursorPressed()) {
                    if (but.getState() == Gamestate.PLAYING) {
                        Universal.bothPlayingLocal = true;
                        but.applyGamestate();
                        Screen.resetCoordenates();
                        Screen.startCoordenates();
                    } if (but.getState() == null){
                        initGameServer();
                        continue;
                    } else {
                        Universal.bothPlayingLocal = true;
                        but.applyGamestate();
                    }
                }
            }
        }

        resetButtons();
    }

    private void resetButtons() {
        for (Buttons but : botoesMenu) {
            but.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (Buttons but : botoesMenu) {
            but.setCursorOver(false);
        }

        for (Buttons but : botoesMenu) {
            if (isIn(e, but)) {
                but.setCursorOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
    /*========== Classe interna Para o Sprite ==========*/
    public enum HostingAnimation implements AnimationType {
        STATIC;

        @Override
        public int getIndex() {
            return 0;
        }

        @Override
        public int getFrameCount() {
            return 1;
        }
    }

    public boolean isWaitingConnection() {
        return waitingConnection;
    }

    public void setWaitingConnection(boolean waitingConnection) {
        this.waitingConnection = waitingConnection;
    }

    
    
}
