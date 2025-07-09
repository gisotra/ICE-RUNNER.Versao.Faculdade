package instances.entities;

import utilz.Universal;

public class Movement {
    
    /*Classe utilizada unicamente para implementação dos movimentos verticais*/
    
    Player player1;

    /*horizontal*/
    public float speed = 100f*Universal.SCALE;
    public float MAX_SPEED = 120f*Universal.SCALE;
    public float horizontalSpeed;
    public float atrito = 25.0f*Universal.SCALE;

    /*vertical*/
    public static boolean isJumping = false;
    public float verticalSpeed = 0f; //Y
    public float gravity = 0.08f * Universal.SCALE;
    public float jumpPower = -2.8f * Universal.SCALE; // Força do meu salto
    public boolean inAir = false;
    public float heightGY; //usado para achar a posição Y em que o player tá "no chão"
    public float groundLvl;

    /*dash*/
    public boolean isDashing = false;
    public boolean canDash = true; //caso dashTimeCounter > 0  && !hasDashed
    public boolean hasDashed = false;
    public float dashSpeed = 1.5f;
    public float dashDuration = .2f;
    public float dashLength = .001f;
    public float dashTimeCounter = 0f;
    public int horizontalDirection; //vai assumir 3 valores possíveis: -1, 1 ou 0 
    public int verticalDirection; //vai assumir 3 valores possíveis: -1, 1 ou 0 

    /*morte*/
    public boolean deathJump = false; //usei isso aqui pra animação de morte 
    public float deathJumpPower = -1.8f * Universal.SCALE;
    public int cont = 0;
    
    public Movement(Player player1){
        this.player1 = player1;
        heightGY = player1.getHitboxHeight();
        groundLvl = Universal.groundY - heightGY + 40; // 5 Tiles - 1 = 4 tiles
    }
    
    public void updateMovement(float deltaTime){
        if(!Universal.p1dead){
            deathJump = false;
            
            if(!isDashing){
                gravity = 0.08f * Universal.SCALE;
            
                
                // ================ movimentação VERTICAL ================
                if (Universal.p1jump && isGrounded()) {
                    player1.playerAction = Universal.JUMP;
                    verticalSpeed = jumpPower;
                    isJumping = true;
                }
                
                if (isJumping) { //caso eu esteja pulando, eu continuamente somo a gravidade na airSpeed
                    verticalSpeed += gravity;
                    player1.setY(player1.getY() + verticalSpeed); //altero o Y do player
                    if (verticalSpeed > 0) { //estou caindo
                        player1.playerAction = Universal.IS_FALLING;
                    }

                    //cheguei no chão, então preciso resetar o pulo
                    if (player1.getY() >= groundLvl) {
                        player1.setY(groundLvl);
                        verticalSpeed = 0f;
                        isJumping = false;
                        hasDashed = false;
                        canDash = true;
                        player1.playerAction = Universal.IDLE;
                    }
                    
                } else if (!isGrounded()){ //cai de uma plataforma ou qualquer evento alternativo
                    verticalSpeed += gravity;
                    player1.setY(player1.getY() + verticalSpeed);
                    player1.playerAction = Universal.IS_FALLING;
                }
                
                // ================ movimentação HORIZONTAL ================
                
                if (Universal.p1right && !Universal.p1dead) {

                    horizontalSpeed = (float) speed * deltaTime;

                } else if (Universal.p1left && !Universal.p1dead) {

                    horizontalSpeed = (float) -speed * deltaTime;

                } else if (Universal.p1dead) {
                    horizontalSpeed = 0;
                } else {

                    //não estou apertando tecla alguma E estou no chão 
                    if (horizontalSpeed > 0) { //freio ele na direita
                        horizontalSpeed -= atrito * deltaTime;
                        if (horizontalSpeed < 0) {
                            horizontalSpeed = 0; //paro
                        }
                    } else if (horizontalSpeed < 0) {
                        horizontalSpeed += atrito * deltaTime;
                        if (horizontalSpeed > 0) {
                            horizontalSpeed = 0; //paro
                        }
                    }
                }
            
                if (horizontalSpeed > MAX_SPEED) {
                    horizontalSpeed = MAX_SPEED;
                }
                if (horizontalSpeed < -MAX_SPEED) {
                    horizontalSpeed = -MAX_SPEED;
                }

                //aplico a mudança no player
                player1.setX(player1.getX() + horizontalSpeed);
                if (player1.getX() < 0) {
                    player1.setX(0);
                } else if (player1.getX() >= Universal.GAME_WIDTH - (Universal.TILES_SIZE) / 2) {
                    player1.setX((Universal.GAME_WIDTH - (Universal.TILES_SIZE) / 2));
                }
                
            } else { //DASH
                dashTimeCounter += deltaTime;
                gravity = 0;
                hasDashed = true;
                canDash = false;
                isDashing = true;
                float dashVelocity = 300 * Universal.SCALE;
                float dx = horizontalDirection * dashVelocity * deltaTime;
                float dy = verticalDirection * dashVelocity * deltaTime;

                player1.setX(player1.getX() + dx);
                player1.setY(player1.getY() + dy);
                
                if (dashTimeCounter >= dashDuration) {
                    //acaba meu dash
                    isDashing = false;
                    dashTimeCounter = 0;
                    horizontalSpeed = 0;
                    verticalSpeed = 0;
                }
                return;
            }
        } else {
            //player morreu 
            //faço ele pular e corto o limitador vertical do chão
            //quando ele ultrapassar o tamanho da tela, eu setto o state como gameover 
            if(!deathJump || (!deathJump && isDashing)){
                gravity = 0.08f * Universal.SCALE;
            verticalSpeed = deathJumpPower; //jogo pra cima 
            deathJump = true;
            isJumping = true; //true 
            player1.playerAction = Universal.IS_DEAD; //muda a animação
            }
            
            if (isJumping) {
                verticalSpeed += gravity;
                player1.setY(player1.getY() + verticalSpeed);
            }
        }
    }

