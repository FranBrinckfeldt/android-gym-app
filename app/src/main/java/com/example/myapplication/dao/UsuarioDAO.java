package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.controller.SqliteHelper;
import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.utils.Constantes;

import java.util.ArrayList;

public class UsuarioDAO implements iCRUD<Usuario> {

    private Context context;
    private SqliteHelper admin;
    private final String table = "users";

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean insert(Usuario usuario) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put("username", usuario.getUsuario());
        registry.put("firstname", usuario.getNombre());
        registry.put("lastname", usuario.getApellido());
        registry.put("birth", usuario.getFechaNacimiento());
        registry.put("height", usuario.getEstatura());
        registry.put("password", usuario.getClave());
        boolean success = db.insert(table, null, registry) != -1;
        db.close();
        return success;
    }

    @Override
    public ArrayList<Usuario> findAll() {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor rows = db.rawQuery("SELECT id, username, firstname, lastname, birth, height FROM users", null);
        ArrayList<Usuario> usuarios = new ArrayList<>();
        while(rows.moveToNext()) {
            int id = Integer.parseInt(rows.getString(0));
            String username = rows.getString(1);
            String firstname = rows.getString(2);
            String lastname = rows.getString(3);
            String birth = rows.getString(4);
            double height = Double.parseDouble(rows.getString(5));
            usuarios.add(new Usuario(id,username, firstname, lastname, birth, height));
        }
        db.close();
        return usuarios;
    }

    public Usuario login(String username, String password) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor row = db.rawQuery("SELECT id, username, firstname, lastname, birth, height, password FROM users WHERE username = ?", new String[] {username});
        if(row.moveToFirst()) {
            int id = Integer.parseInt(row.getString(0));
            String firstname = row.getString(2);
            String lastname = row.getString(3);
            String birth = row.getString(4);
            double height = Double.parseDouble(row.getString(5));
            String password_db = row.getString(6);
            db.close();
            if (password.equals(password_db)) {
                return new Usuario(id,username, firstname, lastname, birth, height);
            } else {
                return null;
            }
        } else {
            db.close();
            return null;
        }

    }

    @Override
    public Usuario findById(int id) {
        return null;
    }

    @Override
    public boolean update(Usuario usuario) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
