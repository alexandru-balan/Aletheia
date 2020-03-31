package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_password_input.*

class PasswordInputFragment : Fragment() {

    private val passwordMatcher : Regex = Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#$%^&*()\\-_+={}\\[\\]|;:\"<>,./?]).{8,}")
    var validPassword : Boolean = false
    var password : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity?.localClassName == "auth.SignupActivity") {

            password_input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (validPassword) {
                        password_input.background =
                            activity?.getDrawable(R.drawable.rounded_button_valid)
                    } else {
                        password_input.background =
                            activity?.getDrawable(R.drawable.rounded_button_invalid)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    password_input.background =
                        activity?.getDrawable(R.drawable.rounded_button_background)
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val str = s.toString()
                    password = str
                    val warningText = activity?.findViewById<TextView>(R.id.warning_text)
                    warningText?.setTextColor(resources.getColor(R.color.warning_color, null))

                    validPassword = passwordMatcher matches str
                    if (!str.contains(Regex("[\\d]"))) {
                        warningText?.visibility = View.VISIBLE
                        warningText?.text = "At least on number is required"
                    } else if (!str.contains(Regex("[~`!@#$%^&*()\\-_+={}\\[\\]|;:\"<>,./?]"))) {
                        warningText?.visibility = View.VISIBLE
                        warningText?.text = "At least one special symbol is required"
                    } else if (!str.contains(Regex("[A-Z]"))) {
                        warningText?.visibility = View.VISIBLE
                        warningText?.text = "At least one upper case character is required"
                    } else if (!str.contains(Regex("[a-z]"))) {
                        warningText?.visibility = View.VISIBLE
                        warningText?.text = "At least one lower case character is required"
                    } else if (str.length < 8) {
                        warningText?.visibility = View.VISIBLE
                        warningText?.text = "The password must have at least 8 characters"
                    } else {
                        warningText?.visibility = View.GONE
                    }
                }
            })
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PasswordInputFragment()

    }

}