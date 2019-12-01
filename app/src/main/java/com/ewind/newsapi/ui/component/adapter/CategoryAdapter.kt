package com.ewind.newsapi.ui.component.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ewind.newsapi.R
import com.ewind.newsapi.domain.model.Category
import com.ewind.newsapi.util.ext.getCompatColor
import com.ewind.newsapi.util.ext.getCompatColorState
import com.ewind.newsapi.util.ext.inflate
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(val categoryList: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var listener: AdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_category, false))
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.run { onBind(position) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun onBind(position: Int) {
            val category = categoryList[position]
            itemView.tv_category.text = category.key
            if (category.selected) {
                itemView.tv_category.setTextColor(itemView.context.getCompatColor(R.color.colorWhite))
                itemView.tv_category.backgroundTintList =
                    itemView.context.getCompatColorState(R.color.colorPurple)
            } else {
                itemView.tv_category.setTextColor(itemView.context.getCompatColor(R.color.colorGraphite))
                itemView.tv_category.backgroundTintList =
                    itemView.context.getCompatColorState(R.color.colorWhite)
            }

            itemView.tv_category.setOnClickListener {
                listener?.onCategorySelected(categoryList[adapterPosition])
                categoryList.firstOrNull { it.selected }?.selected = false
                categoryList[adapterPosition].selected = true
                notifyDataSetChanged()
            }
        }
    }

    fun clearDate() {
        categoryList.clear()
        notifyDataSetChanged()
    }

    fun addCategory(list: MutableList<Category>) {
        categoryList.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onCategorySelected(category: Category)
    }
}