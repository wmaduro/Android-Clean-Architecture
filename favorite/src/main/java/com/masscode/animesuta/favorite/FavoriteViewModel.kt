package com.masscode.animesuta.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.masscode.animesuta.core.domain.usecase.AnimeUseCase
import com.masscode.animesuta.core.utils.Lixo

class FavoriteViewModel(animeUseCase: AnimeUseCase, lixo: Lixo) : ViewModel() {
    init {
//        println("maduro - homevm rezted -${Thread.currentThread().name}")
//        TesteObj.teste(viewModelScope)
        println("maduro - init >>> ${this.javaClass.simpleName}")
        println("madurox - $lixo | ${lixo.teste}")
        lixo.teste="teste lixo favorite"
        println("madurox - $lixo | ${lixo.teste}")
    }
    override fun onCleared() {
        super.onCleared()
        println("maduro - on cleared <<< ${this.javaClass.simpleName}")
    }

    val favoriteAnime = animeUseCase.getFavoriteAnime().asLiveData()

}

