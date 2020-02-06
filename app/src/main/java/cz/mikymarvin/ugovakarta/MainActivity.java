package cz.mikymarvin.ugovakarta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ;

    EditText email, password, password2, name, ugocard;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.regName);
        ugocard = findViewById(R.id.ugoId);
        email = findViewById(R.id.regMail);
        password = findViewById(R.id.regPass);
        password2 = findViewById(R.id.regPass2);
        btnSignUp = findViewById(R.id.regBtn);
        tvSignIn = findViewById(R.id.textView);

        prefs = getApplicationContext().getSharedPreferences("ugo", 0);
        editor = prefs.edit();

        btnSignUp.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 final String nameID = name.getText().toString();
                 String emailID = email.getText().toString();
                 final String ugocardID = ugocard.getText().toString();
                 String pwd = password.getText().toString();
                 String pwd2 = password2.getText().toString();


                 if(nameID.isEmpty()){
                     name.setError("Takhle že se jmenuješ?");
                     name.requestFocus();
                 }

                 else  if(ugocardID.isEmpty()){
                     ugocard.setError("Zadej číslo ze své UGO karty.");
                     ugocard.requestFocus();

                 }

                 else if(emailID.isEmpty()){
                     email.setError("Ups... Tohle není asi email. ");
                     email.requestFocus();
                 }
                 else  if(pwd.isEmpty()){
                     password.setError("Bezpečnější heslo by nebylo?");
                     password.requestFocus();

                 }
                 else  if(!pwd.equals(pwd2)){
                     password2.setError("Tohle se ale neshoduje.");
                     password2.requestFocus();







                 }
                 else  if(emailID.isEmpty() && pwd.isEmpty() && ugocardID.isEmpty() && !pwd.equals(pwd2)&& nameID.isEmpty()){
                     Toast.makeText(MainActivity.this,"Všechna pole jsou povinná.",Toast.LENGTH_SHORT).show();
                 }

                 else  if(!(emailID.isEmpty() && pwd.isEmpty())){
                     mFirebaseAuth.createUserWithEmailAndPassword(emailID, pwd)
                             .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(!task.isSuccessful()){
                                 Toast.makeText(MainActivity.this,"Sper to ďas! Něco se pokazilo. Zkus to znovu.",Toast.LENGTH_SHORT).show();
                             }
                             else {
                                 FirebaseUser fbuser = task.getResult().getUser();
                                 User user = new User();
                                 user.ugocard = ugocardID;
                                 user.name = nameID;
                                 String uid = fbuser.getUid();
                                 final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                 DatabaseReference ref = database.getReference();
                                 ref.child("users").child(uid).setValue(user);
                                 editor.putString("ugoCard", ugocardID);
                                 editor.putString("name", nameID);
                                 editor.apply();

                                 startActivity(new Intent(MainActivity.this, WelcomeSlide.class));
                             }
                         }
                     });
                 }
                 else{
                     Toast.makeText(MainActivity.this,"Fatální chyba. Fuck! ",Toast.LENGTH_SHORT).show();

                 }
             }
         }


        );



        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });
    }


}
