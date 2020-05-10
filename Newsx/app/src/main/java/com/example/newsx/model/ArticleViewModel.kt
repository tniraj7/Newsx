package com.example.newsx.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.newsx.data.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    val articles by lazy { MutableLiveData<List<Article>>() }

    val loadingError by lazy { MutableLiveData<Boolean>() }

    val isLoading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    fun refresh() {
        isLoading.value = true
        initializeArticles()
    }


    private fun initializeArticles() {
        disposable.add(
            RetrofitServiceBuilder
                .buildService()
                .getTopHeadlines("in", API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<TopHeadlines>() {

                    override fun onSuccess(topHeadlines: TopHeadlines) {
                        loadingError.value = false
                        isLoading.value = false
                        articles.value = topHeadlines.articles
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loadingError.value = true
                        isLoading.value = false
                        articles.value = null
                    }
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}