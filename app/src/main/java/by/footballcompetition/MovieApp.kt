package by.footballcompetition

import android.app.Application
import by.footballcompetition.di.apiModule
import by.footballcompetition.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApp)
            modules(listOf(viewModels, apiModule))
        }
    }
}