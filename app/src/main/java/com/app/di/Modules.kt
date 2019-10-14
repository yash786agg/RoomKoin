package com.app.di

import android.app.Application
import androidx.room.Room
import com.app.dataBase.BirdsDatabase
import com.app.helper.UiHelper
import com.app.location.LocationViewModel
import com.app.repository.birds.BirdsRepository
import com.app.repository.birds.BirdsUseCase
import com.app.ui.birds.viewmodel.BirdsViewModel
import com.app.utils.Constants.Companion.BIRDS_DATABASE_NAME
import com.google.android.gms.location.LocationServices
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
                   fUsedLocationModule,
                   uiHelperModule)
    )
}

val viewModelModule = module {
    viewModel { BirdsViewModel(birdsUseCase = get()) }
    viewModel { LocationViewModel(locationProviderClient = get(),uiHelper = get()) }
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

val fUsedLocationModule = module {
    single { locationProviderClient(androidApplication()) }
}

val birdsAppModule = module {

    // Room Database
    single { Room.databaseBuilder(androidApplication(), BirdsDatabase::class.java, BIRDS_DATABASE_NAME).build() }

    // BirdsDAO
    single { get<BirdsDatabase>().birdsDAO() }
}

private fun locationProviderClient(androidApplication : Application)
        = LocationServices.getFusedLocationProviderClient(androidApplication)


