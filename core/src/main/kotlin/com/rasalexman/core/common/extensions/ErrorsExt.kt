package com.rasalexman.core.common.extensions

import com.rasalexman.core.R
import com.rasalexman.core.data.errors.QException

fun QException.getErrorMessageResId(): Int {
    return when(this) {
        is QException.AuthErrors.EmptyName -> R.string.error_user_name_empty
        is QException.AuthErrors.EmptyEmail -> R.string.error_user_email_empty
        is QException.AuthErrors.EmptyPassword -> R.string.error_user_password_empty
        is QException.AuthErrors.ShortPassword -> R.string.error_user_password_short
        is QException.AuthErrors.EmptyPhone -> R.string.error_user_phone_empty
        is QException.AuthErrors.EmptySmsCode -> R.string.error_user_sms_code_empty
        is QException.AuthErrors.InvalidEmail -> R.string.error_user_email_incorrect
        is QException.AuthErrors.RepeatedPasswordInvalid -> R.string.error_user_repeated_password_incorrect
        is QException.AuthErrors.UnauthorizedError -> R.string.error_user_not_exist
        is QException.AuthErrors.TooManyRequestError -> R.string.error_too_many_request
        is QException.AuthErrors.InvalidCredentialsError -> R.string.error_user_invalid_credentials
        is QException.AuthErrors.ResultNullToken -> R.string.error_unexpected
        is QException.AuthErrors.ResultError -> R.string.error_unexpected
        is QException.AuthErrors.EventNullError -> R.string.error_user_event_sign_in_failed
        is QException.AuthErrors.SignInFailedError -> R.string.error_user_sign_in
        is QException.AuthErrors.UserExistError -> R.string.error_user_exist
        is QException.AuthErrors.UserNullError -> R.string.error_user_not_exist

        is QException.NoInternetConnectionException -> R.string.error_no_internet_connection
        is QException.ActivityNullError -> R.string.error_internal_exception
        else -> -1
    }
}

inline fun <reified T : QException> throwError(message: String = ""): Nothing {
    throw makeError<T>(message)
}

inline fun <reified T : QException> makeError(message: String = ""): Exception {
    return when (T::class) {
        QException.ActivityNullError::class -> QException.ActivityNullError(message)
        QException.EmptyBundleParcelableException::class -> QException.EmptyBundleParcelableException(message)

        QException.AuthErrors.ResultError::class -> QException.AuthErrors.ResultError()
        QException.AuthErrors.ResultNullToken::class -> QException.AuthErrors.ResultNullToken()
        QException.AuthErrors.UserNullError::class -> QException.AuthErrors.UserNullError()
        QException.AuthErrors.SignInFailedError::class -> QException.AuthErrors.SignInFailedError(message)
        QException.AuthErrors.EventNullError::class -> QException.AuthErrors.EventNullError()
        QException.AuthErrors.InvalidCredentialsError::class -> QException.AuthErrors.InvalidCredentialsError()
        QException.AuthErrors.UnauthorizedError::class -> QException.AuthErrors.UnauthorizedError()
        QException.AuthErrors.TooManyRequestError::class -> QException.AuthErrors.TooManyRequestError()

        QException.NoInternetConnectionException::class -> QException.NoInternetConnectionException()

        QException.AuthErrors.EmptyName::class -> QException.AuthErrors.EmptyName
        QException.AuthErrors.EmptyEmail::class -> QException.AuthErrors.EmptyEmail
        QException.AuthErrors.EmptyPassword::class -> QException.AuthErrors.EmptyPassword
        QException.AuthErrors.InvalidEmail::class -> QException.AuthErrors.InvalidEmail
        QException.AuthErrors.RepeatedPasswordInvalid::class -> QException.AuthErrors.RepeatedPasswordInvalid

        else -> Exception(message)
    }
}