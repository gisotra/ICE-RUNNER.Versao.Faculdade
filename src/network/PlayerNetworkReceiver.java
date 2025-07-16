package network;

import instances.Objects;
import instances.entities.Player;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import utilz.Screen;

public class PlayerNetworkReceiver implements Runnable{
    
    private Socket socket;
    private boolean running = false;
    private int dummyIndex; // 1 ou 2 
     
    public PlayerNetworkReceiver(Socket socket, int dummyIndex){
        this.socket = socket;
        this.dummyIndex = dummyIndex;
    }
    
    public void stop(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Player dummy = getDummyFromScreen();
            
            if(dummy == null){
                System.out.println("Erro: O dummy eh nulo");
                return;
            }
            
            while(running){
                float x = dis.readFloat();
                float y = dis.readFloat();
                byte animIndex = dis.readByte();
                
                dummy.setX(x);
                dummy.setY(y);
                dummy.playerAction = Player.PlayerAnimation.values()[animIndex];
                
            }
        } catch(IOException e){
            if(running){
                System.err.println("Erro na recepção de dados:");
                e.printStackTrace();
            }
        }
    }
    
    private Player getDummyFromScreen(){
        for(Objects obj : Screen.objectsOnScreen){
            if(obj instanceof Player){
                Player p = (Player)obj;
                if(p.isDummy() && p.getPlayerIndex() == dummyIndex){
                    return p;
                }
            }
        }
        return null;
    }

}
