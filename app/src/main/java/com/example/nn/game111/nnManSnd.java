package com.example.nn.game3;

/**
 * Created by nn on 2017/02/05.
 */
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.AudioManager;

import java.util.ArrayList;
import java.util.HashMap;


public class nnManSnd {
    nnAppCore own;
    Context c;
    /*sound setting*/
    SoundPool sp;

    HashMap<Integer,Integer> bgm_map;
    ArrayList<Integer> sid;
    //media
    private MediaPlayer mp;

    public nnManSnd(nnAppCore a){
        own=a;
        c=a.c;
        bgm_map= new HashMap<Integer,Integer>();
        bgm_map.put(1, R.raw.sd04_opening);
        bgm_map.put(2, R.raw.sd09_dq2_castle);

    }

    void  nnDoSound(int id){
        int val = sid.get(id);
        sp.play(val, 1.0F, 1.0F, 0, 0, 1.0F);
        // sp.play(sid[1],1.0F,1.0F,0,0,1.0F);
    }
    public void nnBGMstart(int k) {
        if( bgm_map.containsKey(k) == false ){
            return ;
        }
        if(mp != null){
            if ( mp.isPlaying()) {
                mp.stop();
            }
            mp.release();
        }

        mp = MediaPlayer.create(c, bgm_map.get(k));
        mp.setLooping(true);
        mp.start();
    }
    public void nnOnResume(Context c){
        sid=new ArrayList();
        sp=new SoundPool(10,AudioManager.STREAM_MUSIC,0);

        sid.add(0, sp.load(c, R.raw.sd05_pickup0001, 1));
        sid.add(1, sp.load(c, R.raw.sd06_pdown0001, 1));
        sid.add(2, sp.load(c, R.raw.sd07_stair0001, 1));
        sid.add(3, sp.load(c, R.raw.sd08_jump0001, 1));
        sid.add(4, sp.load(c, R.raw.sd10_tama0001, 1));
        sid.add(5, sp.load(c, R.raw.sd11_bom0001, 1));
        sid.add(6, sp.load(c, R.raw.sd12_damage0001, 1));

        if (mp != null && mp.isPlaying() == false) {
            mp.start();
        }
    }
    public void nnOnPause(){
        sp.unload(sid.get(0));
        sp.unload(sid.get(1));
        sp.unload(sid.get(2));
        sp.unload(sid.get(3));
        sp.unload(sid.get(4));
        sp.unload(sid.get(5));
        sp.unload(sid.get(6));

        sid.clear();
        sid=null;
        sp.release();
        sp=null;
        if ( mp!=null && mp.isPlaying()){
            mp.pause();
        }
    }
}
