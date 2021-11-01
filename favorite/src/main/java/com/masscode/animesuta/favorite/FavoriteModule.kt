package com.masscode.animesuta.favorite

import com.masscode.animesuta.core.data.source.local.LocalDataSource
import com.masscode.animesuta.core.utils.Lixo
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get(), get()) }
}