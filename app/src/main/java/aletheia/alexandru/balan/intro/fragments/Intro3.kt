package aletheia.alexandru.balan.intro.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.SignupActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro3.*

class Intro3 : Fragment() {

    //private var listener : OnButtonClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intro3_button.setOnClickListener {
            val intent = Intent(activity, SignupActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    companion object {

        @JvmStatic
        fun newInstance() = Intro3()

    }

    interface OnButtonClickedListener {
        fun onButton3Clicked()
    }

}