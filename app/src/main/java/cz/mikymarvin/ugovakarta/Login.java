package cz.mikymarvin.ugovakarta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getApplicationContext().getSharedPreferences("ugo", 0);
        editor = prefs.edit();

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){



                    String uid = mFirebaseAuth.getUid();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = database.child("users").child(uid);
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            editor.putString("ugoCard", user.ugocard);
                            editor.putString("name", user.name);
                            editor.apply();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    ref.addListenerForSingleValueEvent(postListener);
                    Intent i = new Intent(Login.this, Home.class);
                    startActivity(i);




                    //tohle někdy padá, tak bacha. Když nepůjde login, bude to tímto.
                    ///zobrazuje fragment na kartě


                }

            }


        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()){
                    emailId.setError("Usp... tento email je vyplněný špatně. ");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Usp... toto heslo je vyplněné špatně. ");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Login.this,"Tato pole jsou povinná!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {



                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this,"Sper to ďas! Tyto údaje nepoznávám. ",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final Intent intToHome = new Intent(Login.this,Home.class);


                                String uid = mFirebaseAuth.getUid();
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref = database.child("users").child(uid);
                                ValueEventListener postListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        editor.putString("ugoCard", user.ugocard);
                                        editor.putString("name", user.name);
                                        editor.apply();
                                        startActivity(intToHome);


                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.w("loadPost:onCancelled", databaseError.toException());
                                    }
                                };
                                ref.addListenerForSingleValueEvent(postListener);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this,"Error Occurred!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(Login.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}



