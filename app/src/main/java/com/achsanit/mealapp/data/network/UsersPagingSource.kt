package com.achsanit.mealapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.achsanit.mealapp.data.response.DataItem
import retrofit2.HttpException
import java.io.IOException

class UsersPagingSource(
    private val service: AuthService
): PagingSource<Int, DataItem>() {
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        val pageIndex = params.key ?: 1
        val query = HashMap<String, String>()
        query["page"] = pageIndex.toString()

        return try {
            val response = service.getListUsers(query)
            val user = response.data

            val nextKey = user.let {
                if (it.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            }

            LoadResult.Page(
                data = user.map {
                    DataItem(
                        id = it?.id,
                        lastName = it?.lastName,
                        firstName = it?.firstName,
                        avatar = it?.avatar,
                        email = it?.email
                    )
                },
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}