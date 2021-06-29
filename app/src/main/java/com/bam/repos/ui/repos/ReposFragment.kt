package com.bam.repos.ui.repos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Calling the presenter onViewCreated to initialize the network request
        presenter.onViewCreated()

        // Setting up the RecyclerView
        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)

        binding.reposRecyclerView.adapter = reposAdapter
        binding.reposRecyclerView.layoutManager = layoutManager
        binding.reposRecyclerView.setHasFixedSize(true)

        // Adding Load more listener
        scrollListener = RecyclerViewLoadMoreScroll(layoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                currentReposIndex++
                presenter.loadRepos(currentReposIndex)
            }
        })
        binding.reposRecyclerView.addOnScrollListener(scrollListener)

        // Swipe to refresh

        binding.swiperefresh.setOnRefreshListener {
            currentReposIndex = 1
            reposAdapter.clear()
            presenter.loadRepos(currentReposIndex)
        }
    }

    override fun updateRepos(repos: List<ReposItem>) {
        binding.swiperefresh.isRefreshing = false
        reposAdapter.removeLoadingView()
        reposAdapter.setData(repos)
    }

    override fun showError(error: String) {
        Snackbar.make(binding.reposContainer, error, Snackbar.LENGTH_LONG).setAction("Ok") {}.show()
        currentReposIndex--
        scrollListener.setLoaded()
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