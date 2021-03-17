package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

interface ElectionRepository {
    suspend fun getUpcomingElections(): ElectionResponse

    suspend fun saveElection(election: Election)

    suspend fun deleteElection(election: Election)

    fun getSavedElections(): LiveData<List<Election>>

    fun getElection(electionId: Int) : LiveData<Election?>

    suspend fun getVoterInfo(address: String, electionId: Int) : VoterInfoResponse
}