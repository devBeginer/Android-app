package com.example.lab6

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {}

val viewModelModule = module {

    single {
        ProfileViewModel(get())
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDB::class.java, "users")
            .build()
    }
    single {
        get<AppDB>().userDao()
    }
}

val repositoryModule = module {
    single { Repository(get()) }
}