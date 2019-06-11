package com.github.mag0716.usercase

import com.github.mag0716.multimodulesample.datasource.IDataRepository
import com.github.mag0716.multimodulesample.datasource.model.Data
import com.github.mag0716.multimodulesample.datastore.model.Detail
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetDataListUseCaseTest {

    private lateinit var dataListView: MockDataListView
    private lateinit var useCase: IGetDataListUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        dataListView = MockDataListView()
        useCase = GetDataListUseCase(
            MockDataRepository(),
            dataListView
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun 仕様を満たしているか() = runBlocking {
        useCase.execute()

        assertEquals(1, dataListView.showLoadingCount)
        assertEquals(1, dataListView.dismissLoadingCount)
        val showedDataList = dataListView.showedDataList
        assertEquals(2, showedDataList.size)
        // cache
        assertEquals(0, showedDataList[0][0].id)
        assertEquals("[mock]title0", showedDataList[0][0].title)
        assertEquals("[mock]thumbnail0", showedDataList[0][0].thumbnail)
        // api result
        assertEquals(0, showedDataList[1][0].id)
        assertEquals("title0", showedDataList[1][0].title)
        assertEquals("thumbnail0", showedDataList[1][0].thumbnail)
    }

    // region mock

    private class MockDataRepository : IDataRepository {
        override suspend fun refreshDataList(): List<Data> {
            return MutableList(10) {
                Data(it, "title$it", "thumbnail$it")
            }
        }

        override suspend fun refreshDataDetail(id: Int): Detail {
            throw IllegalStateException("invalid behavior")
        }

        override suspend fun loadDataList(): List<Data> {
            return MutableList(10) {
                Data(it, "[mock]title$it", "[mock]thumbnail$it")
            }
        }

        override suspend fun loadDataDetail(id: Int): Detail? {
            throw IllegalStateException("invalid behavior")
        }
    }

    class MockDataListView : IDataListView {

        val showedDataList = mutableListOf<List<Data>>()
        var showLoadingCount = 0
        var dismissLoadingCount = 0

        override fun showDataList(dataList: List<Data>) {
            showedDataList.add(dataList)
        }

        override fun showLoading() {
            showLoadingCount++
        }

        override fun dismissLoading() {
            dismissLoadingCount++
        }
    }

    // endregion
}