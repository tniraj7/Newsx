package com.example.newsx.activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.newsx.data.FINAL_API_URL
import com.example.newsx.data.NewsArticleAdapter
import com.example.newsx.model.Article
import com.example.newsx.model.Source
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject



class MainActivity : AppCompatActivity() {

    val LOG_TAG = "MainActivity"
    var articles: ArrayList<Article>? = null
    var adapter: NewsArticleAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.newsx.R.layout.activity_main)

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                com.example.newsx.R.color.colorPrimary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemsswipetorefresh.setOnRefreshListener {
            Log.i(LOG_TAG, "Began refreshing..")

            initializeArticles(this)
            layoutManager = LinearLayoutManager(this)

            adapter = articles?.let {
                NewsArticleAdapter(this, it)
            }
            adapter?.notifyDataSetChanged()
            itemsswipetorefresh.isRefreshing = false
        }

        articles =  ArrayList<Article>()

        initializeArticles(this)

    }

    override fun onResume() {
        super.onResume()
        initializeArticles(this)
        adapter?.notifyDataSetChanged()
    }

    fun initializeArticles(context: Context) {

        var volleyRequest: RequestQueue? = Volley.newRequestQueue(context)

        val jsonObjReq = JsonObjectRequest(
            Request.Method.GET,
            FINAL_API_URL,
            null,
            Response.Listener { response: JSONObject ->
                try {
                    var articleList = response.getJSONArray("articles")
                    for (i in 0..articleList.length() - 1) {
                        var article = articleList.getJSONObject(i)

                        var sourceObj = article.getJSONObject("source")
                        var source = Source()
                        source.id = sourceObj.getString("id")
                        source.name = sourceObj.getString("name")

                        var newsArticle = Article()
                        newsArticle.author = article.getString("author")
                        newsArticle.content = article.getString("content")
                        newsArticle.description = article.getString("description")
                        newsArticle.publishedAt = article.getString("publishedAt")
                        newsArticle.title = article.getString("title")
                        newsArticle.url = article.getString("url")
                        newsArticle.urlToImage = article.getString("urlToImage")
                        newsArticle.source = source

                        Log.d("Title", newsArticle.title)
                        articles?.add(newsArticle)

                    }

                    adapter = articles?.let {
                        NewsArticleAdapter(this, it)
                    }

                    layoutManager = LinearLayoutManager(this)
                    newsRecyclerView.adapter = adapter
                    newsRecyclerView.layoutManager = layoutManager
                    adapter?.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }, Response.ErrorListener { error: VolleyError? ->
                try {
                    Log.d("Error", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyRequest?.add(jsonObjReq)
    }
}
