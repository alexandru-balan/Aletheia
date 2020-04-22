package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        emailText = requireArguments()["emailText"] as String
        passwordText = requireArguments()["passwordText"] as String

        emailInputFragment = EmailInputFragment.newInstance(emailText)
        passwordInputFragment = PasswordInputFragment.newInstance(passwordText)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_inputs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.email_input, emailInputFragment)
        transaction.replace(R.id.password_input, passwordInputFragment)

        transaction.commit()
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
