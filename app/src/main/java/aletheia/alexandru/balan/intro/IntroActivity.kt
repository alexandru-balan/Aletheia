package aletheia.alexandru.balan.intro

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.intro.fragments.Intro1
import aletheia.alexandru.balan.intro.fragments.Intro2
import aletheia.alexandru.balan.intro.fragments.Intro3
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/***
 * This activity will be started only once and serves as a carousel of introductory fragments
 * that tell the user what the app is about in a nutshell before letting him use the app for
 * the first time.
 */
class IntroActivity : FragmentActivity(), Intro1.OnButtonClickedListener,
    Intro2.OnButtonClickedListener {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.intro_activity)

        // Instantiate a ViewPager2 and a PagerAdapter
        viewPager = findViewById(R.id.pager)
        val pagerAdapter = IntroPagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    override fun onButton1Clicked() { // Transition to second introductory slide
        viewPager.currentItem++
    }

    override fun onButton2Clicked() { // Transition to final introductory slide
        viewPager.currentItem++
    }

    /***
     * The method defines the behaviour of the back button during the intro screen.
     */
    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            // If the the intro is not on the first screen then don't exit app, go back one page
            // in the intro hierarchy
            viewPager.currentItem--
        }
    }

    private inner class IntroPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private var fragments: List<Fragment> =
            listOf(Intro1.newInstance(), Intro2.newInstance(), Intro3.newInstance())

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

}