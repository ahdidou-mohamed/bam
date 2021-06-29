package com.bam.repos.ui.repos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bam.repos.base.BaseFragment
import com.bam.repos.databinding.FragmentReposLayoutBinding
import com.bam.repos.model.ReposItem
import com.bam.repos.utils.OnLoadMoreListener
import com.bam.repos.utils.RecyclerViewLoadMoreScroll
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
    private val reposAdapter = ReposAdapter()

    /**
     * The Recycler View Scroll Listener
     */
    lateinit var scrollListener: RecyclerViewLoadMoreScroll

    /**
     * Hold the current page index
     */
    private var currentReposIndex = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReposLayoutBinding.inflate(layoutInflater)

        binding.adapter = reposAdapter
        binding.layoutManager = LinearLayoutManager(activity)

        // Calling the presenter onViewCreated to initialize the network request
        presenter.onViewCreated()

        // Adding Load more listener
        val layoutManager = LinearLayoutManager(activity)
        scrollListener = RecyclerViewLoadMoreScroll(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                presenter.loadRepos(currentReposIndex)
                currentReposIndex++
            }
        })
        binding.reposRecyclerView.addOnScrollListener(scrollListener)
        binding.reposRecyclerView.setHasFixedSize(true)

        // Swipe to refresh

        binding.swiperefresh.setOnRefreshListener {
            currentReposIndex = 1
            reposAdapter.clear()
            presenter.loadRepos(currentReposIndex)
        }

        return binding.root
    }

    override fun updateRepos(repos: List<ReposItem>) {
        scrollListener.setLoaded()
        binding.swiperefresh.isRefreshing = false
        reposAdapter.removeLoadingView()
        reposAdapter.setData(repos)
    }

    override fun showError(error: String) {
        Snackbar.make(binding.reposContainer, error, Snackbar.LENGTH_LONG).setAction("Ok") {}.show()
        currentReposIndex--
    }

    override fun showLoading() {
        reposAdapter.addLoadingView()
    }

    override fun hideLoading() {
        scrollListener.setLoaded()
        reposAdapter.removeLoadingView()
    }

    override fun instantiatePresenter(): ReposPresenter {
        return ReposPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}