package com.example.jetpackcomposebase.ui.dashboard.repository

import com.example.jetpackcomposebase.base.BaseRepository
import com.example.jetpackcomposebase.network.ApiInterface
import com.example.jetpackcomposebase.ui.dashboard.model.MovieCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class HomeRepository @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository() {


    fun getCharacters(): Flow<List<MovieCharacter>> = flow {
        val characters = apiInterface.getCharacter()
        emit(characters)
    }
}

