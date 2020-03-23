package aletheia.alexandru.balan.intro.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import aletheia.alexandru.balan.R
import android.content.Context
import kotlinx.android.synthetic.main.fragment_intro1.*
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass.
 * Use the [Intro1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Intro1 : Fragment() {

    private var listener : OnButtonClickedListener? = null

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
         * @return A new instance of fragment Intro1.
         */
        @JvmStatic
        fun newInstance() = Intro1()

    }

    interface OnButtonClickedListener {
        fun onButton1Clicked()
    }
}
