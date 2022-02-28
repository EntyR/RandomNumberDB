package com.harman.roomdbapp.app.di

import androidx.room.Room
import com.harman.roomdbapp.app.other.DATABASE_NAME
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.data.repository.RandomNumberRepository
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<IRandomNumberRepository> {
        RandomNumberRepository(get())
    }

    single {
        Room.databaseBuilder(androidContext(), RandomNumberDataBase::class.java, DATABASE_NAME)
            .build()
    }

    factory {
        RandomNumberUseCase(get())
    }

}