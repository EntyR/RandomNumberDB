package com.harman.roomdbapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import com.harman.roomdbapp.data.db.RandomNumberDataBase
import com.harman.roomdbapp.data.enity.RandomNumberEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RandomNumberDBTest {

    private lateinit var db: RandomNumberDataBase

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RandomNumberDataBase::class.java).build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun verify_if_number_was_added_and_can_be_received_from_database() = runBlocking {
        val numbers = listOf(RandomNumberEntity(0, 1))
        numbers.forEach {
            db.getDao().addNumber(it)
        }
        val events = mutableListOf<RandomNumberEntity>()
        db.getDao().getAllNumbers().first().forEach {
            events.add(it)
        }
        Truth.assertThat(events.size).isEqualTo(numbers.size)
        events.forEachIndexed { index, entity ->
            Truth.assertThat(entity.number).isEqualTo(numbers[index].number)
        }
    }

    @Test(expected = Test.None::class)
    fun verify_if_flow_can_be_received_from_database_without_exception() {
        db.getDao().getAllNumbers()
    }

    @Test(expected = Test.None::class)
    fun verify_if_number_can_be_inserted_into_database_without_exception() = runBlocking {
        val number = RandomNumberEntity(0, 1)
        db.getDao().addNumber(number)
    }
}
