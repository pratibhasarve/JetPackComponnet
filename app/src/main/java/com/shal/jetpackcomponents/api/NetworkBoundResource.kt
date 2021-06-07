package com.shal.jetpackcomponents.api

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.shal.jetpackcomponents.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor() {


    private val result = MediatorLiveData<Resource<ResultType>>()


    init {
        result.value = Resource.Loading(null)
        @Suppress("LeakingThis")

        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
               }
            }
        }

    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>?) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }


    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected open fun processResponse(response: RequestType) = response

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ResultType>


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            // when (response) {
            Log.d("TAG", "Here is response :" + response)
            CoroutineScope(IO).launch {
                saveCallResult(processResponse(response as RequestType))
            }
            CoroutineScope(Main).launch {
                result.addSource(loadFromDb()) { newData ->
                    setValue(Resource.Success(newData))
                }

            }

            //}
        }
    }
}


/*
is ApiSuccessResponse<*> -> {
    appExecutors.diskIO().execute {
        saveCallResult(processResponse(response as ApiSuccessResponse<RequestType>))
        appExecutors.mainThread().execute {
            // we specially request a new live data,
            // otherwise we will get immediately last cached value,
            // which may not be updated with latest results received from network.
            result.addSource(loadFromDb()) { newData ->
                setValue(Resource.Success(newData))
            }
        }
    }
}
is ApiEmptyResponse<*> -> {
    appExecutors.mainThread().execute {
        // reload from disk whatever we had
        result.addSource(loadFromDb()) { newData ->
            setValue(Resource.Success(newData))
        }
    }
}
is ApiErrorResponse<*> -> {
    onFetchFailed()
    result.addSource(dbSource) { newData ->
        setValue(Resource.Error(response.errorMessage, newData))
    }
}
     */
