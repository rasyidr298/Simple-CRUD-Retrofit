package com.errdev.crudwithretrofit.Model;

import java.util.ArrayList;
import java.util.List;

public class Value {

    String value;
    String message;
    ArrayList<Mahasiswa> mahasiswaList;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Mahasiswa> getMahasiswaList() {
        return mahasiswaList;
    }
}
