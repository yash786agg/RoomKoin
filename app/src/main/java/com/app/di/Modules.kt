package com.app.di

import androidx.room.Room
import com.app.database.BirdsDatabase
import com.app.helper.UiHelper
import com.app.repository.birds.BirdsRepository
import com.app.repository.birds.BirdsUseCase
import com.app.ui.birds.list.BirdsListViewModel
import com.app.utils.Constants.Companion.BIRDS_DATABASE_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
            listOf(viewModelModule,
                   repositoryModule,
                   useCaseModule,
                   birdsAppModule,
                   uiHelperModule)
    )
}

val viewModelModule = module {
    viewModel { BirdsListViewModel(birdsUseCase = get()) }
}

val repositoryModule = module {
    single { BirdsRepository(birdsDAO = get()) }
}

val useCaseModule = module {
    single { BirdsUseCase(birdsRepository = get()) }
}

val uiHelperModule = module {
    single { UiHelper(androidContext()) }
}

val birdsAppModule = module {

    // Room Database
    single { Room.databaseBuilder(androidApplication(), BirdsDatabase::class.java, BIRDS_DATABASE_NAME).build() }

    // BirdsDAO
    single { get<BirdsDatabase>().birdsDAO() }
}

