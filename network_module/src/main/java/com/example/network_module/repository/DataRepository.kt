package com.example.network_module.repository

import com.example.network_module.network.NetworkCallStatus
import com.example.network_module.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepository(
    private val service: NetworkService,
    private val coroutineDisPatcher: CoroutineDispatcher
) {

    suspend fun fetchRepositoryDetails(searchName: String) =
        flow {
            emit(NetworkCallStatus.Loading())
            emit(service.fetchRepoDetails(searchName = searchName))
        }.catch { this.emit(NetworkCallStatus.Error(msg = it.message, data = null)) }
            .flowOn(coroutineDisPatcher)

    suspend fun fetchRepositoriesIssues(fullName: String) =
        flow {
            emit(NetworkCallStatus.Loading())
            emit(service.fetchRepositoriesIssues(fullName = fullName))
        }.catch { this.emit(NetworkCallStatus.Error(msg = it.message, data = null)) }
            .flowOn(coroutineDisPatcher)

    suspend fun fetchRepositoriesContributors(fullName: String) =
        flow {
            emit(NetworkCallStatus.Loading())
            emit(service.fetchRepositoriesContributors(fullName = fullName))
        }.catch { this.emit(NetworkCallStatus.Error(msg = it.message, data = null)) }
            .flowOn(coroutineDisPatcher)

}