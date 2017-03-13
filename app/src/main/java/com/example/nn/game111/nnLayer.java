package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */
import android.graphics.Rect;
import java.util.ArrayList;
import android.graphics.Point;

public class nnLayer {
    String name;
    int show;
    ArrayList<nnObj> objs;
    Point area;

    public nnLayer(String name,int showing,Point inarea){
        area=new Point(inarea);
        this.show= showing;
        this.name=name;
        this.objs = new ArrayList();
   }

}
