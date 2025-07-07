package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private ServerSocket serverSocket;
    
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    
    /*public void startServer(){
        
        try {
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("Um cliente se conectou");
                ClientHandler clientHandler = new ClientHandler(socket);
                
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e){
            
        }
    }
    
    public void closeServerSocket() {
        try{
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void createServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
    */
    /*
    como vai funcionar o online do jogo endless runner (multiplayer para quem sobrevive mais tempo):
    > player entra no jogo (cutscene)
    > menu
    > player clica na opção multiplayer
    > player tem 2 opções [criar servidor] [entrar em servidor]
    > na primeira opção, o jogador clica e instantaneamente entra em uma sala de espera, que permite começar o jogo somente quando o outro jogador entrar na sala
    > na segunda opção, vai aparecer uma text area na tela, e o player que está entrando no servidor tem que digitar o ip correspondente, esse valor sera lido e usado para fazer a conexao no servidor
    do outro cara
    > depois da conexão, o loop se baseia em quem sobrevive mais tempo no jogo, quando um player morre, o jogo acaba, e este sai como perdedro
    --> como eu pretendia realizar tudo isso
    criar 2 threads pras conexões, uma para receber inputs e uma para ler inputs, além disso uma outra thread para o servidor em si
     */
}