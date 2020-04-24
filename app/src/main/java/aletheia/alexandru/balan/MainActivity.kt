package aletheia.alexandru.balan

import aletheia.alexandru.balan.auth.AuthActivity
import aletheia.alexandru.balan.intro.IntroActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show intro only once
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val shown = sharedPreferences.getBoolean("introShown", false)

        if (shown) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        } else {
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
