package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
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

    override fun getElection(electionId: Int): LiveData<Election?> =
        electionDao.getElection(electionId)


    override suspend fun getVoterInfo(address: String, electionId: Int): VoterInfoResponse =
        withContext(Dispatchers.IO) {
            service.getVoterInfo(address, electionId)
        }

    override suspend fun getRepresentatives(address: String): RepresentativeResponse =
        withContext(Dispatchers.IO) {
            service.getRepresentatives(address)
        }


}