package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.graphics.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class nnManView {
    Resources res;
    int count;

    class Velement {
        int resid;
        Bitmap bm;
        ArrayList<Drawable> d;
        int h;
        int w;
        int nx;
        int ny;
        int imax;
    }

    HashMap<nnTypeElm, Velement> ElementMap;

    public nnManView(nnAppCore a) {
        res = a.res;
        count = 0;
        ElementMap = new HashMap<nnTypeElm, Velement>();
    }

    public void configElmBound(int inh, int inw, nnTypeElm key) {
        if (ElementMap.containsKey(key) == false) {
            return;
        }
        Velement vtmp = ElementMap.get(key);

        double tx, ty, t;
        tx = inw / vtmp.w;
        ty = inh / vtmp.h;
        if (tx > ty) {
            t = tx;
        } else {
            t = ty;
        }

        vtmp.w = (int) (vtmp.w * t);
        vtmp.h = (int) (vtmp.h * t);

        ElementMap.put(key, vtmp);
    }

    public void OverWriteBound(int inh, int inw, nnTypeElm key) {
        if (ElementMap.containsKey(key) == false) {
            return;
        }
        Velement vtmp = ElementMap.get(key);
        vtmp.w = inw;
        vtmp.h = inh;
        ElementMap.put(key, vtmp);
    }


    public Point getBound(nnTypeElm key){
        if(ElementMap.containsKey(key) ==false){
            return null;
        }
        Velement vtmp = ElementMap.get(key);
        int h=vtmp.h;
        int w=vtmp.w;
        Point p=new Point(w,h);
        return p;
    }
    public void nnDrawElm(Canvas c,int idx,nnTypeElm key,int x,int y){
        int w;
        int h;
        if(ElementMap.containsKey(key) ==false){
            return;
        }
        Velement vtmp = ElementMap.get(key);
        if(vtmp.imax <= idx ){
            idx=0;
        }
        w=vtmp.w;
        h=vtmp.h;
        Drawable dd = vtmp.d.get(idx);
        dd.setBounds(x-w/2,y-h/2,x+w/2,y+h/2);
        dd.draw(c);
    }
    public void addElem(int resid,int nx,int ny,nnTypeElm key){
        Velement vtmp = new Velement();
        Bitmap bmtmp= BitmapFactory.decodeResource(res, resid);
        Bitmap bmtmp2;
        int h= bmtmp.getHeight()/ny;
        int w=bmtmp.getWidth()/nx;
        ArrayList<Drawable> dtmp =new ArrayList();
        int i,j;
        for (i=0;i<ny;i++) {
            for(j=0;j<nx;j++){
                bmtmp2  = Bitmap.createBitmap(bmtmp,(int)(j*w),(int)(i*h), w, h);
                dtmp.add(new BitmapDrawable(bmtmp2));
            }
        }
        vtmp.resid=resid;
        vtmp.bm=bmtmp;
        vtmp.h=h;
        vtmp.w=w;
        vtmp.d=dtmp;
        vtmp.nx=nx;
        vtmp.ny=ny;
        vtmp.imax=ny*nx;
        ElementMap.put(key,vtmp);
    }
}
