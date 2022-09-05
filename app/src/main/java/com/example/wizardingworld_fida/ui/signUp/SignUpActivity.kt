package com.example.wizardingworld_fida.ui.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.wizardingworld_fida.MainActivity
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.databinding.ActivitySignUpBinding
import com.example.wizardingworld_fida.ui.signIn.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        val nameField = binding.userName
        val emailField = binding.userEmail
        val passwordField = binding.userPassword

        val signUpButton = binding.signUpButton
        val login = binding.tvLoginHere

        login.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }

        mAuth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            val name = nameField.text.toString().trim()
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
                        val user = task.result.user
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name
                        }
                        user!!.updateProfile(profileUpdates).addOnCompleteListener { nameTask->
                            if(nameTask.isSuccessful){
                                Log.d("name","Name Saved")
                                //Toast.makeText(this,"Name Saved",Toast.LENGTH_SHORT).show()
                            }
                        }
                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task->
                            if(task.isSuccessful){
                                Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()
                            }
                            else{
                                Toast.makeText(this,"Sign In Failed-> ${task.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                        //startActivity(Intent(this,SignInActivity::class.java))
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