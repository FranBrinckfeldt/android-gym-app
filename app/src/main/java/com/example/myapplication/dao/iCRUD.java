package com.example.myapplication.dao;

import java.util.ArrayList;

public interface iCRUD<T> {

    boolean insert(T t);
    ArrayList<T> findAll();
    T findById(int id);
    boolean update(T t);
    boolean deleteById(int id);

}
