package com.hcb.customkeyboardkey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;

import com.hcb.keyboardview.KeyboardListener;
import com.hcb.keyboardview.KeyboardView;

public class MainActivity extends AppCompatActivity {
    private EditText edOutput;
    private KeyboardView keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edOutput = findViewById(R.id.txtOutput);
        keyboard = findViewById(R.id.keyboard);

        // prevent system keyboard from appearing when EditText is tapped
        edOutput.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edOutput.setTextIsSelectable(true);

        // pass the InputConnection from the EditText to the keyboard
        InputConnection ic = edOutput.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);
    }
}
