package com.ewind.newsapi.ui.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ewind.newsapi.R
import com.ewind.newsapi.domain.model.Category
import com.ewind.newsapi.domain.model.DArticles
import com.ewind.newsapi.ui.component.adapter.CategoryAdapter
import com.ewind.newsapi.ui.component.adapter.NewsAdapter
import com.ewind.newsapi.ui.main.base.BaseFragment
import com.ewind.newsapi.ui.main.newsview.EXTRA_NEWS
import com.ewind.newsapi.ui.main.newsview.NewsViewActivity
import com.ewind.newsapi.util.Msg
import com.ewind.newsapi.util.PaginationScrollListener
import com.ewind.newsapi.util.Resource
import com.ewind.newsapi.util.ResourceState
import com.ewind.newsapi.util.ext.*
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment(), CategoryAdapter.AdapterListener, NewsAdapter.AdapterListener {

    private val newsViewModel by viewModel<NewsViewModel>()
    private lateinit var adapterPref: CategoryAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel.newsliveDate.observe(this, Observer { updateView(it) })
        newsViewModel.livedataPre.observe(this, Observer { populate(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_news, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterPref = CategoryAdapter(mutableListOf())
        adapterPref.listener = this
        rv_category.adapter = adapterPref

        newsAdapter = NewsAdapter(mutableListOf())
        newsAdapter.listener = this
        rv_top_news.adapter = newsAdapter
        rv_top_news.addOnScrollListener(object :
            PaginationScrollListener(rv_top_news.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                newsViewModel.currentPage++
                getNews()
            }

            override fun isLastPage(): Boolean {
                return if (newsViewModel.totalCount != null) {
                    newsAdapter.itemCount >= newsViewModel.totalCount!!
                } else {
                    false
                }
            }

            override fun isLoading(): Boolean {
                return newsViewModel.isLoading
            }

        })

        pull_refresh.setOnRefreshListener {
            refreshData()
            getNews()
        }

        getNews()
        newsViewModel.preferenceAll()
    }

    private fun getNews() {
        context?.withNetwork(
            {
                newsViewModel.getNews()
            }
            , {
                Msg.INTERNET_ISSUE.showToast(context!!)
            }
        )
    }

    private fun refreshData() {
        newsViewModel.currentPage = 1
        newsAdapter.clearDate()
    }

    private fun updateView(resource: Resource<MutableList<DArticles>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                    pull_refresh.startRefresh()
                }
                ResourceState.SUCCESS -> {
                    pull_refresh.stopRefresh()
                    it.data?.let { it1 ->
                        newsAdapter.addNews(it1)
                    }
                }
                ResourceState.ERROR -> {
                    pull_refresh.stopRefresh()
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun populate(resource: Resource<List<Category>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> {
                }
                ResourceState.SUCCESS -> {
                    pull_refresh.stopRefresh()
                    it.data?.toMutableList()?.let { it1 ->
                        if (it1.isNotEmpty()) {
                            it1[0].selected = true
                        }
                        adapterPref.addCategory(it1)
                    }
                }
                ResourceState.ERROR -> {
                }
            }
        }
    }

    override fun onCategorySelected(category: Category) {
        refreshData()
        category.key?.let {
            newsViewModel.keyword = category.key
        }
        getNews()
    }

    override fun onNewsSelected(news: DArticles) {
        context?.startActivity<NewsViewActivity> {
            putExtra(EXTRA_NEWS, news)
        }
    }
}