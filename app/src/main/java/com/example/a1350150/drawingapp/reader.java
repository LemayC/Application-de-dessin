package com.example.a1350150.drawingapp;

/**
 * Created by 9565960 on 2016-10-18.
 */

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;

import java.io.IOException;

public class reader implements NfcAdapter.ReaderCallback {
    private CanvasView customCanvas;

    public reader(CanvasView customCanvas) {
        this.customCanvas = customCanvas;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        MifareUltralight amiibo = MifareUltralight.get(tag);
        byte[] info = new byte[0];

        try{
            amiibo.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connexion avec amiibo FAIL");
        }

        if (amiibo.isConnected()){

            try{
                info = amiibo.readPages(21);

                System.out.println(info[0]);
                System.out.println(info[1]);
                System.out.println(info[2]);
                System.out.println(info[3]);

                amiibo.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Fermeture du amiibo FAIL");
            }

        }

        customCanvas.clearCanvas();
    }

}
