package com.example.warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Allows any user to create an account
 */
public class RegisterActivity extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    Button mRegisterButton;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
		/**
         * Ensures that the entered values are valid. Such as the email section contains an actual email
	* @param savedInstanceState
	**/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connect to xml resources
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterButton = findViewById(R.id.loginButton);
        mLoginBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        // will send the user to the main activity once the user is registered instead of making them login after registering
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Checks that the user's input is valid
	        * @param v
	        **/
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // Event Handling in the case that their is nothing in text fields
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please Enter an Email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please Enter a Password");
                    return;
                }

                // Event handling for password requirement
                if(password.length() < 6){
                    mPassword.setError("Password must be at least 6 characters long.");
                    return;
                }

                // Reveal the progress bar to let the user know that the app is working and not frozen
                progressBar.setVisibility(View.VISIBLE);

                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    /**
                     * Lets the user know if the account was created successfully
	                * @param task
	                **/
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error has occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // if the user is already registered then this will send them to the login screen
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Calls the Login class
	        * @param v is the button that was clicked
	        **/
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}