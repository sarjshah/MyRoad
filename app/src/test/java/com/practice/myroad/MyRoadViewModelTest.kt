package com.practice.myroad

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.practice.myroad.internal.di.*
import com.practice.myroad.model.MyRoad
import com.practice.myroad.ui.MyRoadViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MyRoadViewModelTest : KoinTest {

    val myRoadViewModel: MyRoadViewModel by inject()

    val road = "A2"

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observerData: Observer<MyRoad>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(listOf(viewModelModule, repositoryModule, apiModule, networkModule, databaseModule))
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getRoadData() {
        myRoadViewModel.roadData.observeForever(observerData)

        myRoadViewModel.getRoadData(road)

        val value = myRoadViewModel.roadData.value ?: error("No Data in ViewModel")

        Mockito.verify(observerData).onChanged(value)
    }
}