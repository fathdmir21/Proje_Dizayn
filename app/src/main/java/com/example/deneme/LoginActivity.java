package com.example.deneme;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




    public class LoginActivity extends AppCompatActivity {
        private EditText etEmail,etPassword;
        private Button btLogin,btRegister;
        private FirebaseAuth auth;
        private FirebaseUser firebaseUser;

        boolean emailVerified;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            etEmail=(EditText)findViewById(R.id.etEmail);
            etPassword=(EditText)findViewById(R.id.etPassword);
            btLogin=(Button)findViewById(R.id.giris);
            auth=FirebaseAuth.getInstance();

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Kolay kullanmak için edittexlerdeki değerleri alıyoruz.
                    String email=etEmail.getText().toString();
                    String password=etPassword.getText().toString();

                    //örnek olabilecek giriş senaryoları biz basit bişeyler yaptık
                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(getApplicationContext(),"Lütfen emailinizi giriniz",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (!email.contains("@")) {
                        Toast.makeText(getApplicationContext(), "Lütfen geçerli bir mail adresi giriniz", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(password)){
                        Toast.makeText(getApplicationContext(),"Lütfen parolanızı giriniz",Toast.LENGTH_SHORT).show();
                    }
                    else if (password.length()<6){
                        Toast.makeText(getApplicationContext(),"Parola en az 6 haneli olmalıdır",Toast.LENGTH_SHORT).show();
                    }else {
                        //Firebase ile bağlantıyı kurup mail ve şifre doğrulaması yapılır ve sonra giriş yapılır veya yapılmaz
                        //Eğer işlem başarılı olursa task.isSuccessful true
                        auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //İşlem başarılı ise MainActivity'e gider
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Giriş Hatalı. Bilgilerinizi Kontrol Ediniz.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }

                }
            });
            TextView signUp_text = findViewById(R.id.signUp_text);
            signUp_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                    finish();
                }
            });
        }
    }


