package com.example.wizardingworld_fida.ui.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.wizardingworld_fida.MainActivity
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.databinding.ActivitySignInBinding
import com.example.wizardingworld_fida.ui.signUp.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        binding = ActivitySignInBinding.inflate(layoutInflater)

        val emailField = binding.userNameSignIn

        val passwordField = binding.userPasswordSignIn

        val signUpButton = binding.signInButton
        val register = binding.tvRegisterHere

        register.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        signUpButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show()
            }
            else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else{
                        Toast.makeText(this,"Sign In Failed-> ${task.exception}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setContentView(binding.root)
    }
}