package instances.entities;

import utilz.AnimationType;

public enum PlayerAnimation implements AnimationType{
    IDLE(0, 1),
    RUNNING(0, 1),
    JUMP(1, 3),
    FALLING(2, 1),
    DEAD(3, 1);
    //DASH(4, 2);

    private final int index;
    private final int frameCount;
    
    PlayerAnimation(int index, int frameCount){
        this.index = index;
        this.frameCount = frameCount;
    }

    @Override
    public int getIndex(){
        return index;
    }
    
    @Override
    public int getFrameCount(){
        return frameCount;
    }
}
