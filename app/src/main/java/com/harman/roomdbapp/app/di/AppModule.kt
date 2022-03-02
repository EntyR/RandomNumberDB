package com.harman.roomdbapp.app.di

import android.app.Application
import androidx.room.Room
import com.harman.roomdbapp.app.other.DATABASE_NAME
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.data.repository.RandomNumberRepository
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


fun providesDatabase(application: Application): RandomNumberDataBase =
    Room.databaseBuilder(application, RandomNumberDataBase::class.java, DATABASE_NAME)
        .build()

fun providesRandomNumberDao(database: RandomNumberDataBase): IRandomNumberDao {
    return  database.getDao()
}

val appModule = module {
    single {
        providesDatabase(androidApplication())
        providesRandomNumberDao(get())
    }

    single<IRandomNumberRepository> {
        RandomNumberRepository(get())
    }

    factory {
        RandomNumberUseCase(get())
    }

    single {
        Dispatchers.Default
    }

    viewModel {
        NumberListViewModel(get(), get())
    }

}