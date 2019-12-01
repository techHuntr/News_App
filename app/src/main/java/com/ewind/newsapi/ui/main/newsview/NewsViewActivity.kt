package com.ewind.newsapi.ui.main.newsview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ewind.newsapi.R
import com.ewind.newsapi.domain.model.DArticles
import com.ewind.newsapi.ui.main.base.BaseActivity
import com.ewind.newsapi.util.ext.*
import kotlinx.android.synthetic.main.activity_news_view.*

const val EXTRA_NEWS = "extra_news"

class NewsViewActivity : BaseActivity() {

    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)

        supportActionBar?.apply {
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        val news = intent?.extras?.getParcelable<DArticles>(EXTRA_NEWS)
        news?.let {
            setData(it)
        }
    }

    private fun setData(news: DArticles) {
        iv_image.loadImageCenterCrop(news.urlToImage)
        tv_author.text = news.source?.name
        tv_title.text = news.title
        tv_description.text = news.description
        tv_content.text = news.content
        news.publishedAt?.let {
            tv_date.text = it.toDate(YYYY_MM_DDThh_mm_ssZ)?.toCustomDate(DD_MMM_YYYY_H_MM_A)
        }
        url = news.url
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ext_open_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_open -> {
                url?.let { url ->
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
