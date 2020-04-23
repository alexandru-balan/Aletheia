package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var emailText: String
    private lateinit var passwordText: String
    private lateinit var authInputsContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Transition management
        enterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
        exitTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_left)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        // Arguments from SignupFragment
        emailText = requireArguments()["emailText"] as String
        passwordText = requireArguments()["passwordText"] as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        authInputsContainerView = view.input_fields

        val authInputsFragment = AuthInputsFragment.newInstance(emailText, passwordText)

        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(authInputsContainerView.id, authInputsFragment).commit()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LoginFragment.
         */
        @JvmStatic
        fun newInstance(emailText: String = "") = LoginFragment()
    }
}
