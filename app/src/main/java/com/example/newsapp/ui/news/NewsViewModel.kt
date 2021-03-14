package com.example.newsapp.ui.news

import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.newsapp.core.CoroutineContextProvider
import com.example.newsapp.data.paging.AllNewsDataSourceFactory
import com.example.newsapp.data.paging.PagingDataSource
import com.example.newsapp.data.paging.PagingState
import com.example.newsapp.data.paging.TopNewsDataSourceFactory
import com.example.newsapp.domain.news.Article
import com.example.newsapp.domain.news.toEntity
import com.example.newsapp.domain.repository.FavoriteNewsRepository
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val favoriteNewsRepository: FavoriteNewsRepository,
    private val coroutineContextProvider: CoroutineContextProvider,
    newsRepository: NewsRepository
) : ViewModel() {

    companion object {
        private const val DELAY_TIME = 5000L
    }

    private var job: Job? = null

    private val _selectedArticle = MutableLiveData<Article>()
    val selectedArticle: LiveData<Article>
        get() = _selectedArticle


    private val _allFavoriteNews = MutableLiveData<List<Article>>()
    val allFavoriteNews: LiveData<List<Article>>
        get() = _allFavoriteNews


    private val topNewsDataSourceFactory = TopNewsDataSourceFactory(newsRepository)
    val topNewsPagingState: LiveData<PagingState> =
        topNewsDataSourceFactory.sourceLiveData.switchMap {
            it.pagingState
        }
    val topNewsLiveData: LiveData<PagedList<Article>> =
        topNewsDataSourceFactory.toLiveData(PagingDataSource.config)

    private val allNewsDataSourceFactory = AllNewsDataSourceFactory(newsRepository)
    val allNewsPagingState = allNewsDataSourceFactory.sourceLiveData.switchMap {
        it.pagingState
    }
    val allNewsLiveData: LiveData<PagedList<Article>> =
        allNewsDataSourceFactory.toLiveData(PagingDataSource.config)


    fun fetchAllNews() = allNewsLiveData

    fun onRefreshAllNews() {
        allNewsDataSourceFactory.refresh()
    }

    fun startUpdates() {
        job = viewModelScope.launch {
            while (true) {
                topNewsDataSourceFactory.refresh()
                delay(DELAY_TIME)
            }
        }
    }

    fun stopUpdates() {
        job?.cancel()
        job = null
    }

    fun checkIfFavorite(article: Article) {
        viewModelScope.launch {
            val result = withContext(coroutineContextProvider.io) {
                favoriteNewsRepository.isFavorite(article.title)
            }
            if (result) {
                _selectedArticle.value = article.copy(isFavorite = true)
            } else {
                _selectedArticle.value = article
            }
        }
    }

    fun addToFavorites(article: Article) {
        viewModelScope.launch {
            withContext(coroutineContextProvider.io) {
                favoriteNewsRepository.insert(article.toEntity())
            }
        }
    }

    fun getAllFavoriteNews() {
        viewModelScope.launch {
            val result: List<Article> = withContext(coroutineContextProvider.io) {
                favoriteNewsRepository.getAllNews()
            }
            _allFavoriteNews.value = result
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

}