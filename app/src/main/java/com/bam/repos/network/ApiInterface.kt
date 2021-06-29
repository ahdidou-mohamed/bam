package com.bam.repos.network

import com.bam.repos.model.ReposItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface  {
    /**
     * Get the list of the repos from the Github API
     */
    @GET("/orgs/bamlab/repos")
    fun getRepos(@Query("page") page: Int): Observable<List<ReposItem>>

}