package by.footballcompetition.di

import by.footballcompetition.api.ApiInterface
import by.footballcompetition.ui.competition.CompetitionsViewModel
import by.footballcompetition.ui.standing.StandingActivity
import by.footballcompetition.ui.standing.StandingViewModel
import by.footballcompetition.ui.team.TeamsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val viewModels = module {
    viewModel { TeamsViewModel(get()) }
    viewModel { CompetitionsViewModel(get()) }
    viewModel { StandingViewModel(get(), getProperty(StandingActivity.COMPETITION_CODE)) }
}

val apiModule = module {
    single {

        val tokenInterceptor = Interceptor { chain ->
            val request =
                  chain
                        .request()
                        .newBuilder()
                        .addHeader("X-Auth-Token", "972eea617f4542d48d00e3bdd22ffa36")
                        .build()
            chain.proceed(request)
        }

        val logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.HEADERS
        }

        val okHttpClient =
              OkHttpClient.Builder()
                    .addInterceptor(tokenInterceptor)
                    .addInterceptor(logInterceptor)
                    .build()

        val retrofit =
              Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api.football-data.org/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()

        retrofit.create(ApiInterface::class.java)
    }
}