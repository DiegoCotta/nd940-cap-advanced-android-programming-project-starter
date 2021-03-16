package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse

interface ElectionRepository {
    suspend fun getUpcomingElections(): ElectionResponse

    suspend fun saveElection(election: Election)

    suspend fun deleteElection(election: Election)

    fun getSavedElections(): LiveData<List<Election>>
}