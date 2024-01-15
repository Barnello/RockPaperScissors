package com.example.rockpaperscissors
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{


    private lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        // Set click listener for the Google Sign-In button
        val btnGoogleSignIn = findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogleSignIn)
        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

    }
    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result != null) {
                handleSignInResult(result)
            }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            // Signed in successfully, handle the user information
            val account = result.signInAccount
            val displayName = account?.displayName
            val email = account?.email
            // Add your logic to proceed with signed-in user information
        } else {
            // Handle sign-in failure
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // Handle connection failure
    }
}