package com.ewind.newsapi.ui.component.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewind.newsapi.domain.model.DArticles
import com.ewind.newsapi.util.ext.*
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(val newsList: MutableList<DArticles>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var listener: AdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(com.ewind.newsapi.R.layout.item_news, false))
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.run { onBind(position) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {

            val news = newsList[position]

            itemView.iv_news_image.loadImageCenterCrop(news.urlToImage)
            itemView.tv_news_author.text = news.source?.name
            itemView.tv_news_title.text = news.title
            news.publishedAt?.let {
                itemView.tv_news_time.text = it.toDate(YYYY_MM_DDThh_mm_ssZ)?.time?.getTime()
            }

            itemView.setOnClickListener {
                listener?.onNewsSelected(newsList[adapterPosition])
            }
        }
    }

    fun clearDate() {
        newsList.clear()
        notifyDataSetChanged()
    }

    fun addNews(list: MutableList<DArticles>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onNewsSelected(news: DArticles)
    }
}