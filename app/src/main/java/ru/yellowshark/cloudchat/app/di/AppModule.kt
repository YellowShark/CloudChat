package ru.yellowshark.cloudchat.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.yellowshark.cloudchat.data.RepositoryImpl
import ru.yellowshark.cloudchat.data.SharedPrefsStorage
import ru.yellowshark.cloudchat.domain.repository.Repository
import ru.yellowshark.cloudchat.ui.chat.ChatViewModel
import ru.yellowshark.cloudchat.ui.sign_in.SignInViewModel

val viewModelsModule = module {
    viewModel { ChatViewModel(get()) }
    viewModel { SignInViewModel(get()) }
}

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get()) }
}

val sharedPrefsModule = module {
    single { SharedPrefsStorage(androidContext()) }
}