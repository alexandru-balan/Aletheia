package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {

    private lateinit var authInputsContainerView: FragmentContainerView
    private lateinit var authInputsFragment: AuthInputsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        authInputsContainerView = view.input_fields

        // Dynamically add the authentication input fields
        authInputsFragment = AuthInputsFragment.newInstance()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(authInputsContainerView.id, authInputsFragment).commit()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize button click handlers
        goto_login.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                authInputsContainerView to "authInputs"
            )
            val args = bundleOf(
                "emailText" to authInputsFragment.emailInputFragment.email,
                "passwordText" to authInputsFragment.passwordInputFragment.password
            )
            it.findNavController()
                .navigate(R.id.action_signup_fragment_to_login_fragment, args, null, extras)
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}
