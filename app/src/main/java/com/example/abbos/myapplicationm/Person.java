package com.example.abbos.myapplicationm;

import io.realm.RealmObject;

public class Person extends RealmObject {

    public String name;
    public int age;

    void setName(String name) {
        this.name = name;
    }

    void setAge(int age) {
        this.age = age;
    }
    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }
    // ... Generated getters and setters ...
}