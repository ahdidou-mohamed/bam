package com.bam.repos.ui.repos

import com.bam.repos.base.BasePresenter
import com.bam.repos.network.ApiClient.client
import com.bam.repos.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ReposPresenter(reposView: ReposView) : BasePresenter<ReposView>(reposView) {
    private var subscription: Disposable? = null

    override fun onViewCreated() {
        loadRepos(1)
    }

    /**
     * Loads the repos from the API and presents them in the view when retrieved, or shows error if
     * any.
     */
    fun loadRepos(index: Int) {
        view.showLoading()

        val apiService = client!!.create(ApiInterface::class.java)

        subscription = apiService
            .getRepos(index)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { view.hideLoading() }
            .subscribe(
                { reposList -> view.updateRepos(reposList) },
                { err -> view.showError(err.localizedMessage) }
            )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}