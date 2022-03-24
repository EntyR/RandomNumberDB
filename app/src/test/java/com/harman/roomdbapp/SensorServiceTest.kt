package com.harman.roomdbapp

import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LifecycleService
import com.google.common.truth.Truth
import com.harman.roomdbapp.app.services.SensorService
import com.harman.roomdbapp.domain.use_cases.GravityFluctuationUseCase
import com.harman.roomdbapp.extension.InstantExecutorExtension
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkConstructor
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExtendWith(InstantExecutorExtension::class)
class SensorServiceTest : KoinTest {

    private lateinit var useCase: GravityFluctuationUseCase
    private lateinit var service: SensorService
    private val dispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {
        val context: Context = mockk(relaxed = true) {
            every { applicationContext } returns this
        }
        useCase = mockk(relaxed = true)

        startKoin {
            modules(
                module {
                    single { useCase }
                    single { Dispatchers.Default }
                }
            )
        }
        val notificationService: android.app.NotificationManager = mockk(relaxed = true)
        val lifecycle = LifecycleRegistry(mockk(relaxed = true))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        service = spyk(SensorService()){
            every { applicationContext } returns context
            every { this@spyk.lifecycle } returns lifecycle
            every { getSystemService(Context.NOTIFICATION_SERVICE) } returns notificationService
            every { packageManager } returns mockk(relaxed = true)
        }

        mockkStatic(PendingIntent::class)
        every { PendingIntent.getActivity(any(), any(), any(), any()) } returns mockk(relaxed = true)

        mockkConstructor(NotificationCompat.Builder::class)
        every { anyConstructed<NotificationCompat.Builder>().build() } returns mockk(relaxed = true)

        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
        unmockkConstructor(NotificationCompat.Builder::class)
        unmockkStatic(PendingIntent::class)
        Dispatchers.resetMain()
    }

    @Test
    fun verify_service_adding_new_value_when_receive() {
        val initVal = 1f
        val initFlow = flowOf(initVal)
        val resultList = mutableListOf<Float>()
        coEvery { useCase.getFluctuationsRecord() } returns initFlow
        coEvery { useCase.addNewItem(initVal) }.answers {
            resultList.add(initVal)
        }

        service.onCreate()
        Truth.assertThat(resultList)
            .contains(initVal)
    }
}