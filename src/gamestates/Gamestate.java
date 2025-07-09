package gamestates;

public enum Gamestate {
    /*
    Classe especial: Enumerador 
    Aqui vai ter várias constantes 
    Os elementos de uma enumeração por padrão são considerados Static e Final
    
    Se eu tentar instanciar usando new algum objeto do tipo enumerador vai dar 
    erro de compilação
    */
    MENU, 
    TUTORIAL, 
    ABOUT, 
    PLAYING_OFFLINE, 
    GAME_OVER, 
    MULTIPLAYER_MENU, 
    SERVER_HOSTING, //botão "criar servidor"
    CLIENT_CONNECTING, //botão "jogar como cliente"
    PLAYING_ONLINE,
    LOCAL_MULTIPLAYER,
    END;
    
    public static Gamestate state = MENU;
}
