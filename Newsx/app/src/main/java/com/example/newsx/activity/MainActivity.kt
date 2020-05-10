package com.example.newsx.activity

import android.app.PictureInPictureParams
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsx.R
import com.example.newsx.data.NewsArticleAdapter
import com.example.newsx.databinding.ActivityMainBinding
import com.example.newsx.model.Article
import com.example.newsx.model.ArticleViewModel

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var articleViewModel: ArticleViewModel
    private var adapter = NewsArticleAdapter(this, arrayListOf())

    private var articleListDataObserver = Observer<List<Article>> { list ->
        list?.let {
            binding.newsRecyclerView.visibility = View.VISIBLE
            adapter.updateArticleList(it)
        }
    }

    private var loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        binding.progressBar.visibility =  if (isLoading) View.VISIBLE else View.GONE

        if (isLoading) {

            binding.apply {
                textError.visibility = View.GONE
                newsRecyclerView.visibility = View.GONE
            }
        }
    }

    private var errorLiveDataObserver = Observer<Boolean> { hasError ->
        binding.textError.visibility = if (hasError) View.VISIBLE else View.GONE

        if (hasError) {

            binding.apply {
                progressBar.visibility = View.GONE
                newsRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupSwipeRefreshControl()

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        articleViewModel.articles.observe(this, articleListDataObserver)
        articleViewModel.loadingError.observe(this,errorLiveDataObserver)
        articleViewModel.isLoading.observe(this, loadingLiveDataObserver)

        articleViewModel.refresh()

        binding.apply {
            newsRecyclerView.adapter = adapter
            newsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    fun setupSwipeRefreshControl() {

        binding.apply {
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.DKGRAY)
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
            swipeRefreshLayout.setOnRefreshListener {
                Log.i(LOG_TAG, "Refreshing began..")

                textError.visibility = View.GONE
                newsRecyclerView.visibility = View.GONE
                progressBar.visibility = View.GONE


                articleViewModel.refresh()
                articleViewModel.articles.value?.let { list ->
                    adapter.updateArticleList(list)
                }
                adapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val display = windowManager.defaultDisplay
        val p = Point()
        display.getSize(p)

        val width = p.x
        val height = p.y

        val ratio = Rational(2, 1.1.toInt())
        val pip_Builder = PictureInPictureParams.Builder()
        pip_Builder.setAspectRatio(ratio).build()
        enterPictureInPictureMode(pip_Builder.build())
    }

}
