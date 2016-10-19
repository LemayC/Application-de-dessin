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

public class Reader implements NfcAdapter.ReaderCallback {
    private CanvasView customCanvas;

    public Reader(CanvasView customCanvas) {
        this.customCanvas = customCanvas;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        MifareUltralight amiibo = MifareUltralight.get(tag);
        byte[] info = new byte[0];
        int resId = 0;

        try{
            amiibo.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connexion avec amiibo FAIL");
        }

        if (amiibo.isConnected()){
            try{
                //Luigi = 28
                //Yoshi = 33
                //Mario = -6
                info = amiibo.readPages(0);
                switch ((int) info[1]) {
                    case 33:
                        resId = R.drawable.image_yoshi;
                        System.out.println("yoshi!");
                        break;
                    case -6:
                        resId = R.drawable.image_mario;
                        System.out.println("mario!");
                        break;
                    default:
                        resId = R.drawable.image_luigi;
                        System.out.println("luigi!");
                        break;
                }
                customCanvas.setBackgroudImage(resId);
                amiibo.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Fermeture du amiibo FAIL");
            }

        }
    }

}
