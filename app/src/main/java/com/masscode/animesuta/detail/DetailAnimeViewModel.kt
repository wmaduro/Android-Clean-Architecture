package com.masscode.animesuta.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masscode.animesuta.core.domain.model.Anime
import com.masscode.animesuta.core.domain.usecase.AnimeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailAnimeViewModel(private val animeUseCase: AnimeUseCase) : ViewModel() {


    init {
//        println("maduro - homevm rezted -${Thread.currentThread().name}")
        TesteObj.teste(viewModelScope)
        println("maduro - init >>> ${this.javaClass.simpleName}")
    }
    override fun onCleared() {
        super.onCleared()
        println("maduro - on cleared <<< ${this.javaClass.simpleName}")
    }

    fun setFavoriteAnime(anime: Anime, newStatus: Boolean) =
        animeUseCase.setFavoriteAnime(anime, newStatus)

    object TesteObj {

        fun teste(viewModelScope: CoroutineScope) {
            viewModelScope.launch {
//                viewModelScope.launch {
                var i = 0
                while (true) {
                    if (i++ > 100) break
                    println("maduro - DetailAnimeViewModel $i -${Thread.currentThread().name}")
                    delay(1000)
                }

            }
        }
    }

}