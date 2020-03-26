package aletheia.alexandru.balan.auth.fragments

import aletheia.alexandru.balan.R
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_password_input.*

class PasswordInputFragment : Fragment() {

    private val passwordMatcher : Regex = Regex("(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#$%^&*()\\-_+={}\\[\\]|;:\"<>,./?]).{8,}")
    private var validPassword : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
                if (validPassword) {
                    password_input.background = activity?.getDrawable(R.drawable.rounded_button_valid)
                }
                else {
                    password_input.background = activity?.getDrawable(R.drawable.rounded_button_invalid)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                password_input.background = activity?.getDrawable(R.drawable.rounded_button_background)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validPassword = passwordMatcher matches s.toString()
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = PasswordInputFragment()

    }

}