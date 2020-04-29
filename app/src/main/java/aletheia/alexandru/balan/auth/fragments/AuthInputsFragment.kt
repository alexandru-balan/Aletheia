package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.playground.HomeActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
import kotlinx.android.synthetic.main.fragment_auth_inputs.*
import kotlinx.android.synthetic.main.fragment_auth_inputs.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [AuthInputsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthInputsFragment : Fragment() {

    lateinit var passwordInputFragment: PasswordInputFragment
    lateinit var emailInputFragment: EmailInputFragment
    private lateinit var emailText: String
    private lateinit var passwordText: String

    private val logTag: String = "Authentication-LOG:"

    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private val signInRequestCode: Int = 1
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        emailText = requireArguments()["emailText"] as String
        passwordText = requireArguments()["passwordText"] as String

        emailInputFragment = EmailInputFragment.newInstance(emailText)
        passwordInputFragment = PasswordInputFragment.newInstance(passwordText)

        // Initialise authentication frameworks
        auth = FirebaseAuth.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, googleSignInOptions) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_auth_inputs, container, false)

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(view.email_input.id, emailInputFragment)
        transaction.replace(view.password_input.id, passwordInputFragment)
        transaction.commit()

        if (parentFragment?.javaClass?.name == "aletheia.alexandru.balan.auth.fragments.LoginFragment") {
            view.auth_button.text = getString(R.string.log_in)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (parentFragment?.javaClass?.name == "aletheia.alexandru.balan.auth.fragments.SignupFragment") {
            auth_button.setOnClickListener {
                if (passwordInputFragment.validPassword && emailInputFragment.validEmail) {
                    activity?.let { it1 ->
                        auth.createUserWithEmailAndPassword(
                            emailInputFragment.email,
                            passwordInputFragment.password
                        ).addOnCompleteListener(it1) { task ->
                            if (task.isSuccessful) {
                                Log.i(logTag, "Successfully created user")
                                user = auth.currentUser!!

                                // Go to home activity
                                val intent = Intent(context, HomeActivity::class.java)
                                val args = bundleOf("user" to user)
                                startActivity(intent, args)
                                it1.finish()
                            } else {
                                Log.w(logTag, "Could not create the user", task.exception)
                                Toast.makeText(
                                    context,
                                    "Can't sign you up right now. Check internet connection!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        } else {
            auth_button.setOnClickListener {
                activity?.let { activity ->
                    auth.signInWithEmailAndPassword(
                        emailInputFragment.email,
                        passwordInputFragment.password
                    )
                        .addOnCompleteListener(activity) { task ->
                            if (task.isSuccessful) {
                                Log.i(logTag, "Authentication successful")
                                user = auth.currentUser!!

                                // Go to home activity
                                val intent = Intent(context, HomeActivity::class.java)
                                val args = bundleOf("user" to user)
                                startActivity(intent, args)
                                activity.finish()
                            } else {
                                Log.e(logTag, "Authentication failure")
                                Toast.makeText(
                                    context,
                                    "Authentication failed! Check network and try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
        }

        // Google Button
        auth_google.setOnClickListener {
            signInWithGoogle()
        }

        //Facebook Button
        auth_facebook.setPermissions("email", "public_profile")
        auth_facebook.fragment = this
        custom_facebook_button.setOnClickListener { auth_facebook.performClick() }
        auth_facebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.i(logTag, "Facebook Auth successful")
                if (result != null) {
                    firebaseAuthWithFacebook(result.accessToken)
                } else {
                    Log.e(logTag, "Facebook Auth Failed")
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
        startActivityForResult(signInIntent, signInRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == signInRequestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(logTag, "Google sign in failed", e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        activity?.let {
            auth.signInWithCredential(credential).addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {
                    Log.i(logTag, "Google auth success")
                    user = auth.currentUser!!

                    // Go to home activity
                    val intent = Intent(context, HomeActivity::class.java)
                    val args = bundleOf("user" to user)
                    startActivity(intent, args)
                    it.finish()
                } else {
                    Log.w(logTag, "Google auth fail", task.exception)
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        activity?.let {
            auth.signInWithCredential(credential).addOnCompleteListener(it) { task ->
                if (task.isSuccessful) {
                    Log.i(logTag, "Facebook auth successful")
                    user = auth.currentUser!!

                    // Go to home activity
                    val intent = Intent(context, HomeActivity::class.java)
                    val args = bundleOf("user" to user)
                    startActivity(intent, args)
                    it.finish()
                } else {
                    Log.w(logTag, "Facebook auth failure", task.exception)
                    Toast.makeText(
                        context,
                        "Can't connect with facebook. Check internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters
         *
         * @return A new instance of fragment AuthInputsFragment.
         */
        @JvmStatic
        fun newInstance(emailText: String = "", passwordText: String = "") =
            AuthInputsFragment().apply {
                arguments = bundleOf(
                    "emailText" to emailText,
                    "passwordText" to passwordText
                )
            }
    }
}
