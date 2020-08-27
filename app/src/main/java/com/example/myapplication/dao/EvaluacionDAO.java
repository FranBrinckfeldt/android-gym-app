package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.controller.SqliteHelper;
import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.utils.Constantes;

import java.util.ArrayList;

public class EvaluacionDAO implements iCRUD<Evaluacion> {

    private Context context;
    private SqliteHelper admin;
    private final String table = "evaluations";
    private SharedPreferences preferences;

    public EvaluacionDAO(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
    }

    @Override
    public boolean insert(Evaluacion evaluacion) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put("user_id", evaluacion.getUid());
        registry.put("date", evaluacion.getDate());
        registry.put("height", evaluacion.getEstatura());
        registry.put("weight", evaluacion.getPeso());
        registry.put("imc", evaluacion.getImc());
        boolean success = db.insert(table, null, registry) != -1;
        db.close();
        return success;
    }

    @Override
    public ArrayList<Evaluacion> findAll() {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor rows = db.rawQuery("SELECT id, user_id, date, height, weight, imc FROM evaluations WHERE user_id = ? ORDER BY date ASC", new String[] {Integer.toString(this.preferences.getInt("id", 0))});
        ArrayList<Evaluacion> evaluaciones = new ArrayList<>();
        while(rows.moveToNext()) {
            int id = Integer.parseInt(rows.getString(0));
            int uid = Integer.parseInt(rows.getString(1));
            String date = rows.getString(2);
            double estatura = Double.parseDouble(rows.getString(3));
            double peso = Double.parseDouble(rows.getString(4));
            double imc = Double.parseDouble(rows.getString(5));
            evaluaciones.add(new Evaluacion(id, uid, date, peso, estatura, imc));
        }
        db.close();
        return evaluaciones;
    }

    public ArrayList<Evaluacion> findAllByDate(String[] dates) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor rows = db.rawQuery("SELECT id, user_id, date, height, weight, imc FROM evaluations WHERE user_id = ? AND date BETWEEN ? AND ? ORDER BY date ASC", new String[] {Integer.toString(this.preferences.getInt("id", 0)), dates[0], dates[1]});
        ArrayList<Evaluacion> evaluaciones = new ArrayList<>();
        while(rows.moveToNext()) {
            Log.d("id", rows.getString(0));
            int id = Integer.parseInt(rows.getString(0));
            int uid = Integer.parseInt(rows.getString(1));
            String date = rows.getString(2);
            double estatura = Double.parseDouble(rows.getString(3));
            double peso = Double.parseDouble(rows.getString(4));
            double imc = Double.parseDouble(rows.getString(5));
            evaluaciones.add(new Evaluacion(id, uid, date, peso, estatura, imc));
        }
        db.close();
        return evaluaciones;
    }

    @Override
    public Evaluacion findById(int id) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor row = db.rawQuery("SELECT user_id, date, height, weight, imc FROM evaluations", null);
        if (row.moveToFirst()) {
            int uid = Integer.parseInt(row.getString(0));
            String date = row.getString(1);
            double estatura = Double.parseDouble(row.getString(2));
            double peso = Double.parseDouble(row.getString(3));
            double imc = Double.parseDouble(row.getString(4));
            db.close();
            return new Evaluacion(id, uid, date, peso, estatura, imc);
        }
        db.close();
        return null;
    }

    @Override
    public boolean update(Evaluacion evaluacion) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put("user_id", evaluacion.getUid());
        registry.put("date", evaluacion.getDate());
        registry.put("height", evaluacion.getEstatura());
        registry.put("weight", evaluacion.getPeso());
        registry.put("imc", evaluacion.getImc());
        boolean isUpdated = db.update(table, registry, "id = ?", new String[] {Integer.toString(evaluacion.getId())}) > 0;
        db.close();
        return isUpdated;
    }

    @Override
    public boolean deleteById(int id) {
        admin = new SqliteHelper(context, Constantes.DATABASE, null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        boolean success = db.delete(table, "id = ?", new String[] {Integer.toString(id)}) > 0;
        db.close();
        return success;
    }
}
