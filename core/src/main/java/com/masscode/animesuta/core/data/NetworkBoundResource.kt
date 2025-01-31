package com.masscode.animesuta.core.data

import com.masscode.animesuta.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {
    private var isFirst = true

    private var result: Flow<Resource<ResultType>> = flow {

        var count = getCount()
        println("maduro count before -> ${count.first()}")

        if (isFirst) {
            deleteAllFromDB()
            isFirst = false
        }
        println("maduro count before -> ${count.first()}")
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (true) {
//        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage, null))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun deleteAllFromDB()

    protected abstract fun getCount(): Flow<Int>

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow():Flow<Resource<ResultType>> {
        return  result
    }
}