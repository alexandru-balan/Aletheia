package aletheia.alexandru.balan.intro.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.intro.fragments.Intro1.OnButtonClickedListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro1.*

/**
 * This [Fragment] is the first slide of the intro activity.
 * Use [Intro1.newInstance] to get an instance of this fragment.
 *
 * Communication between this fragment and the IntroActivity is done through the interface
 * [OnButtonClickedListener]
 */
class Intro1 : Fragment() {
    // The instance of the implemented listener from IntroActivity
    private var listener: OnButtonClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intro1_button.setOnClickListener {
            listener?.onButton1Clicked()
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of Intro1.
         */
        @JvmStatic
        fun newInstance() = Intro1()

    }

    interface OnButtonClickedListener {
        fun onButton1Clicked()
    }
}
