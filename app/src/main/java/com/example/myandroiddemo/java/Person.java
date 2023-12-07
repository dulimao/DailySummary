package com.example.myandroiddemo.java;

import com.example.annotationmodule.GenerateGetter;
import com.example.annotationmodule.GenerateSetter;

import java.io.Serializable;

public class Person implements Serializable {
    @GenerateGetter(name = "name")
    private String name;
    @GenerateGetter(name = "age")
    @GenerateSetter
    private static int age;

}
