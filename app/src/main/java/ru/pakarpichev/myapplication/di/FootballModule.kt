package ru.pakarpichev.myapplication.di

import android.content.Context
import androidx.room.Room
import com.onesignal.OneSignal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pakarpichev.myapplication.data.DbRepositoryImpl
import ru.pakarpichev.myapplication.data.FootballDao
import ru.pakarpichev.myapplication.data.FootballDb
import ru.pakarpichev.myapplication.domain.repository.DbRepository
import ru.pakarpichev.myapplication.domain.repository.RetrofitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FootballModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): RetrofitRepository =
        Retrofit.Builder()
            .baseUrl("https://apiv3.apifootball.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitRepository::class.java)
    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext appContext: Context): FootballDb {
        return Room.databaseBuilder(
            appContext,
            FootballDb::class.java,
            "note.db"
        ).build()
    }
    @Singleton
    @Provides
    fun provideNoteDao(appDatabase: FootballDb): FootballDao {
        return appDatabase.FootballDao()
    }
    @Singleton
    @Provides
    fun provideDbRepository(dao: FootballDao): DbRepository = DbRepositoryImpl(dao = dao)
}