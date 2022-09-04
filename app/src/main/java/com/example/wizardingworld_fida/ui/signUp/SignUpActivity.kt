package com.example.wizardingworld_fida.ui.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.databinding.ActivitySignUpBinding
import com.example.wizardingworld_fida.ui.signIn.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        val emailField = binding.userName

        val passwordField = binding.userPassword

        val signUpButton = binding.signUpButton
        val login = binding.tvLoginHere

        login.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }

        mAuth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            if(TextUtils.isEmpty(email)){
                Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show()
            }
            else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,SignInActivity::class.java))
                    }
                    else{
                        Toast.makeText(this,"Registration Failed \n ${task.exception}",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }


        setContentView(binding.root)
    }


}