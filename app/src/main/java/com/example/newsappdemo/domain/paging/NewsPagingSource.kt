package com.example.newsappdemo.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappdemo.BuildConfig
import com.example.newsappdemo.data.models.Article
import com.example.newsappdemo.data.remote.NewsApiService
import com.example.newsappdemo.domain.util.Constants.IMAGE_REQUEST
import com.example.newsappdemo.domain.util.Constants.STARTING_PAGE_NUMBER
import com.example.newsappdemo.domain.util.Constants.STARTING_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val apiService: NewsApiService,
    private val q: String,
    private val lang: String
): PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_NUMBER
            val response = apiService.getNews(
                app_key = BuildConfig.APP_KEY,
                q = q,
                page = pageNumber,
                page_size = STARTING_PAGE_SIZE,
                lang = lang,
                media = IMAGE_REQUEST
            )
            val prevKey = if (pageNumber == STARTING_PAGE_NUMBER) null else pageNumber - 1
            val nextKey = if (response.articles.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response.articles,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}