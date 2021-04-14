package ru.samsung.itschool.mdev.yandexpredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String PREDICTOR_URI_JSON = "https://predictor.yandex.net/";
    private static String PREDICTOR_KEY = "!!!Replace with key!!!";
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                getReport();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
    }

    public void getReport() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PREDICTOR_URI_JSON)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestAPI service = retrofit.create(RestAPI.class);
        Call<Model> call = service.predict(PREDICTOR_KEY, editText.getText().toString(), "en");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                try {
                    Log.d("Yandex",response.body().text[0]);
                    String textWord = response.body().text[0];
                    textView.setText("Predictor: " + textWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("Yandex",t.getMessage());
            }
        });
    }
}