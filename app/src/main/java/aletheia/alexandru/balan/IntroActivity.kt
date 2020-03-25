package aletheia.alexandru.balan

import aletheia.alexandru.balan.intro.fragments.Intro1
import aletheia.alexandru.balan.intro.fragments.Intro2
import aletheia.alexandru.balan.intro.fragments.Intro3
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class IntroActivity : FragmentActivity(), Intro1.OnButtonClickedListener, Intro2.OnButtonClickedListener {

    private val numberOfPages = 3
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instantiate a ViewPager2 and a PagerAdapter
        setContentView(R.layout.intro_activity)
        viewPager = findViewById(R.id.pager)
        val pagerAdapter = IntroPagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    override fun onButton1Clicked() {
        viewPager.currentItem++
    }

    override fun onButton2Clicked() {
        viewPager.currentItem++
    }

    /***
     * The method defines the behaviour of the back button during the intro screen
     */
    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        }
        else {
            // If the the intro is not on the first screen then don't exit app, go back on page in the intro
            viewPager.currentItem--
        }
    }
    private inner class IntroPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        private var fragments : List<Fragment> =
            listOf(Intro1.newInstance(), Intro2.newInstance(), Intro3.newInstance())

        override fun getItemCount(): Int {
            return numberOfPages
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

}