package myGameUtil;

import com.netgroup_jv.gamecalc.MainActivity;

/**
 * Created by kikuragetyann on 16/03/16.
 */
public interface MoveObject {
    public abstract void move(float dpX,float dpY);
    public abstract void moveX();
    public abstract void moveMX();
    public abstract void moveX(float dpX);
    public abstract void moveY();
    public abstract void moveMY();
    public abstract void moveY(float dpY);
}
