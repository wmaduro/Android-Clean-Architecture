package com.masscode.animesuta.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.masscode.animesuta.core.domain.usecase.AnimeUseCase
import com.masscode.animesuta.core.utils.Lixo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(animeUseCase: AnimeUseCase, lixo: Lixo) : ViewModel() {

    init {
//        println("maduro - homevm rezted -${Thread.currentThread().name}")
//        TesteObj.teste(viewModelScope)
        println("maduro - init >>> ${this.javaClass.simpleName}....... ")
        println("madurox - $lixo | ${lixo.teste}")
        lixo.teste="teste lixo home"
        println("madurox - $lixo | ${lixo.teste}")
    }
    override fun onCleared() {
        super.onCleared()
        println("maduro - on cleared <<< ${this.javaClass.simpleName}")
    }


    val anime = animeUseCase.getAllAnime().asLiveData()


   object TesteObj {

        fun teste(viewModelScope: CoroutineScope) {
            viewModelScope.launch {
                var i = 0
                while (true) {
                    if (i++ > 100) break
                    println("maduro - *HomeViewModel $i -${Thread.currentThread().name}")
                    delay(1000)
                }

            }
        }

    }
}




