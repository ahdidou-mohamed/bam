package com.bam.repos.ui.repos

import android.content.Context
import com.bam.repos.base.BaseFragment
import com.bam.repos.model.ReposItem

class ReposFragment: BaseFragment<ReposPresenter>(), ReposView {
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