package com.app.di

import androidx.room.Room
import com.app.dataBase.BirdsDatabase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectTestFeature() = loadTestFeature

private val loadTestFeature by lazy {
    loadKoinModules(
        listOf(roomTestModule)
    )
}

/**
 * In-Memory Room Database definition
 */

val roomTestModule = module {

    // Test Room Database
    single {
        // In-Memory database config
        Room.inMemoryDatabaseBuilder(get(), BirdsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    // Test Birds DAO
    single { get<BirdsDatabase>().birdsDAO() }
}