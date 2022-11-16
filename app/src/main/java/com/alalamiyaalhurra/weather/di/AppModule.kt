package com.alalamiyaalhurra.weather.di

import com.alalamiyaalhurra.weather.data.repository.WeatherRepositoryImp

import com.alalamiyaalhurra.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }


}
@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {

    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: WeatherRepositoryImp): WeatherRepository
}


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

