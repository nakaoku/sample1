package com.example.nn.game3;

/**
 * Created by nn on 2017/02/10.
 */
public class nnManEnemy {
    nnAppCore own;
    int count;
    int max;
    public nnManEnemy(nnAppCore a){
        max=15;
        count=0;
        own = a;
    }
    public boolean genEnemy(nnObj base){
        boolean ret= false;
        if( max >  count) {
            count++;
            nnObj obj = new nnObj(base.own,nnTypeObj.enemy);
            obj.InitMov();
            obj.InitView();
            obj.InitAtari();
            obj.setPos(base.getPos());
            own.IntoOjb("char",obj);
            ret=true;
        }
        own.nnInfo.set_num(count);
        return ret;
    }
    public void destroy(){
        count--;
    }
}
