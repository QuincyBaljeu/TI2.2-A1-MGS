package com.example.ti22_a1_mgs.utils;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class PopupUtil {

    public static void showNotification(Activity activity, String title, String message, DialogInterface.OnClickListener onClickListener){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onClickListener)
                .show();
    }
}
