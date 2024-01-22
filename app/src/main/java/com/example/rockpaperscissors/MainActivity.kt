package com.example.rockpaperscissors
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener{

    private lateinit var  accessStatusText: TextView;
    private lateinit var continueButton: Button
    private lateinit var accountStatusNoti: TextView
    private lateinit var loginText: TextView
    private var clicked: Int = 2
    private lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accessStatusText = findViewById<TextView>(R.id.textCreateAccount)
        continueButton = findViewById<Button>(R.id.btnContinue)
        accountStatusNoti = findViewById<TextView>(R.id.textAlreadyHaveAccount)
        loginText = findViewById<TextView>(R.id.textLogIn)
        createSpan(loginText.text.length)

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

    @SuppressLint("SetTextI18n")
    private fun hasAccount(){
        println("What is happakshdfkjahsdflkjhlaksjhdf")
        accessStatusText.text = "Sign In";
        accountStatusNoti.text = "Need to create an account? "
        continueButton.text = "Sign In"
        loginText.text = "Create Account"

        clicked += 1

        createSpan(loginText.text.length)

        if(clicked % 2 == 0){
            accessStatusText.text = "Create an Account";
            accountStatusNoti.text = "Already have an account? "
            continueButton.text = "Continue"
            loginText.text = "Log in"

            createSpan(loginText.text.length)

        }
    }

    ///Used only to create a clickable text for the loginText textview
    private fun createSpan(endNum: Int){
        val spanString = SpannableStringBuilder(loginText.text)

        val loginTextClicked = object : ClickableSpan() {
            override fun onClick(widget: View){
                hasAccount();
            }
        }
        spanString.setSpan(loginTextClicked, 0, endNum, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        loginText.text = spanString
        loginText.movementMethod = LinkMovementMethod.getInstance()
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