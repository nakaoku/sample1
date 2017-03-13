package com.example.nn.game3;

/**
 * Created by nn on 2017/02/10.
 */
import android.graphics.Point;
import android.graphics.Rect;
import java.util.Random;

public class nnSpecMov {
    nnObj own;
    Point pos_prev;
    Random rnd;

    int d;
    int dx;
    int dy;
    int cnt;

    public nnSpecMov(nnObj obj){
        own=obj;
        rnd=new Random();
        d=30;
        setmov(obj.type);
        cnt=0;
    }

    public void mov(){
        pre_mov();
        pos_prev = own.getPos();
        own.setPos( mov_core());
        return;
    }
    public void BrakeAreaAction(){
        if( own.type == nnTypeObj.enemycore){
            if( own.getPos().x > own.nnlayer.area.x-100  ){dx=-dx; }
            if( own.getPos().x < 100 ){dx=-dx;  }
            if( own.getPos().y > own.nnlayer.area.y-200  ){dy=-dy; }
            if( own.getPos().y < 200 ){dy=-dy;  }
        }else if( own.type == nnTypeObj.enemy){
            if( own.getPos().x > own.nnlayer.area.x-100  ){own.mustremove=true; }
            if( own.getPos().x < 100 ){own.mustremove=true;  }
            if( own.getPos().y > own.nnlayer.area.y-200  ){own.mustremove=true; }
            if( own.getPos().y < 200 ){own.mustremove=true; }
        }else{
            DoCancelmov();
        }
    }
    public void DoCancelmov(){ own.setPos(pos_prev);}
    public void DoAfterMov(){
        BrakeAreaAction();
    }
    private Point  mov_core(){
        Point p=new Point(own.getPos());
        if( own.type == nnTypeObj.enemycore){
            p.x=p.x+dx;
            p.y=p.y+dy;
        }else if( own.type == nnTypeObj.enemy){
            p.x=p.x+dx;
            p.y=p.y+dy;
        }
        return p;
    }
    public void pre_mov(){
        if( own.type == nnTypeObj.enemycore){
            cnt++;
            if (cnt > 5){
                own.own.nnEnemy.genEnemy(own);
                cnt=0;
            }
        }
    }
    public void setmov(nnTypeObj t){
        if( t == nnTypeObj.enemycore){
            double rad = 2.0 * Math.PI * Math.random();
            dx= (int)(d*Math.cos(rad));
            dy= (int)(d*Math.sin(rad));
        }else if( t == nnTypeObj.enemy){
            double rad = 2.0 * Math.PI * Math.random();
            dx= (int)(d*Math.cos(rad));
            dy= (int)(d*Math.sin(rad));
        }
    }


}
