package jp.developer.retia.frozenword.model

import java.util.Date

data class LogStreak(val start: Date, val end: Date, val size: Int, val length: Int)
