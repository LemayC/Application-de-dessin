package com.example.a1350150.drawingapp;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private CanvasView customCanvas;
    private NfcAdapter adapter;
    private reader callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        callback = new reader(customCanvas);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

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