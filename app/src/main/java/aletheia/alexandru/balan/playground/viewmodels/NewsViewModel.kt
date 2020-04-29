package aletheia.alexandru.balan.playground.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.coroutineScope

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val rssFeed: String = "http://rss.cnn.com/rss/cnn_allpolitics.rss"

    private lateinit var articles: MutableLiveData<Channel>

    fun getArticles(): MutableLiveData<Channel> {
        if (!::articles.isInitialized) {
            articles = MutableLiveData()
        }
        return articles
    }

    private fun setChannel(channel: Channel) {
        articles.postValue(channel)
    }

    suspend fun fetch() {
        coroutineScope {
            try {
                val parser = Parser()
                val articleList = parser.getChannel(rssFeed)
                setChannel(articleList)
            } catch (e: Exception) {
                Log.e("News", e.message!!)
                Toast.makeText(getApplication(), "Couldn't fetch the news", Toast.LENGTH_SHORT)
                    .show()
                setChannel(Channel(null, null, null, null, null, null, mutableListOf()))
            }
        }
    }
}