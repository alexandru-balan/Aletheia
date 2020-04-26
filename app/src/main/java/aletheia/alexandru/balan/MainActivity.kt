package aletheia.alexandru.balan

import aletheia.alexandru.balan.auth.AuthActivity
import aletheia.alexandru.balan.intro.IntroActivity
import aletheia.alexandru.balan.playground.HomeActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        // Show intro only once
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val shown = sharedPreferences.getBoolean("introShown", false)

        if (shown) {

            val intent: Intent

            val user = auth.currentUser
            // If the user is also logged in then send him to Home
            if (user != null) {
                intent = Intent(this, HomeActivity::class.java)
                val args = bundleOf("user" to user)
                startActivity(intent, args)
            } else {
                intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
            finish()
        } else { // If tutorial was never shown then the user never logged in
            with(sharedPreferences.edit()) {
                putBoolean("introShown", true)
                commit()
            }
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
