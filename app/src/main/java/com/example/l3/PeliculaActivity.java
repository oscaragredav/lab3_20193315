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

import com.example.l3.dto.Peliculas;
import com.example.l3.services.TypicodeService;

import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeliculaActivity extends AppCompatActivity {

    TypicodeService typicodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);


        ApplicationThreads application = (ApplicationThreads) getApplication();
        ExecutorService executorService = application.executorService;

        typicodeService = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TypicodeService.class);

        if (tengoInternet1()){
            Toast.makeText(PeliculaActivity.this, "Ingresó a la vista de películas", Toast.LENGTH_SHORT).show();

            typicodeService.getPeliculas().enqueue(new Callback<Peliculas>() {
                @Override
                public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                    if(response.isSuccessful()){
                        Peliculas pelicula = response.body();

                        TextView tvTitulo = findViewById(R.id.tvTitulo);
                        tvTitulo.setText(pelicula.getTitle());
                        TextView tvDirector = findViewById(R.id.tvDirector);
                        tvDirector.setText(pelicula.getDirector());
                        TextView tvActores = findViewById(R.id.tvActores);
                        tvActores.setText(pelicula.getActors());
                        TextView tvFecha = findViewById(R.id.tvFecha);
                        tvFecha.setText(pelicula.getReleased());
                        TextView tvGeneros = findViewById(R.id.tvGeneros);
                        tvGeneros.setText(pelicula.getGenre());
                        TextView tvEscritores = findViewById(R.id.tvEscritores);
                        tvEscritores.setText(pelicula.getWriters());
                        TextView tvTrama = findViewById(R.id.tvTrama);
                        tvTrama.setText(pelicula.getPlot());

                    }
                }

                @Override
                public void onFailure(Call<Peliculas> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(PeliculaActivity.this, "onFailure problem", Toast.LENGTH_LONG).show();
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