package jp.developer.retia.frozenword.extension

import android.app.Activity

inline fun <reified T> Activity.lazyWithExtras(key: String): Lazy<T> {
    return lazy { getArgs(key) }
}

inline fun <reified T> Activity.getArgs(key: String) = requireNotNull(intent.extras).get(key) as T
