package com.harman.roomdbapp.other

import java.lang.reflect.Field
import java.lang.reflect.Modifier

fun setStaticFieldViaReflection(field: Field, value: Any) {
    field.isAccessible = true
    Field::class.java.getDeclaredField("modifiers").apply {
        isAccessible = true
        setInt(field, field.modifiers and Modifier.FINAL.inv())
    }
    field.set(null, value)
}
