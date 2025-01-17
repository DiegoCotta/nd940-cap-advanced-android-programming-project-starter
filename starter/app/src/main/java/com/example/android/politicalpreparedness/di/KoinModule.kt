package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import com.example.android.politicalpreparedness.repository.ElectionRepositoryImpl
import com.example.android.politicalpreparedness.representative.RepresentativeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    fun getModule() = module {
        single { ElectionDatabase.getInstance(get()).electionDao }
        single {
            return@single CivicsApi.retrofitService
        }
        single<ElectionRepository> { ElectionRepositoryImpl(get(), get()) }
        single { }

        viewModel { ElectionsViewModel(get(), get()) }
        viewModel { (election: Election) -> VoterInfoViewModel(get(), election) }
        viewModel { RepresentativeViewModel(get()) }
    }
}