    public boolean isGrounded() {
        if (player1.getY() >= groundLvl) {
            return true;
        } else {
            return false;
        }
    }

    
    public int getDirection(){
        if (Universal.p1up && Universal.p1left) {
            return 5;
        } else if (Universal.p1up && Universal.p1right) {
            return 6;
        } else if (Universal.p1down && Universal.p1left) {
            return 7;
        } else if (Universal.p1down && Universal.p1right) {
            return 8;
        } else if (Universal.p1up) {
            return 1;
        } else if (Universal.p1down) {
            return 2;
        } else if (Universal.p1right) {
            return 3;
        } else if (Universal.p1left) {
            return 4;
        }
        //caso nenhuma direção seja passada
        return 0;
    }
    
    public void Dash(){
        //usar para ativar o boolean do dash
        //usar para pegar as direções
        //mas onde chamar esse método? 
        //> updateMovement
        //> update dentro da classe player1 
        dashTimeCounter = 0f; // reset
        int direction = getDirection();
        isDashing = true;
        
        switch(direction){
            case 1:{ // UP
            horizontalDirection = 0;
            verticalDirection = -1;
            }break;
            case 2:{ //DOWN
            horizontalDirection = 0;
            verticalDirection = 1;                
            }break;
            case 3:{ //RIGHT
            horizontalDirection = 1;
            verticalDirection = 0;    
            }break;
            case 4:{ //LEFT
            horizontalDirection = -1;
            verticalDirection = 0;    
            }break;
            case 5:{ //UPPER LEFT
            horizontalDirection = -1;
            verticalDirection = -1;                    
            }break;
            case 6:{ //UPPER RIGHT
            horizontalDirection = 1;
            verticalDirection = -1;                    
            }break;
            case 7:{ //LOWER LEFT
            horizontalDirection = -1;
            verticalDirection = 1;                    
            }break;
            case 8:{ //LOWER RIGHT
            horizontalDirection = 1;
            verticalDirection = 1;                
            }break;
            case 0:{ //DEFAULT PARADO
            horizontalDirection = 1;
            verticalDirection = 0;
            break;
            }
        }
    }
}
