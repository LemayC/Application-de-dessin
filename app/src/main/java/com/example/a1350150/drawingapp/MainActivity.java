package com.example.a1350150.drawingapp;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private CanvasView customCanvas;
    private NfcAdapter adapter;
    private Reader callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        callback = new Reader(customCanvas);

        adapter = NfcAdapter.getDefaultAdapter(this);
        adapter.enableReaderMode(this, this.callback, NfcAdapter.STATE_ON, null);
    }

    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }

    public void changeDrawingColor(View v){
        customCanvas.changeDrawingColor(v);
    }



}