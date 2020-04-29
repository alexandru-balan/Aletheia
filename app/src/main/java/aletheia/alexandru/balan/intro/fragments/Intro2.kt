package aletheia.alexandru.balan.intro.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.intro.fragments.Intro2.OnButtonClickedListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro2.*

/**
 * This [Fragment] is the second slide of the intro activity.
 * Use [Intro2.newInstance] to get an instance of this fragment.
 *
 * Communication between this fragment and the IntroActivity is done through the interface
 * [OnButtonClickedListener]
 */
class Intro2 : Fragment() {
    private var listener : OnButtonClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intro2_button.setOnClickListener {
            listener?.onButton2Clicked()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as? OnButtonClickedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnButton1Clicked method")
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = Intro2()

    }

    interface OnButtonClickedListener {
        fun onButton2Clicked()
    }

}