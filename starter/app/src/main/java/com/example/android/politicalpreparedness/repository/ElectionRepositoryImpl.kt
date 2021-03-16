package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ElectionRepositoryImpl constructor(
    val service: CivicsApiService,
    val electionDao: ElectionDao
) : ElectionRepository {
    override suspend fun getUpcomingElections(): ElectionResponse =
        withContext(Dispatchers.IO) {
            service.getElections()
        }

    override suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO) {
            electionDao.insertAll(election)
        }
    }

    override suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            electionDao.delete(election)
        }
    }

    override fun getSavedElections(): LiveData<List<Election>> =
        electionDao.getElections()

}