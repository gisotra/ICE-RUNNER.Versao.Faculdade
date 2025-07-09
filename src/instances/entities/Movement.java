package instances.entities;

import utilz.Universal;

public class Movement {
    /*
    Classe utilizada unicamente para implementação dos movimentos verticais
    */
    
    Player1 player1;

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
    public float dashSpeed = 14f;
    public float dashDuration = 1f;
    public float dashLength = .3f;
    public float dashTimeCounter;
    public int horizontalDirection; //vai assumir 3 valores possíveis: -1, 1 ou 0 
    public int verticalDirection; //vai assumir 3 valores possíveis: -1, 1 ou 0 


    /*morte*/
    public boolean deathJump = false; //usei isso aqui pra animação de morte 
    public float deathJumpPower = -1.8f * Universal.SCALE;
    public int cont = 0;
    
    public Movement(Player1 player1){
        this.player1 = player1;
        heightGY = player1.getHitboxHeight();
        groundLvl = Universal.groundY - heightGY + 40; // 5 Tiles - 1 = 4 tiles
    }
    
    public void updatePosY(float deltaTime){ //ainda vou usar o deltaTime para movimentação horizontal depois
        
        if(!Universal.dead){
            deathJump = false;
            
            if(!isDashing){
                gravity = 0.08f * Universal.SCALE;
                
            if(Universal.jump && isGrounded()){
                player1.playerAction = Universal.JUMP;
                verticalSpeed = jumpPower;
                isJumping = true;
            }
        
            if(isJumping){ //caso eu esteja pulando, eu continuamente somo a gravidade na airSpeed
                verticalSpeed += gravity;
                player1.setY(player1.getY() + verticalSpeed); //altero o Y do player
                    if(verticalSpeed > 0){ //estou caindo
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
            } else {
                player1.setY((player1.getY() + verticalSpeed));
            }
        } else {
            //player morreu 
            //faço ele pular e corto o limitador vertical do chão
            //quando ele ultrapassar o tamanho da tela, eu setto o state como gameover 
            if(!deathJump){
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
    
    public void updatePosX(float deltaTime) {
        
        if(!isDashing){
        
        if (Universal.right && !Universal.dead) {

            horizontalSpeed = (float) speed * deltaTime;

        } else if (Universal.left && !Universal.dead) {

            horizontalSpeed = (float) -speed * deltaTime;

        } else if (Universal.dead){
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
        player1.setX( player1.getX() + horizontalSpeed);
        if(player1.getX() < 0){
            player1.setX(0);
        } else if (player1.getX() >= Universal.GAME_WIDTH - (Universal.TILES_SIZE)/2){
            player1.setX((Universal.GAME_WIDTH - (Universal.TILES_SIZE)/2));
        }
        } else { //estou dando dash
            player1.setX((player1.getX() + horizontalSpeed));
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
        if(Universal.up){
            return 1;
        } else if (Universal.down) {
            return 2;
        } else if (Universal.right){
            return 3;
        } else if (Universal.left){
            return 4;
        } else if (Universal.up && Universal.left){
            return 5;
        } else if (Universal.up && Universal.right){
            return 6;
        } else if (Universal.down && Universal. left){
            return 7;
        } else if (Universal.down && Universal.right){
            return 8;
        }
        //caso nenhuma direção esteja ativada
        return 0;
    }
    
    public void Dash(){
        float dashStartTime = System.currentTimeMillis();
        hasDashed = true;
        isDashing = true;
        isJumping = false;
        gravity = 0;
        int direction = getDirection();
        
        switch(direction){
            case 1:{ // UP
            horizontalDirection = 0;
            verticalDirection = 1;
            }break;
            case 2:{ //DOWN
            horizontalDirection = 0;
            verticalDirection = -1;                
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
            verticalDirection = 1;                    
            }break;
            case 6:{ //UPPER RIGHT
            horizontalDirection = 1;
            verticalDirection = 1;                    
            }break;
            case 7:{ //LOWER LEFT
            horizontalDirection = -1;
            verticalDirection = -1;                    
            }break;
            case 8:{ //LOWER RIGHT
            horizontalDirection = 1;
            verticalDirection = -1;                
            }break;
            case 0:{ //DEFAULT PARADO
            horizontalDirection = 1;
            verticalDirection = 0;
            break;
            }
        }
        
        while(System.currentTimeMillis() < dashStartTime + dashLength){
            horizontalSpeed = horizontalDirection * speed * dashSpeed;
            verticalSpeed = verticalDirection * speed * dashSpeed;
        }
        isDashing = false;
    }



}
