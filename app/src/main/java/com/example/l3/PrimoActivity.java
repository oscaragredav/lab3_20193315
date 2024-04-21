package com.example.l3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.l3.dto.Numeros;
import com.example.l3.dto.Numeros;
import com.example.l3.services.TypicodeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrimoActivity extends AppCompatActivity {

    TypicodeService typicodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primo);
        Toast.makeText(getApplicationContext(), "Ingresó a la vista de números primos", Toast.LENGTH_SHORT).show();

        ApplicationThreads application = (ApplicationThreads) getApplication();

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);

        if (tengoInternet1()){

            typicodeService.getNumeros().enqueue(new Callback<List<Numeros>>() {
                @Override
                public void onResponse(Call<List<Numeros>> call, Response<List<Numeros>> response) {
                    if(response.isSuccessful()){
                        List<Numeros> numeros = response.body();
                        TextView tvPrimo = findViewById(R.id.tvPrimo);
                        for(Numeros c : numeros){
                            tvPrimo.setText(c.getNumber());


                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Numeros>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(PrimoActivity.this, "onFailure problem", Toast.LENGTH_LONG).show();
                }
            });   
        }


    }
    public boolean tengoInternet1() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test-internet", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}