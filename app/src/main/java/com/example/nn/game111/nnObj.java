package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Rect;
import android.graphics.Point;
import java.util.ArrayList;
public class nnObj {
    nnAppCore own;
    nnTypeObj type;
    private Point pos_c;
    private Point sz;

    int cnt;// view count of live;

    public boolean  mustremove;
    public boolean visible;

    nnLayer nnlayer;
    nnSpecAtari nnatari;
    nnSpecMov nnmov;
    nnSpecView nnview;

    public nnObj(nnAppCore o,nnTypeObj t) {
        type = t;
        own=o;
        visible = true;
        mustremove=false;
    }

    public void InitMov() {
        if (type == nnTypeObj.enemy) {
            nnmov = new nnSpecMov(this);
        }else if( type == nnTypeObj.enemycore){
            nnmov = new nnSpecMov(this);
        } else if (type == nnTypeObj.background){
            nnmov =null;
        } else if ( type == nnTypeObj.effect_touch){
            nnmov=null;
        }
    }
    public void InitView(){
        if( type == nnTypeObj.enemycore){
            nnview = new nnSpecView(this,nnTypeElm.cat);
        }else if(type == nnTypeObj.enemy){
            nnview = new nnSpecView(this,nnTypeElm.cat2);
        }else if( type == nnTypeObj.background){
            nnview = new nnSpecView(this,nnTypeElm.background);
        }else if( type == nnTypeObj.effect_touch) {
            nnview = new nnSpecView(this, nnTypeElm.effect_touch);
            cnt=3;
        }else if( type == nnTypeObj.num_score){
            nnview = new nnSpecView(this,nnTypeElm.num_score);
        }
        sz=nnview.getBound();

    }
    public void InitAtari(){
        nnatari =new nnSpecAtari(this);
    }

    public Point getPos(){ return pos_c; }
    public void setPos(Point p){pos_c=p;}

    public void move(){
        nnmov.mov();
    }
    public void ActionAfterMov( ){
        if( this.nnmov != null) {
            nnmov.DoAfterMov();
        }
        if(type == nnTypeObj.enemycore){

        }
    }


    public void nnAtariCheckAndDo( ArrayList<nnLayer>  ll){
        int n, m;
        for (n = 0; n < ll.size(); n++) {
            if (ll.get(n).show == 1) {
                ArrayList<nnObj> objs_atari = ll.get(n).objs;
                for (m = 0; m < objs_atari.size(); m++) {
                    nnObj obj_atari = objs_atari.get(m);
                    if (obj_atari != this) {
                        if (obj_atari.nnatari != null ) {
                            if (this.isConflict(obj_atari)) {
                                this.AtariAction(obj_atari);
                            }
                        }
                    }
                }
            }
        }
    }

    public void nnDraw(Canvas c){
        if( type == nnTypeObj.effect_touch){
            if( --cnt < 0 ){
                mustremove=true;
            }
        }
        if(nnview != null){
            nnview.nnDraw(c,getPos().x,getPos().y);
        }
    }
    public void viewConfig(int type,Point p) {
        //1: backgound
        //2:score
        if (this.nnview != null) {
            switch(type){
                case 1:
                    nnview.configBound(p.y,p.x);
                    break;
                case 2:
                    nnview.configBoundOverWrite(p.y, p.x);
                    break;
                default:
                    break;
            }
        }
    }
    public boolean isConflict(nnObj target){
        if( target.nnatari != null ){return false;}
        if( this.nnatari != null ){return false;}
        return this.nnatari.isConflict(this,target);
    }

    public boolean ClickAction(int cx,int cy){

        boolean ret=false;
        int x,y;
        x=pos_c.x;
        y=pos_c.y;

        if (x - sz.x / 2 < cx && x + sz.x / 2 > cx
                && y - sz.y / 2 < cy && y + sz.y / 2 > cy) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }
    public void AtariAction(nnObj obj){
        if(      type == nnTypeObj.enemy && obj.type == nnTypeObj.effect_touch ){
            this.mustremove=true;
        }else if( type == nnTypeObj.effect_touch && obj.type == nnTypeObj.enemy ) {
            obj.mustremove=true;
        }
    }
}
