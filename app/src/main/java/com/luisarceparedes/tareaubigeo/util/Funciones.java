package com.luisarceparedes.tareaubigeo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Spinner;

import com.luisarceparedes.tareaubigeo.R;

/**
 * Created by laboratorio_computo on 22/09/2016.
 */
public class Funciones {

    private static boolean mResult;

    public static boolean mensajeConfirmacion(Context context, String title, String message) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };

        // make a text input dialog and show it
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setIcon(R.drawable.eliminar);
        alert.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mResult = true;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mResult = false;
                handler.sendMessage(handler.obtainMessage());
            }
        });
        alert.show();

        // loop till a runtime exception is triggered.
        try {
            Looper.loop();
        }
        catch(RuntimeException e2) {

        }

        return mResult;
    }

    public static void selectedItemSpinner(Spinner spinner, String itemSelection){
        int position = 0;
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(itemSelection)){
                position = i;
                break;
            }
        }
        spinner.setSelection(position);
    }

}
