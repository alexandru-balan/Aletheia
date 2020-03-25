package aletheia.alexandru.balan

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.login_activity_layout.*

class LogInActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity_layout)

        back_button.setOnClickListener {
            super.onBackPressed()
        }
    }

}