package com.harman.roomdbapp

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.google.common.truth.Truth
import com.harman.roomdbapp.app.services.SensorService
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import com.harman.roomdbapp.extension.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.dsl.module


@ExtendWith(InstantExecutorExtension::class)
class SensorServiceTest {

    lateinit var useCase: GravityFluctuationUseCase
    lateinit var service: SensorService
    val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {

        useCase = mockk(relaxed = true)
        service = mockk()

        startKoin {
            modules(
                module {
                    single { useCase }
                    single<CoroutineDispatcher> { dispatcher }
                }
            )
        }

    }

    @Test
    fun verify_service_adding_new_value_when_receive() {
        val initVal = 1f
        val initFlow = flowOf(initVal)
        val resultList = mutableListOf<Float>()
        coEvery { useCase.getFluctuationsRecord() } returns initFlow

        val lifecycle = LifecycleRegistry(mockk(relaxed = true))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        every { service.lifecycle } returns lifecycle
        every { service.onCreate() } answers {
            callOriginal()
        }
        coEvery { useCase.addNewItem(initVal) }.answers {
            resultList.add(initVal)
        }

        service.onCreate()
        Truth.assertThat(resultList)
            .contains(initVal)
    }
}