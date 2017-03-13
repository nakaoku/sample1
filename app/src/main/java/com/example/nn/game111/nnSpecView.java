package com.example.nn.game3;

/**
 * Created by nn on 2017/02/10.
 */
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;


public class nnSpecView {
    nnObj own;
    nnTypeElm type;
    int idx;
    int ist;
    int ien;

    public nnSpecView(nnObj obj,nnTypeElm t){
        own=obj;type=t;
        ist=ien=idx=0;
    }
    public void nnDraw(Canvas c,int x,int y){
        own.own.nnView.nnDrawElm(c,idx,type,x,y);
    }
    public Point getBound(){
        return own.own.nnView.getBound(type);
    }
    public void configBound(int inh,int inw){
        own.own.nnView.configElmBound(inh,inw,type);
    }
    public void configBoundOverWrite(int inh,int inw){
        own.own.nnView.OverWriteBound(inh,inw,type);
    }

}
