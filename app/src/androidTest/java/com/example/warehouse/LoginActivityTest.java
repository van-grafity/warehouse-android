package com.example.warehouse;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginActivityTest {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Test
    public void LoginTestMethod()
    {
        String input1 = "  a@gmail.com    ";
        String input2 = "  aaaaaa ";


        assertEquals("a@gmail.com", input1.trim());
        assertEquals("aaaaaa", input2.trim());

    }
}