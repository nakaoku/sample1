package com.example.nn.game3;

/**
 * Created by nn on 2017/02/10.
 */
import android.graphics.Rect;
public class nnSpecAtari {
    nnObj own;
    Rect area_rel;
    int stat; //0:disable, 1:enable
    public nnSpecAtari(nnObj obj){
        own=obj;
        disable();
        //area_rel=new Rect( -obj.w/2,-obj.h/2, obj.w/2,obj.h/2);
        area_rel=new Rect(-20,-20,20,20);
    }
    public void enable(){stat=1;}
    public void disable(){stat=0;}

    public boolean isConflict(nnObj obj1,nnObj obj2){
        boolean ret=false;

        if(obj1.nnatari == null){return false;}
        if(obj2.nnatari == null){return false;}
        Rect tmp1 =obj1.nnatari.area_rel;
        Rect tmps1= new Rect(
                tmp1.left+obj1.getPos().x, tmp1.top+obj1.getPos().y,
                tmp1.right+obj1.getPos().x,tmp1.bottom+obj1.getPos().y);
        Rect tmp2 = obj2.nnatari.area_rel;
        Rect tmps2= new Rect(
                tmp2.left+obj2.getPos().x, tmp2.top+obj2.getPos().y,
                tmp2.right+obj2.getPos().x,tmp2.bottom+obj2.getPos().y);
        if( tmps1.intersects(tmps2.left,tmps2.top,tmps2.right,tmps2.bottom)){ret=true; }
        return ret;
    }
}
