package com.example.l3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.l3.databinding.ActivityMainBinding;
import com.example.l3.databinding.ActivityPrimoBinding;
import com.example.l3.dto.Numeros;
import com.example.l3.dto.Numeros;
import com.example.l3.services.TypicodeService;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrimoActivity extends AppCompatActivity {
    private ActivityPrimoBinding binding;
    TypicodeService typicodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrimoBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(getApplicationContext(), "Ingresó a la vista de números primos", Toast.LENGTH_SHORT).show();

        ApplicationThreads application = (ApplicationThreads) getApplication();

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);

        Button buttonBuscar = findViewById(R.id.buttonBuscar);
        Button buttonASc = findViewById(R.id.buttonAsc);

        buttonBuscar.setOnClickListener(v ->
                buscarPrimo()
        );
        buttonASc.setOnClickListener(v ->
                ascender()
        );


    }
    public void buscarPrimo(){
        if (tengoInternet1()){
            typicodeService.getNumeros().enqueue(new Callback<List<Numeros>>() {
                @Override
                public void onResponse(Call<List<Numeros>> call, Response<List<Numeros>> response) {
                    if(response.isSuccessful()){
                        List<Numeros> numeros = response.body();
                        TextView tvPrimo = findViewById(R.id.tvPrimo);
                        EditText editText = findViewById(R.id.editText);
                        TextView tv3 = findViewById(R.id.tvPrimo);
                        TextView tv4 = findViewById(R.id.tvPrimo0);
                        int intIngresado = Integer.parseInt(editText.getText().toString());

                        if(intIngresado>=999 || intIngresado<1 ){
                            Toast.makeText(PrimoActivity.this, "Escoger un número entre 1 y 999", Toast.LENGTH_LONG).show();
                        }else{
                            for(Numeros c : numeros){
                                if (intIngresado == Integer.parseInt(c.getOrder())){
                                    tvPrimo.setText(c.getNumber());
                                }
                            }
                            tv3.setVisibility(View.VISIBLE);
                            tv4.setVisibility(View.VISIBLE);
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

    public void ascender(){
        if (tengoInternet1()){
            UUID uuid = UUID.randomUUID();

            WorkRequest workRequest = new OneTimeWorkRequest.Builder(ThreadWorker.class)
                    .setId(uuid)
                    .build();

            WorkManager
                    .getInstance(PrimoActivity.this.getApplicationContext())
                    .enqueue(workRequest);

            WorkManager
                    .getInstance(binding.getRoot().getContext())
                    .getWorkInfoByIdLiveData(uuid)
                    .observe(PrimoActivity.this, workInfo -> {
                        if(workInfo != null){
                            Data progress = workInfo.getProgress();
                            String contador = progress.getString("contador");
                            Log.d("msg-test-progress", "progress: " + contador);
                            binding.tvthread.setText(String.valueOf(contador));
                        }else{
                            Log.d("msg-test", "work info == null ");
                        }
                    });

        }
    }



    public boolean tengoInternet1() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-internet", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}