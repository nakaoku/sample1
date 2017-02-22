package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.content.res.Resources;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;

import java.util.ArrayList;


public class nnAppCore extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder holder;
    private Thread thread;
    boolean isSurfaceStandby=false;
    boolean InnerPause;
    Context c;
    Resources res;
    Activity act;
    nnManSnd nnSnd;
    nnManView nnView;;
    nnManInfo nnInfo;
    nnManEnemy nnEnemy;

    ArrayList<nnLayer> nnLayer;
    Point nnArea;

    public nnAppCore(Context context, int inh, int inw,Activity inact) {
        super(context);
        InnerPause = false;
        c=context;
        res = getResources();
        act = inact;
        nnArea = new Point(inw,inh);
        init_load();
        holder = getHolder();
        holder.addCallback(this);
        holder.setFixedSize(getWidth(), getHeight());//
    }

    public void init_load(){
        nnSnd=new nnManSnd(this);
        nnSnd.nnBGMstart(2);

        nnView=new nnManView(this);
        nnView.addElem(R.drawable.img0001, 1, 1, nnTypeElm.cat);
        nnView.addElem(R.drawable.img0002, 1, 1, nnTypeElm.background);

        nnLayer=new ArrayList();
        nnLayer.add(new nnLayer("debug001", 1,nnArea));//0
        nnLayer.add(new nnLayer("background", 1,nnArea));
        nnLayer.add(new nnLayer("char",1, nnArea));//3
        nnLayer.add(new nnLayer("debug002",1, nnArea));//11

        nnEnemy = new nnManEnemy(this);

        nnInfo= new nnManInfo(this);
        nnInfo.readSaveData();

        nnInfo.prepEnemy();
        nnInfo.prepBG();

    }

    public void IntoOjb(String name,nnObj obj){
        int i;
        for (i = 0; i < nnLayer.size(); i++) {
            if( nnLayer.get(i).name.equals(name)) {
                obj.nnlayer=nnLayer.get(i);
                obj.nnlayer.objs.add(obj);
            }
        }
    }
    public void onDrawCore(Canvas c){
        int i, j;
        for (i = 0; i < nnLayer.size(); i++) {
            if (nnLayer.get(i).show == 1) {
                ArrayList<nnObj> objs = nnLayer.get(i).objs;
                for (j = 0; j < objs.size(); j++) {
                    nnObj obj = objs.get(j);
                    if( obj.mustremove){
                        objs.remove(obj);
                        obj=null;
                    }else {
                        Point p = null;
                        if (obj.nnmov != null ) { p = new Point( obj.move()); }
                        if (obj.nnatari != null ) { obj.nnAtariCheckAndDo(nnLayer);  }
                        if (obj.nnmov != null ) { obj.ActionAfterMov(p);}
                        if (obj.visible) {obj.nnDraw(c);}
                    }
                }
            }
        }
    }


    @Override
    public void run() {
        Canvas c;
        while(thread != null){
            try{
                c = holder.lockCanvas();
                if (!isSurfaceStandby) {
                    return;
                }
                c.drawColor(Color.BLACK);
                synchronized (holder) {
                    onDrawCore(c);
                }
            }finally{
                //do nothing
            }
            if( c !=null) {
                holder.unlockCanvasAndPost(c);
            }
            try {
                Thread.sleep(50);//お決まり
            } catch (Exception e){}
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int tmpx,tmpy;
        int did=0;
        /*visual effect*/
        tmpx=(int)event.getX();
        tmpy=(int)event.getY();
        synchronized (this){
            int i,j;
            for (i = 0; i < nnLayer.size(); i++) {
                if( nnLayer.get(i).show == 1) {
                    ArrayList<nnObj> objs = nnLayer.get(i).objs;
                    for (j = 0; j < objs.size(); j++) {
                        nnObj obj = objs.get(j);
                        if( obj.visible  ) {
                            if (obj.nnatari != null ) {
                                if (obj.ClickAction(tmpx, tmpy)) {
                                    did++;
                                }
                                //never call move,draw.
                            }
                        }
                    }
                }
            }
        }
        if( did == 0){
            GoClick();
        }
        return true;
    }
    public void onResume(Context context) {
        nnSnd.nnOnResume(context);
    }
    public void onPause(Context context) {
        nnSnd.nnOnPause();
    }
    public void GoClick() {
        nnSnd.nnDoSound(0);
        //sp.play(sid[1],1.0F,1.0F,0,0,1.0F);
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {/*nothing*/}
    public void surfaceCreated(SurfaceHolder arg0) {
        isSurfaceStandby=true;
        thread = new Thread(this);thread.start();}
    public void surfaceDestroyed(SurfaceHolder arg0) {
        isSurfaceStandby=false;
        thread = null;}//


    public void setPause(){this.InnerPause=true;}
    public void clearPause(){this.InnerPause=false;}
}

