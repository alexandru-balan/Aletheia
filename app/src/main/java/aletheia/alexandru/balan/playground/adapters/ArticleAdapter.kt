package aletheia.alexandru.balan.playground.adapters

import aletheia.alexandru.balan.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_layout.view.*

class ArticleAdapter(val articles: MutableList<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(articles[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            itemView.article_headline.text = article.title

            Picasso.get()
                .load(article.image)
                .placeholder(R.drawable.placeholder)
                .into(itemView.thumbnail)
        }
    }
}