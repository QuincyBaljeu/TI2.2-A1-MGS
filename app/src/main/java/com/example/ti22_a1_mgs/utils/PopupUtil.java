package com.example.ti22_a1_mgs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

public class PopupUtil {

    public static void showAlertDialog(Activity activity, String title, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onClickListener)
                .show();
    }

    public static void showToast(Context context, String message, int length) {
        Toast.makeText(context, message, length)
                .show();
    }

    public static void showSnackbarNotification(View view, String message, int duration, String actionText ,View.OnClickListener onClickListener){
        Snackbar.make(view,message,duration)
                .setAction(actionText,onClickListener)
                .show();
    }
}
