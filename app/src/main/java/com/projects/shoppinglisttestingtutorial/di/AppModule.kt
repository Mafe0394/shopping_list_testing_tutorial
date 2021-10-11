package com.projects.shoppinglisttestingtutorial.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingDao
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItemDatabase
import com.projects.shoppinglisttestingtutorial.data.remote.PixabyAPI
import com.projects.shoppinglisttestingtutorial.other.Constants.BASE_URL
import com.projects.shoppinglisttestingtutorial.other.Constants.DATABASE_NAME
import com.projects.shoppinglisttestingtutorial.repositories.DefaultShoppingRepository
import com.projects.shoppinglisttestingtutorial.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingItemDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providesPixabayApi(): PixabyAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabyAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesDefaultShoppingRepository(
        dao:ShoppingDao,
        api:PixabyAPI
    ):ShoppingRepository=DefaultShoppingRepository(dao,api)

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    )= Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )
}