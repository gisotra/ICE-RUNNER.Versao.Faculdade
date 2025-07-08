package loop;

import gamestates.Gamestate;
import static gamestates.Gamestate.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import utilz.Screen;
import static utilz.Screen.resetCoordenates;
import utilz.Universal;

public class KeyInputs implements KeyListener {

    private GCanvas gameCanvas;

    public KeyInputs(GCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ignorar por enquanto
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            /*player1 - Movimentação*/
            case KeyEvent.VK_W:
                Universal.up = false;
                break;
            case KeyEvent.VK_A:
                Universal.left = false;
                break;
            case KeyEvent.VK_S:
                Universal.down = false;
                break; 
            case KeyEvent.VK_D:
                Universal.right = false;
                break;
            case KeyEvent.VK_SPACE:
                Universal.jump = false;
                break;
            case KeyEvent.VK_SHIFT:
                Universal.dash = false;
                break;
            case KeyEvent.VK_P:
                break;    
                /*DEBUG*/
            case KeyEvent.VK_U:
                //Toggle Grid 
                Universal.showGrid = !Universal.showGrid;
                break;    
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            /*player1 - Movimentação*/
            case KeyEvent.VK_W:
                Universal.up = true;
                break;
            case KeyEvent.VK_A:
                Universal.left = true;
                break;
            case KeyEvent.VK_S:
                Universal.down = true;
                break;
            case KeyEvent.VK_D:
                Universal.right = true;
                break;
            case KeyEvent.VK_SPACE:
                Universal.jump = true;
                break;
            case KeyEvent.VK_SHIFT:
                Universal.dash = true;
                break;
            case KeyEvent.VK_P:
                if (Gamestate.state != MENU) {
                    Gamestate.state = MENU;
                } else {
                    Gamestate.state = PLAYING_OFFLINE;
                }
                break;
            case KeyEvent.VK_I:
                //meu player morreu, e eu apertei i (provisorio)
                if (Gamestate.state == GAME_OVER) {
                    Gamestate.state = PLAYING_OFFLINE;
                    Screen.resetCoordenates();
                    Screen.startCoordenates();
                }
                
                break;
            /*DEBUG*/    
            case KeyEvent.VK_U:
                //Toggle Grid 
                break;   
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
             
                
        }
    }
}
