package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class nnManInfo {
    nnAppCore own;
    Activity act;
    int highscore;

    ArrayList<nnObj> scoreobjs;

    int score;

    public  nnManInfo(nnAppCore a){
        highscore=-1;
        own=a;
        act=a.act;
        score=0;
        scoreobjs = new ArrayList();
    }
    public void readSaveData(){
        highscore=0;
        try {
            InputStream is = act.openFileInput("savedata.txt");
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(is));
            String str;
            String[] para;
            String c;

            try {
                while ((str = br.readLine()) != null) {
                    if(Integer.toHexString(str.charAt(0)).equals("feff")){str=str.substring(1);}
                    c=Character.toString(str.charAt(0));
                    if( c.equals("@") ){
                        str=str.substring(1);
                        para= str.split(",",0);
                        //not implemented here.
                        highscore = Integer.parseInt(para[1]);
                    }
                }
            } finally {  if (br != null) { br.close();  is.close(); }}
        } catch (IOException e) {       }
        return;
    }
    public void writeSaveData(){

        try {
            OutputStream os = act.openFileOutput("savedata.txt", act.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            String str;
            String[] para;
            String c;

            try {
                str = new String("@highscore,"+highscore);
                bw.write(str);

            } finally {  if (bw != null) { bw.close();  os.close(); }}
        } catch (IOException e) {       }
    }
    public void prepEnemy(){
        nnObj enemycore = new nnObj(own,nnTypeObj.enemycore);
        enemycore.InitView();
        enemycore.InitMov();
        enemycore.InitAtari();
        Point p = new Point();
        p.x=own.nnArea.x/2;
        p.y=own.nnArea.y/2;
        enemycore.setPos(p);
        own.IntoOjb("char",enemycore);
    }
    public void prepBG(){
        nnObj bg = new nnObj(own,nnTypeObj.background);
        bg.InitView();

        Point p = new Point();
        p.x=own.nnArea.x/2;
        p.y=own.nnArea.y/2;
        bg.setPos(p);
        bg.viewConfig(1, own.nnArea);
        own.IntoOjb("background",bg);
    }
    public void prepTouch(int x,int y){
        nnObj touch_effect = new nnObj(own,nnTypeObj.effect_touch);
        touch_effect.InitView();
        touch_effect.InitAtari();
        Point p = new Point(x,y);
        touch_effect.setPos(p);
        own.IntoOjb("char",touch_effect);
    }
    public void set_num(int num){
        if ( num > 999999 || num < 0 ){
            return;
        }
        int len = String.valueOf(num).length();
        int d=(int)Math.pow(10,len-1);
        for(int i=1 ; i<=scoreobjs.size();i++){
            if( scoreobjs.size() -len < i ) {
                scoreobjs.get(i - 1).nnview.idx = num / d;
                num%=d;
                d/=10;
            }else{
                scoreobjs.get(i - 1).nnview.idx=0;
            }
        }
    }
    public void prep_score(){
        for( int i=0 ;i < 6;i++){
            nnObj obj=new nnObj(own,nnTypeObj.num_score);
            obj.InitView();
            Point p= new Point(200+i*100,150);
            obj.setPos(p);
            p = new Point(80,100);
            obj.viewConfig(2, p);
            obj.nnview.idx=i;
            own.IntoOjb("char",obj);
            scoreobjs.add(obj);
        }

        if( highscore != -1 ){
            set_num(highscore);
        }

    }
}
