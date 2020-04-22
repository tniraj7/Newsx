package com.example.newsx.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsx.R
import com.example.newsx.activity.WebViewActivity
import com.example.newsx.model.Article
import com.squareup.picasso.Picasso

class NewsArticleAdapter(private val context: Context,
                         private val articleList: ArrayList<Article>): RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.news_item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(articleList[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView?>(R.id.newsTitleId)
        var subtitle = itemView.findViewById<TextView?>(R.id.newsSubtitleId)
        var source = itemView.findViewById<TextView?>(R.id.sourceId)
        var imageView = itemView.findViewById<ImageView?>(R.id.newsImageView)

        fun bindItem(article: Article) {

            title?.text = article.title

            subtitle?.text = article.description
            source?.text = article.source?.name

            if (!TextUtils.isEmpty(article.urlToImage)) {
                Picasso.get()
                    .load(article.urlToImage)
                    .into(imageView)
            } else {
                Picasso.get()
                    .load(R.drawable.sample_news_image)
                    .into(imageView)
            }
            itemView.setOnClickListener {
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("link", article.url.toString())
                context.startActivity(intent)
            }
        }
    }
}