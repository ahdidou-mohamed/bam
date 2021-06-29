package com.bam.repos.ui.repos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bam.repos.base.BaseFragment
import com.bam.repos.databinding.FragmentReposLayoutBinding
import com.bam.repos.model.ReposItem

class ReposFragment: BaseFragment<ReposPresenter>(), ReposView {

    /**
     * DataBinding instance
     */
    private var _binding: FragmentReposLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReposLayoutBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun updateRepos(repos: List<ReposItem>) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context {
        TODO("Not yet implemented")
    }

    override fun instantiatePresenter(): ReposPresenter {
        return ReposPresenter(this)
    }
}