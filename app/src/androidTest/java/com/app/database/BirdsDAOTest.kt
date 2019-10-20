package com.app.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.dataBase.BirdsDAO
import com.app.dataBase.BirdsDatabase
import com.app.di.injectTestFeature
import com.app.model.birds.BirdsEntity
import com.app.utils.ConstantsTest.Companion.BIRD_TEST_NAME
import com.app.utils.ConstantsTest.Companion.BIRD_TEST_NOTES
import com.app.utils.ConstantsTest.Companion.BIRD_TEST_RARITY
import com.app.utils.ConstantsTest.Companion.BIRD_TIMESTAMP
import com.app.utils.ConstantsTest.Companion.TEST_LATITUDE
import com.app.utils.ConstantsTest.Companion.TEST_LONGITUDE
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class BirdsDAOTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
    * Inject needed components from Koin
    */

    private val birdsDatabase: BirdsDatabase by inject()
    private val birdsDAO: BirdsDAO by inject()

    /**
     * Override default Koin configuration to use Room in-memory database
     */

    @Before
    fun setUpDatabase() {
        stopKoin() // to remove 'A Koin Application has already been started'
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            injectTestFeature()
        }
    }

    @Test
    fun writeBirdAndReadInList() {

        val birdsEntity = BirdsEntity(
            BIRD_TIMESTAMP,
            BIRD_TEST_NAME,
            BIRD_TEST_NOTES,
            BIRD_TEST_RARITY,
            TEST_LATITUDE,
            TEST_LONGITUDE
        )
        runBlocking {
            birdsDAO.insertBirdData(birdsEntity)
            val birdsData = birdsDAO.fetchBirdsData()

            assertEquals(1, birdsData.size)
            assertEquals(birdsData[0].birdName, birdsEntity.birdName)
            assertEquals(birdsData[0].notes, birdsEntity.notes)
            assertEquals(birdsData[0].birdRarity, birdsEntity.birdRarity)

            assertTrue(birdsData[0].latitude == birdsEntity.latitude)
            assertTrue(birdsData[0].longitude == birdsEntity.longitude)
            assertTrue(birdsData[0].timeStamp == birdsEntity.timeStamp)

            assertFalse(birdsData[0].latitude == 0.0)
            assertFalse(birdsData[0].longitude == 0.0)
        }
    }

    /**
     * Close resources
     */

    @After
    fun tearDown() {
        birdsDatabase.close()
        stopKoin()
    }
}