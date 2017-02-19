package com.example.nn.origsample;

import android.os.Bundle;
/*nakaoku*/
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.PixelFormat;
/*nakaoku*/

public class MainActivity extends Activity  {
    public int h,w;
    nnAppCore nndbg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);*/
        /*init nakaoku*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Window window = getWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        w=display.getWidth();
        h=display.getHeight();
        nndbg =  new nnAppCore(this,h,w,this);
        setContentView(nndbg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*need register services */
        nndbg.onResume(getApplicationContext());
    }
    @Override
    protected void onPause() {
        super.onPause();
        /*need un-register services */
        nndbg.onPause(getApplicationContext());
    }

}
