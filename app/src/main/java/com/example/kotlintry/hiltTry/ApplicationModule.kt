package com.example.kotlintry.hiltTry

import android.app.Application
import com.example.kotlintry.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideApp(application: Application):App{
        return application as App
    }

}