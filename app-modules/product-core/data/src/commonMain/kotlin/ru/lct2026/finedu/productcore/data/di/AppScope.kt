package ru.lct2026.finedu.productcore.data.di

import me.tatarka.inject.annotations.Scope

/** Один экземпляр на приложение. Ставится на корневой компонент и `@Provides`. */
@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class AppScope
