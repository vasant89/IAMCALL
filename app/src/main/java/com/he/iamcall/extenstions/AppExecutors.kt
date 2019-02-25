package com.he.iamcall.extenstions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlin.coroutines.CoroutineContext

const val THREAD_COUNT = 3


open class AppExecutors constructor(
        val ioContext: CoroutineContext = Dispatchers.Default,
        val networkContext: CoroutineContext = newFixedThreadPoolContext(THREAD_COUNT, "networkIO"),
        val uiContext: CoroutineContext = Dispatchers.Main)