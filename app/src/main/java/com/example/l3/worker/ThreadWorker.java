package com.example.l3.worker;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.l3.dto.Numeros;
import com.example.l3.services.TypicodeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThreadWorker extends Worker {

    TypicodeService typicodeService;

    public ThreadWorker (Context context, WorkerParameters parameters){
        super(context,parameters);
    }

    @NonNull
    @Override
    public Result doWork(){

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);

        typicodeService.getNumeros().enqueue(new Callback<List<Numeros>>() {
            @Override
            public void onResponse(Call<List<Numeros>> call, Response<List<Numeros>> response) {
                if(response.isSuccessful()){
                    List<Numeros> numeros = response.body();

                    for(Numeros n : numeros){
                        setProgressAsync(new Data.Builder().putString("contador",(n.getNumber())).build());
                        Toast.makeText(getApplicationContext(), n.getNumber(), Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<List<Numeros>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return Result.success();
    }
}
