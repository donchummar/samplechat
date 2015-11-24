package com.chatapp.nod.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.chatapp.nod.activity.StartUpActivity;

/**
 * Created by Don Chummar on 11/19/2015.
 */
public class Utils {
    public static void showDialog(Context ctx, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
