package jlexdev.com.firebase_melardev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
    private TextView tvLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText)findViewById(R.id.et_name);
        etEmail = (EditText)findViewById(R.id.et_email);
        etPassword = (EditText)findViewById(R.id.et_password);
        btnRegister = (Button)findViewById(R.id.btn_register);
        tvLogin = (TextView)findViewById(R.id.tv_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = (FirebaseAuth.getInstance());


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginEmailActivity.class));
                finish();
            }
        });


        // Escuchador del estado de Autenticación
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(RegisterActivity.this, LoginEmailActivity.class));
                    finish();
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Estado de Login
        firebaseAuth.addAuthStateListener(authStateListener);
    }


    private void startRegister() {
        final String name = etName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        // Aseguro que ninguno de los campos esté vacío
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressDialog.setMessage("Registrando...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Quito Progress Dialog
                            progressDialog.dismiss();

                            // Compruebo Registro
                            if (task.isSuccessful()){
                                firebaseAuth.signInWithEmailAndPassword(email, password);
                                // startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                // finish();

                                // Guardar Información relacionada con el Usuario
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");
                                DatabaseReference currentUserDb = db.child(firebaseAuth.getCurrentUser().getUid());

                                currentUserDb.child("name").setValue(name);
                                currentUserDb.child("image").setValue("default");

                            } else {
                                Toast.makeText(RegisterActivity.this, "¡Falló Registro!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

}
