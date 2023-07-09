package com.example.textspeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.example.textspeech.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    public static TextToSpeech tts;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         tts = new TextToSpeech(this , this , "com.google.android.tts");

        binding.button.setOnClickListener(
                v -> {


                    String text = binding.editTextTextMultiLine2.getText().toString();

                    Runnable R = new Runnable() {
                        @Override
                        public void run() {
                            tts.speak(text , TextToSpeech.QUEUE_FLUSH , null);
                        }
                    };

                   new  Thread(R).start();

                }
        );
    }

    @Override
    public void onInit(int i) {
        if(i == TextToSpeech.SUCCESS){
            int lang = tts.setLanguage(Locale.ENGLISH);

            if(lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this , "Something went wrong" , Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onDestroy() {

        super.onDestroy();

        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
    }
}