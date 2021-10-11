package com.projects.shoppinglisttestingtutorial.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingDao
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItemDatabase
import com.projects.shoppinglisttestingtutorial.data.remote.PixabyAPI
import com.projects.shoppinglisttestingtutorial.repositories.DefaultShoppingRepository
import com.projects.shoppinglisttestingtutorial.repositories.FakeShoppingRepositoryAndroidTest
import com.projects.shoppinglisttestingtutorial.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context,
        ShoppingItemDatabase::class.java
    ).allowMainThreadQueries()
        .build()


    @Provides
    fun providesFakeShoppingRepository():ShoppingRepository=
        FakeShoppingRepositoryAndroidTest()


    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    )= Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

}