package com.example.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Login;

public class LogOut {

    public static void exec(Context context, SharedPreferences preferences, @Nullable String message) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("id");
        editor.remove("token");
        editor.remove("height");
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
        String toastMessage = message != null ? message : "La sesión ha expirado";
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
    }

}
