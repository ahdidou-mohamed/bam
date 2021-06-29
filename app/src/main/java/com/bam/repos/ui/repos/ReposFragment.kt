package com.bam.repos.ui.repos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bam.repos.base.BaseFragment
import com.bam.repos.databinding.FragmentReposLayoutBinding
import com.bam.repos.model.ReposItem
import com.google.android.material.snackbar.Snackbar

class ReposFragment : BaseFragment<ReposPresenter>(), ReposView {

    /**
     * DataBinding instance
     */
    private var _binding: FragmentReposLayoutBinding? = null
    private val binding get() = _binding!!

    /**
     * The adapter for the list of repositories
     */
    private val postsAdapter = ReposAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReposLayoutBinding.inflate(layoutInflater)
        binding.adapter = postsAdapter
        binding.layoutManager = LinearLayoutManager(activity)
        presenter.onViewCreated()
        return binding.root
    }

    override fun updateRepos(repos: List<ReposItem>) {
        postsAdapter.setData(repos)
    }

    override fun showError(error: String) {
        Snackbar.make(binding.reposContainer, error, Snackbar.LENGTH_LONG).setAction("Ok") {}.show()
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun instantiatePresenter(): ReposPresenter {
        return ReposPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}