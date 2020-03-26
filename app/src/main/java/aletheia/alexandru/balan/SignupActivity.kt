package aletheia.alexandru.balan

import aletheia.alexandru.balan.auth.fragments.EmailInputFragment
import aletheia.alexandru.balan.auth.fragments.PasswordInputFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signup_activity_layout.*

class SignupActivity : FragmentActivity() {

    private lateinit var passwordInputFragment : PasswordInputFragment
    private lateinit var emailInputFragment: EmailInputFragment
    private lateinit var auth : FirebaseAuth
    private var TAG : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity_layout)

        // Initialising important variables
        passwordInputFragment = signup_passwordFragment as PasswordInputFragment
        emailInputFragment = signup_emailFragment as EmailInputFragment
        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
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
                        Log.i(TAG, "Successfully created user")
                        currentUser = auth.currentUser
                        // TODO: Redirect to app
                    }
                    else {
                        Log.w(TAG, "Could not create the user", task.exception)
                        Toast.makeText(baseContext, "Can't sign you up right now. Check internet connection!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
