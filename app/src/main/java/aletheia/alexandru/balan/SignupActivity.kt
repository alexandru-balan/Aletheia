package aletheia.alexandru.balan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.signup_activity_layout.*

class SignupActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity_layout)

        // Button Click Listeners
        goto_login.setOnClickListener {
            val intent = Intent (this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}
