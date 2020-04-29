package aletheia.alexandru.balan.playground.fragments

import aletheia.alexandru.balan.R
import aletheia.alexandru.balan.playground.adapters.ArticleAdapter
import aletheia.alexandru.balan.playground.viewmodels.NewsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    private lateinit var adapter: ArticleAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        viewModel =
            NewsViewModel(requireActivity().application)

        viewModel.getArticles().observe(viewLifecycleOwner, Observer { channel ->
            if (channel != null) {
                /*if (channel.title != null) {
                    article_headline.text = channel.title
                }*/
                adapter =
                    ArticleAdapter(
                        channel.articles
                    )
                view.recycler_view.adapter = adapter
                view.recycler_view.layoutManager = LinearLayoutManager(activity)
                view.recycler_view.itemAnimator = DefaultItemAnimator()
                adapter.notifyDataSetChanged()
                view.swipe_layout.isRefreshing = false
            }

        })

        view.swipe_layout.setColorSchemeResources(R.color.brand_color, R.color.brand_color_dark)
        view.swipe_layout.canChildScrollUp()
        view.swipe_layout.setOnRefreshListener {
            adapter.articles.clear()
            adapter.notifyDataSetChanged()
            view.swipe_layout.isRefreshing = true
            CoroutineScope(Dispatchers.Main).launch { viewModel.fetch() }
        }

        CoroutineScope(Dispatchers.Main).launch { viewModel.fetch() }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment()

    }
}
