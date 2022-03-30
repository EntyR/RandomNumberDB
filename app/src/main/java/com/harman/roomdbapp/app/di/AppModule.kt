package com.harman.roomdbapp.app.di

import android.app.Application
import androidx.room.Room
import com.harman.roomdbapp.app.other.DATABASE_NAME
import com.harman.roomdbapp.app.ui.viewmodel.AddNumberViewModel
import com.harman.roomdbapp.app.ui.viewmodel.GravityViewModel
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.data.dao.IFluctuationDao
import com.harman.roomdbapp.data.dao.IRandomNumberDao
import com.harman.roomdbapp.data.datasouce.GravitySensorDataSource
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.data.repository.GravityFluctuationsRepository
import com.harman.roomdbapp.data.repository.RandomNumberRepository
import com.harman.roomdbapp.domain.datasource.IGravitySensorDataSource
import com.harman.roomdbapp.domain.repository.IGravityFluctuationsRepository
import com.harman.roomdbapp.domain.repository.IRandomNumberRepository
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import com.harman.roomdbapp.domain.use_cases.RandomNumberUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// TODO Add proper migration for release version
fun providesDatabase(application: Application): RandomNumberDataBase {
    return Room.databaseBuilder(application, RandomNumberDataBase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun providesRandomNumberDao(database: RandomNumberDataBase): IRandomNumberDao {
    return database.randomNumberDao()
}

fun provideFluctuationDao(database: RandomNumberDataBase): IFluctuationDao {
    return database.fluctuationDao()
}

val dataBaseModule = module {
    single { providesDatabase(androidApplication()) }
    single { providesRandomNumberDao(get()) }
    single { provideFluctuationDao(get()) }
}

val dataModule = module {
    single<IRandomNumberRepository> { RandomNumberRepository(get()) }
    single<IGravitySensorDataSource> { GravitySensorDataSource(androidContext()) }
    single<IGravityFluctuationsRepository> { GravityFluctuationsRepository(get(), get()) }
}

val useCaseModule = module {
    factory { RandomNumberUseCase(get()) }
    factory { GravityFluctuationUseCase(get()) }
}

val viewModelModule = module {
    single { Dispatchers.Default }
    viewModel { GravityViewModel(get()) }
    viewModel { NumberListViewModel(get()) }
    viewModel { AddNumberViewModel(get(), get()) }
}
