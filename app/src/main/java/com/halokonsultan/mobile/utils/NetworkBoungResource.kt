package com.halokonsultan.mobile.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResult: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            val response = fetch()
            saveFetchResult(response)
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable.localizedMessage ?: "unknown error", it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}