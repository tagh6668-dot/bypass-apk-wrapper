package com.bypass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText linkInput;
    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        linkInput = new EditText(this);
        linkInput.setHint("Paste Join Link Here");
        layout.addView(linkInput);

        btnStart = new Button(this);
        btnStart.setText("Start Proxy");
        layout.addView(btnStart);

        btnStop = new Button(this);
        btnStop.setText("Stop Proxy");
        layout.addView(btnStop);

        setContentView(layout);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = linkInput.getText().toString().trim();
                if (link.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a link", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ProxyService.class);
                intent.putExtra("link", link);
                startService(intent);
                Toast.makeText(MainActivity.this, "Proxy Service Started", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, ProxyService.class));
                Toast.makeText(MainActivity.this, "Proxy Service Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
