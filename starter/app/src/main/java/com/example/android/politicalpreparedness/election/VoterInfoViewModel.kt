package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(val repository: ElectionRepository, val election: Election) :
    ViewModel() {

    private val _voterInfo = MutableLiveData<VoterInfoResponse?>()

    val voterInfo: LiveData<VoterInfoResponse?>
        get() = _voterInfo

    private val _urlBrowser = MutableLiveData<String?>()

    val urlBrowser: LiveData<String?>
        get() = _urlBrowser

    val isFollow: LiveData<Boolean> =
        Transformations.map(repository.getElection(election.id)) { it != null }

    init {
        viewModelScope.launch {
            try {
                val address = if (election.division.state.isNotBlank())
                    "${election.division.country}, ${election.division.state}"
                else election.division.country
                val response = repository.getVoterInfo(address, election.id)
                _voterInfo.value = response
            } catch (e: Exception) {
                _voterInfo.value = VoterInfoResponse(election)
            }
        }
    }

     fun openBrowser(url: String) {
        _urlBrowser.value = url
    }

     fun openBrowserCompleted() {
        _urlBrowser.value = null
    }


    fun toggleFollow(election: Election, isFollow: Boolean) {
        viewModelScope.launch {
            if (!isFollow)
                repository.saveElection(election)
            else
                repository.deleteElection(election)
        }

    }
}