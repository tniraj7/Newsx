package com.example.newsx.data

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsx.R
import com.example.newsx.activity.WebViewActivity
import com.example.newsx.databinding.NewsItemRowBinding
import com.example.newsx.model.Article

class NewsArticleAdapter(
    private val context: Context,
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<NewsArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        var inflater = LayoutInflater.from(context)

        val view = DataBindingUtil.inflate<NewsItemRowBinding>(
            inflater,
            R.layout.news_item_row,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        holder.bind(article)
    }

    inner class ViewHolder(val view: NewsItemRowBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(article: Article) {
            view.article = article
            view.source = article.source

            view.cardView.setOnClickListener {
                // Explicit Intent
                val intent = Intent(context, WebViewActivity::class.java)
                intent.putExtra("link", article.url.toString())
                context.startActivity(intent)

               /*  Implicit Intent which opens any capable app from options upon user selection.

                val webPage  = Uri.parse(article.url.toString())
                val intent = Intent(Intent.ACTION_VIEW, webPage)

                var pm = context.packageManager
                if (intent.resolveActivity(pm) != null) {
                    context.startActivity(Intent.createChooser(intent, "Choose browser .."))
                } */
            }

        }
    }

    fun updateArticleList(newArticleList: List<Article>) {
        articleList.clear()
        articleList.addAll(newArticleList)
        notifyDataSetChanged()
    }
}
