package jlexdev.com.firebase_melardev;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/** Melar Dev (YouTube-Tutorial) */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrate;
    private Button btnInicia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrate = (Button)findViewById(R.id.btn_registrate);
        btnInicia = (Button)findViewById(R.id.btn_inicia);

        btnRegistrate.setOnClickListener(this);
        btnInicia.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registrate:
                //Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                //startActivity(i);
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;

            case R.id.btn_inicia:
                startActivity(new Intent(MainActivity.this, LoginEmailActivity.class));
                break;
        }
    }
}
