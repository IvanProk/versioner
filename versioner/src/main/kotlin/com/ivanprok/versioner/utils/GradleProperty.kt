package com.ivanprok.versioner.utils

import org.gradle.api.Project
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal inline fun <reified T> gradleProperty(
    project: Project,
    default: T
) = object : ReadWriteProperty<Any, T> {
    private val property = project.objects.property(T::class.java).apply { set(default) }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T = this.property.get()
    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) = this.property.set(value)
}

internal inline fun <reified T> gradleProperty(project: Project) = gradleProperty<T?>(project, null)

internal inline fun <reified E> gradleListProperty(
    project: Project,
    default: List<E>
) = object : ReadWriteProperty<Any, List<E>> {
    private val property = project.objects.listProperty(E::class.java).apply { set(default) }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): List<E> = this.property.get()
    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: List<E>) = this.property.set(value)
}
