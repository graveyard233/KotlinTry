package com.example.kotlintry.hiltTry

import com.example.kotlintry.ui.pages.HiltFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Qualifier

@Module
@InstallIn(FragmentComponent::class)// 意思是允许在fragment中使用
abstract class EngineModule {

    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine) :Engine

    @BindElectricEngine
    @Binds
    abstract fun binElectricEngine(electricEngine: ElectricEngine) :Engine

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine