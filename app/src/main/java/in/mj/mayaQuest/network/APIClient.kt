package `in`.mj.mayaQuest.network

import `in`.mj.mayaQuest.const.MayaQuestConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

internal object APIClient {
    val getClient: APIInterface by lazy {
        Timber.d("Creating retrofit client")
        val retrofit = Retrofit.Builder()
            .baseUrl(MayaQuestConstants.BASE_URL)
            // Moshi maps JSON to classes
            .addConverterFactory(MoshiConverterFactory.create())
            // The call adapter handles threads
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        // Create Retrofit client
        return@lazy retrofit.create(APIInterface::class.java)
    }
}