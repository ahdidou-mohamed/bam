package com.bam.repos.ui.repos

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bam.repos.databinding.LoadMoreLoaderLayoutBinding
import com.bam.repos.databinding.ReposRowLayoutBinding
import com.bam.repos.model.ReposItem
import com.bam.repos.utils.Constant.Companion.VIEW_TYPE_ITEM
import com.bam.repos.utils.Constant.Companion.VIEW_TYPE_LOADING

class ReposAdapter: Adapter<RecyclerView.ViewHolder >() {

    private var repositories = mutableListOf<ReposItem?>()

    class MyViewHolder(val binding: ReposRowLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(repos: ReposItem) {
            binding.repos = repos
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding = ReposRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }



    class LoadingViewHolder(val binding: LoadMoreLoaderLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): LoadingViewHolder {
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding = LoadMoreLoaderLayoutBinding.inflate(layoutInflater, parent, false)
                return LoadingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (repositories[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            MyViewHolder.from(parent)
        }else {
            LoadingViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder , position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            val currentRepos = repositories[position]!!
            (holder as MyViewHolder).bind(currentRepos)
            holder.bind(currentRepos)
        }

    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    fun addLoadingView() {
        //Add loading item
        Handler().post {
            if (repositories.size > 0 && repositories[repositories.size - 1] != null) {
                repositories.add(null)
                notifyItemInserted(repositories.size - 1)
            }
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        if (repositories.size != 0) {
            repositories.removeAt(repositories.size - 1)
            notifyItemRemoved(repositories.size)
        }
    }

    fun clear() {
        repositories.clear()
        notifyDataSetChanged()
    }

    fun setData(newData: List<ReposItem>) {
        repositories.addAll(newData)
        notifyDataSetChanged()
        Log.d("ONLOADMORE", repositories.size.toString())
    }

}