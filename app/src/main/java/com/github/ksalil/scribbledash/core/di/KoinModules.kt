package com.github.ksalil.scribbledash.core.di

import com.github.ksalil.scribbledash.core.DefaultDispatcherProvider
import com.github.ksalil.scribbledash.core.DispatcherProvider
import com.github.ksalil.scribbledash.game.presentation.DrawViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::DefaultDispatcherProvider) { bind<DispatcherProvider>() }
    viewModelOf<DrawViewModel>(::DrawViewModel)
}