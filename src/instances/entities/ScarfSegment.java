package instances.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import utilz.SpriteData;
import utilz.SpriteLoader;
import utilz.Universal;

public class ScarfSegment {
     //vai ter X, Y, as sprites, e os valores prévios de X e Y
    private Player player;
    private BufferedImage scarfSegSprite;
    private BufferedImage scarfSegSpriteSCALED;
    private int largura = 7;
    private int altura = 7;
    private float x;
    private float y;
    private float lastXPosition;
    private float lastYPosition;
    private float gravity = 1.4f;
    private float wind = 0.8f;
    

    public ScarfSegment(float x, float y, Player player) {
        this.x = x;
        this.y = y;
        lastXPosition = x;
        lastYPosition = y;
        
        this.player = player;
        initSprite();
    }
    
    public void initSprite() {
        SpriteData scarfData = null;
        if(player.getPlayerIndex() == 1){
            scarfData = SpriteLoader.spriteDataLoader().get("scarf1");
        } else if (player.getPlayerIndex() == 2){
            scarfData = SpriteLoader.spriteDataLoader().get("scarf2");
        }
        
        try {
            scarfSegSprite = ImageIO.read(getClass().getResource(scarfData.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int larguraEscalonada = largura * (int)Universal.SCALE;
        int alturaEscalonada = altura * (int)Universal.SCALE;

        scarfSegSpriteSCALED = new BufferedImage(larguraEscalonada, alturaEscalonada, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scarfSegSpriteSCALED.createGraphics();
        g2d.drawImage(this.scarfSegSprite, 0, 0, larguraEscalonada, alturaEscalonada, null);
        g2d.dispose();
    }
    
    public void render(Graphics2D g2d){
        int drawX = (int) x; //converter para int 
        int drawY = (int) y;
        //implementar centerX :/
        int centerX = drawX + scarfSegSpriteSCALED.getWidth() / 2; //aplico que a ancora será no centro da minha sprite
        int centerY = drawY + scarfSegSpriteSCALED.getHeight() / 2;
        g2d.drawImage(scarfSegSpriteSCALED, centerX-scarfSegSpriteSCALED.getWidth() / 2, centerY-scarfSegSpriteSCALED.getHeight() / 2, null);
    }
    
    public void update(float deltaTime) {
        float currentX = x;
        float currentY = y;

        // calcula a velocidade
        float velocityX = (x - lastXPosition) * 0;
        float velocityY = (y - lastYPosition) * 0; 

        // aplica forças externas na "velocidade"
        velocityY += gravity * deltaTime;
        velocityX += wind * deltaTime;

        // atualiza posição com base na nova velocidade
        x += velocityX;
        y += velocityY;

        // armazena a posição anterior
        lastXPosition = currentX;
        lastYPosition = currentY;
    }
    
    public void applyConstraint(ScarfSegment segmentoAncora, float distanciaIDEALEntreSegmentos){ //limita os meus segmentos de forma que eles pareçam conectados entre si
        float deltaX = x - segmentoAncora.getX(); 
        float deltaY = y - segmentoAncora.getY();
        
        //aplico teorema de pitágoras para pegar a distância entre esses dois pontos
        // D = √(x² + y²)
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        
        float diferenca = distanciaIDEALEntreSegmentos - dist;
        
        float percent = diferenca / dist / 2;
        //calcula o tanto que a gente precisa mudar a posição dos dois pontos para se aproximar da distancia ideal
        //se estiver muito perto, a gente vai afastar metade da distancia cada um 
        //se estiver muito longe, a gente vai juntar e por aí vai
        
        float OffSetX = deltaX * percent;
        float OffSetY = deltaY * percent;
        /*
        Divide o erro de distância pela distância total,
        e depois por 2, para distribuir o ajuste igualmente entre os dois segmentos (meio pra cada lado).
        */
        
        setX(getX() + OffSetX);
        setY(getY() + OffSetY);
        segmentoAncora.setX(getX() - OffSetX);
        segmentoAncora.setY(getY() - OffSetY);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getLastXPosition() {
        return lastXPosition;
    }

    public void setLastXPosition(int lastXPosition) {
        this.lastXPosition = lastXPosition;
    }

    public float getLastYPosition() {
        return lastYPosition;
    }

    public void setLastYPosition(int lastYPosition) {
        this.lastYPosition = lastYPosition;
    }
    
    
    
    
    
    
}
