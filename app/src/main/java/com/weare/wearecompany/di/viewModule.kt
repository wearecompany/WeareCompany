package com.weare.wearecompany.di

import com.weare.wearecompany.ui.container.ContainerViewModel
import com.weare.wearecompany.ui.join.JoinViewModel
import com.weare.wearecompany.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModel: Module = module {
    viewModel { LoginViewModel(get()) }
    viewModel { JoinViewModel(get()) }
    viewModel { ContainerViewModel(get()) }
}

val appModules = listOf{
    viewModel
}