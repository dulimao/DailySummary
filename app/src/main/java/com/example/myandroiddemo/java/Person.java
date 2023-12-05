package com.example.myandroiddemo.java;

import com.example.annotationmodule.GenerateGetter;
import com.example.annotationmodule.GenerateSetter;

public class Person {
    @GenerateGetter
    private String name;
    @GenerateGetter
    @GenerateSetter
    private int age;
}
