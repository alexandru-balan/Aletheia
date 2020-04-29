package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_email_input.*

class EmailInputFragment : Fragment() {

    var validEmail: Boolean = false
    var email : String = ""
    private lateinit var receivedEmailText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        receivedEmailText = requireArguments()["emailText"] as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_email_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_input.setText(receivedEmailText)
        email = receivedEmailText

        email_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (validEmail and onSignup()) {
                    email_input.background =
                        activity?.getDrawable(R.drawable.rounded_button_valid)
                } else if (onSignup()) {
                    email_input.background =
                        activity?.getDrawable(R.drawable.rounded_button_invalid)
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                email_input.background =
                    activity?.getDrawable(R.drawable.rounded_button_background)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validEmail = Regex(Patterns.EMAIL_ADDRESS.toString()) matches s.toString()
                email = s.toString()
            }

        })

    }

    private fun onSignup(): Boolean {
        return parentFragment?.parentFragment?.javaClass?.name == "aletheia.alexandru.balan.auth.fragments.SignupFragment"
    }

    companion object {

        @JvmStatic
        fun newInstance(emailText: String = "") = EmailInputFragment().apply {
            arguments = bundleOf(
                "emailText" to emailText
            )
        }

    }

}