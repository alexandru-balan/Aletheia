package aletheia.alexandru.balan.intro.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.auth.AuthActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro3.*

/**
 * This [Fragment] is the third slide of the intro activity.
 * Use [Intro3.newInstance] to get an instance of this fragment.
 */
class Intro3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This listener finishes the intro activity, so the user can't go back to it and starts
        // the authentication activity
        intro3_button.setOnClickListener {
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = Intro3()

    }

}