package com.example.warehouse;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterActivityTest {
    EditText mFullName, mEmail, mPassword;
    Button mRegisterButton;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Test
    public void testing()
    {
      String name = "Bob Bob";
      String email = "bob@gmail.com";
      String password = "password";


      assertEquals("Bob Bob", name);
      assertEquals("bob@gmail.com", email);
      assertEquals("password", password);
    }



}