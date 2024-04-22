package com.example.l3.dto.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrimoViewModel extends ViewModel {
    private final MutableLiveData<String> contador = new MutableLiveData<>();

    public MutableLiveData<String> getContador(){return contador;}
}
