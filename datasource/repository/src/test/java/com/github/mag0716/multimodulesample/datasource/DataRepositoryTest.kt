package com.github.mag0716.multimodulesample.datasource

import com.github.mag0716.api.ApiService
import com.github.mag0716.api.response.DataResponse
import com.github.mag0716.api.response.DetailResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DataRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var dataRepository: IDataRepository

    @Before
    fun setUp() {
        apiService = MockApiService()
        dataRepository = DataRepository(apiService)
    }

    @Test
    fun API経由でデータ一覧が取得できること() = runBlocking {
        val dataList = dataRepository.refreshDataList()
        assertEquals(10, dataList.size)
        assertEquals(0, dataList[0].id)
        assertEquals("title0", dataList[0].title)
        assertEquals("thumbnail0", dataList[0].thumbnail)
    }

    @Test
    fun API経由でデータ詳細が取得できること() = runBlocking {
        val detailData = dataRepository.refreshDataDetail(0)
        assertEquals(0, detailData.id)
        assertEquals("title0", detailData.title)
        assertEquals("description0", detailData.description)
        assertEquals("image0", detailData.image)
        assertEquals(0L, detailData.createdTime)
    }

    @Test
    fun 初回はキャッシュがないのでデータ一覧が取得できないこと() = runBlocking {
        val cacheDataList = dataRepository.loadDataList()
        assertEquals(0, cacheDataList.size)
    }

    @Test
    fun 一度APIを実行したらデータ一覧のキャッシュが取得できること() = runBlocking {
        dataRepository.refreshDataList()
        val cacheDataList = dataRepository.loadDataList()
        assertEquals(10, cacheDataList.size)
    }

    @Test
    fun 初回はデータ詳細のキャッシュが取得できないこと() = runBlocking {
        val cacheDataDetail = dataRepository.loadDataDetail(0)
        assertEquals(null, cacheDataDetail)
    }

    @Test
    fun 一度APIを実行したらデータ詳細のキャッシュが取得できること() = runBlocking {
        dataRepository.refreshDataDetail(0)
        val cacheDataDetail = dataRepository.loadDataDetail(0)
        assertNotNull(null, cacheDataDetail)
        assertEquals(0, cacheDataDetail!!.id)
    }

    // region API mock

    private class MockApiService : ApiService {
        override suspend fun data(): List<DataResponse> {
            delay(1000)
            return MutableList(10) {
                DataResponse(it, "title$it", "thumbnail$it")
            }
        }

        override suspend fun detail(id: Int): DetailResponse {
            delay(1000)
            return DetailResponse(id, "title$id", "description$id", "image$id", id.toLong())
        }
    }

    // endregion
}