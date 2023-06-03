package com.hivian.compose_mvvm.core.extensions

import androidx.annotation.StringRes
import com.hivian.compose_mvvm.core.R
import com.hivian.compose_mvvm.core.datasources.remote.ErrorType

@StringRes
fun ErrorType.toErrorMessage(): Int {
    return when(this) {
        ErrorType.ACCESS_DENIED -> R.string.error_access_denied
        ErrorType.CANCELLED -> R.string.error_cancelled
        ErrorType.HOST_UNREACHABLE -> R.string.error_no_connection
        ErrorType.TIMED_OUT -> R.string.error_timeout
        ErrorType.NO_RESULT -> R.string.error_not_found
        ErrorType.UNKNOWN -> R.string.error_unknown
        ErrorType.DATABASE_ERROR -> R.string.error_database
    }
}
