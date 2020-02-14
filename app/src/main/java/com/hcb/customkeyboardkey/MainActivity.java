package com.hcb.customkeyboardkey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hcb.keyboardview.KeyboardListener;
import com.hcb.keyboardview.KeyboardView;

public class MainActivity extends AppCompatActivity {
    private TextView txtOutput;
    private KeyboardView keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOutput = findViewById(R.id.txtOutput);
        keyboard = findViewById(R.id.keyboard);

        keyboard.setOutput(txtOutput);

        keyboard.setListener(new KeyboardListener() {
            @Override
            public void onAdd(KeyboardView keyboardView, String dataAdded, String newText, String oldText) {
                Log.i("Keyboard_OnAdd", "Added: " + dataAdded +" - New text: " + newText);
            }

            @Override
            public void onDelete(KeyboardView keyboardView, String newText, String oldText) {
                Log.i("Keyboard_OnDelete",  "New text: " + newText);
            }
        });
    }
}
