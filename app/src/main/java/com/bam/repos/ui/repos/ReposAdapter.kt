package com.bam.repos.ui.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bam.repos.databinding.ReposRowLayoutBinding
import com.bam.repos.model.ReposItem
import com.bam.repos.utils.ReposDiffUtil

class ReposAdapter: Adapter<ReposAdapter.MyViewHolder>() {

    private var repositories = emptyList<ReposItem>()

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRepos = repositories[position]
        holder.bind(currentRepos)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    fun setData(newData: List<ReposItem>) {
        val diffUtil = ReposDiffUtil(repositories, repositories)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        repositories = newData

        diffUtilResult.dispatchUpdatesTo(this)
    }

}