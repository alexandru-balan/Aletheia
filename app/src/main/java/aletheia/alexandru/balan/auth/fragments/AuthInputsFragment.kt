package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_auth_inputs.*

/**
 * A simple [Fragment] subclass.
 * Use the [AuthInputsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AuthInputsFragment : Fragment() {

    lateinit var passwordInputFragment: PasswordInputFragment
    lateinit var emailInputFragment: EmailInputFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_inputs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordInputFragment = password_input as PasswordInputFragment
        emailInputFragment = email_input as EmailInputFragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters
         *
         * @return A new instance of fragment AuthInputsFragment.
         */
        @JvmStatic
        fun newInstance() = AuthInputsFragment()
    }
}
