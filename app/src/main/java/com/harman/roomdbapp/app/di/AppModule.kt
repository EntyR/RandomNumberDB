package com.harman.roomdbapp.app.di

import android.app.Application
import androidx.room.Room
import com.harman.roomdbapp.app.other.DATABASE_NAME
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.data.repository.RandomNumberRepository
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


fun providesDatabase(application: Application): RandomNumberDataBase =
    Room.databaseBuilder(application, RandomNumberDataBase::class.java, DATABASE_NAME)
        .build()


val appModule = module {
    single<IRandomNumberRepository> {
        RandomNumberRepository(get())
    }

    single {
        providesDatabase(get())
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