package aletheia.alexandru.balan.auth

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.auth.fragments.EmailInputFragment
import aletheia.alexandru.balan.auth.fragments.PasswordInputFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.signup_activity_layout.*

class SignupActivity : FragmentActivity() {

    private lateinit var passwordInputFragment : PasswordInputFragment
    private lateinit var emailInputFragment: EmailInputFragment
    private lateinit var user : FirebaseUser
    private lateinit var auth : FirebaseAuth
    private var logTag : String = "Aletheia"
    private lateinit var googleSignInOptions : GoogleSignInOptions
    lateinit var googleSignInClient : GoogleSignInClient
    private val signinRequestCode : Int = 1
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity_layout)

        // Initialising important variables
        passwordInputFragment = signup_passwordFragment as PasswordInputFragment
        emailInputFragment = signup_emailFragment as EmailInputFragment
        auth = FirebaseAuth.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //user = auth.currentUser!!
        /**
         * TODO: Implement checks to see if the user is already logged in and redirect directly to app
         * */

        // Button Click Listeners
        goto_login.setOnClickListener {
            val intent = Intent (this, LogInActivity::class.java)
            startActivity(intent)
        }

        signup_button.setOnClickListener {
            if (passwordInputFragment.validPassword && emailInputFragment.validEmail) {
                auth.createUserWithEmailAndPassword(
                    emailInputFragment.email,
                    passwordInputFragment.password
                ).addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        Log.i(logTag, "Successfully created user")
                        user = auth.currentUser!!
                        // TODO: Redirect to app
                    }
                    else {
                        Log.w(logTag, "Could not create the user", task.exception)
                        Toast.makeText(baseContext, "Can't sign you up right now. Check internet connection!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // Google Button
        signup_google.setOnClickListener {
            signInWithGoogle()
        }

        //Facebook Button
        signup_facebook.setPermissions("email", "public_profile")
        custom_facebook_button.setOnClickListener { signup_facebook.performClick() }
        signup_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.i (logTag, "Facebook Auth successful")
                if (result != null) {
                    firebaseAuthWithFacebook(result.accessToken)
                }
            }

            override fun onCancel() {
                Log.d(logTag, "Facebook Auth Canceled")
            }

            override fun onError(error: FacebookException?) {
                Log.w(logTag, "Facebook Auth error", error)
            }
        })
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, signinRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == signinRequestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }
            catch (e : ApiException) {
                Log.w(logTag, "Google sign in failed", e)
            }
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(account : GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            task ->
            if(task.isSuccessful) {
                Log.i (logTag, "Google auth success")
                user = auth.currentUser!!
            }
            else {
                Log.w(logTag, "Google auth fail", task.exception)
                Toast.makeText(baseContext, "Sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithFacebook(token : AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            task->
            if (task.isSuccessful) {
                Log.i(logTag, "Facebook auth successful")
                user = auth.currentUser!!
            }
            else {
                Log.w(logTag, "Facebook auth failure", task.exception)
                Toast.makeText(baseContext, "Can't connect with facebook. Check internet", Toast.LENGTH_LONG).show()
            }
        }
    }

}