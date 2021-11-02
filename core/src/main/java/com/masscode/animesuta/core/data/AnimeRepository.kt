package com.masscode.animesuta.core.data

import com.masscode.animesuta.core.data.source.local.LocalDataSource
import com.masscode.animesuta.core.data.source.remote.RemoteDataSource
import com.masscode.animesuta.core.data.source.remote.network.ApiResponse
import com.masscode.animesuta.core.data.source.remote.response.AnimeResponse
import com.masscode.animesuta.core.domain.model.Anime
import com.masscode.animesuta.core.domain.repository.IAnimeRepository
import com.masscode.animesuta.core.utils.AppExecutors
import com.masscode.animesuta.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAnimeRepository {

    var lixo = object : NetworkBoundResource<List<Anime>, List<AnimeResponse>>() {
        override fun loadFromDB(): Flow<List<Anime>> {
            return localDataSource.getAllAnime().map { DataMapper.mapEntitiesToDomain(it) }
        }

        override fun shouldFetch(data: List<Anime>?): Boolean {
            return data == null || data.isEmpty()
        }

        override suspend fun createCall(): Flow<ApiResponse<List<AnimeResponse>>> {
            return remoteDataSource.getAllAnime()
        }

        override suspend fun saveCallResult(data: List<AnimeResponse>) {
            val animeList = DataMapper.mapResponsesToEntities(data)
            animeList.get(0).id = (0..100).random()
            localDataSource.insertAnime(animeList)
        }

        override suspend fun deleteAllFromDB() {
            localDataSource.deleteAll()
        }

        override fun getCount(): Flow<Int> {
            return localDataSource.getCount()
        }

    }


    override fun getAllAnime(): Flow<Resource<List<Anime>>> =
        lixo.asFlow()

    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return localDataSource.getFavoriteAnime().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteAnime(anime: Anime, state: Boolean) {
        val animeEntity = DataMapper.mapDomainToEntity(anime)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteAnime(animeEntity, state)
        }
    }
}