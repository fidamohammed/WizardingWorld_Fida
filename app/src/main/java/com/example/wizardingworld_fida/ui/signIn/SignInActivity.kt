package com.example.wizardingworld_fida.ui.signIn

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wizardingworld_fida.ui.main.MainActivity
import com.example.wizardingworld_fida.R
import com.example.wizardingworld_fida.databinding.ActivitySignInBinding
import com.example.wizardingworld_fida.ui.signUp.SignUpActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var mAuth: FirebaseAuth

    private val RC_SIGN_IN = 100
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        binding = ActivitySignInBinding.inflate(layoutInflater)



//Authentication using Google SignIn
        val googleSignInButton = binding.googleSignIn
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        googleSignInButton.setOnClickListener {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, RC_SIGN_IN,
                            null, 0, 0, 0, null)

                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("SignIn", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener(this) { e ->
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d("SignIn", e.localizedMessage)
                }
        }


//Authentication using Email and Password
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
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                    else{
                        Toast.makeText(this,"Sign In Failed-> ${task.exception}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setContentView(binding.root)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == RC_SIGN_IN) {

                val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = googleCredential.googleIdToken

                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                        mAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignIn", "signInWithCredential:success")
                                    val user = mAuth.currentUser
                                    Toast.makeText(this,"Successfully Logged In",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finishAffinity()
                                    //updateUI(user)
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("SignIn", "signInWithCredential:failure", task.exception)
                                    Toast.makeText(this,"Sign In Failed-> ${task.exception}",Toast.LENGTH_SHORT).show()
                                   //updateUI(null)
                                }
                            }
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.d("SignIn", "No ID token!")
                    }
                }
            }

        } catch (exception: Exception) {
            Log.d("SignIn", "crashed  ${exception.localizedMessage}")
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}