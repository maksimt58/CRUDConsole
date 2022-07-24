package com.example.crud;

import com.example.crud.view.BaseCrudRunner;

import java.io.*;

public class CrudConsoleSolution {
    public static void main(String[] args) throws IOException {
        BaseCrudRunner crudRunner = new BaseCrudRunner();
        crudRunner.loadContext();
    }
}
