package com.example.crud;

import com.example.crud.view.ApplicationContext;

import java.io.*;

public class CrudConsoleSolution {
    public static void main(String[] args) throws IOException {
        ApplicationContext crudRunner = new ApplicationContext();
        crudRunner.loadContext();
    }
}
