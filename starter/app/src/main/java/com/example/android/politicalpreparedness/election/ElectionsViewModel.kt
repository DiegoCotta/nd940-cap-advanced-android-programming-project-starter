package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class ElectionsViewModel constructor(
    electionRepository: ElectionRepository,
    app: Application
) : AndroidViewModel(app) {

    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val savedElections = electionRepository.getSavedElections()

    init {
        viewModelScope.launch {
            try {
                _upcomingElections.value = electionRepository.getUpcomingElections().elections
            } catch (e: Exception) {

            }
        }
    }

    private val _navigateToVoterInfo = MutableLiveData<Election?>()

    val navigateToVoterInfo: LiveData<Election?>
        get() = _navigateToVoterInfo

    fun displayVoterInfo(election: Election) {
        _navigateToVoterInfo.value = election
    }

    fun displayVoterInfoComplete() {
        _navigateToVoterInfo.value = null
    }

}