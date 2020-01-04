package com.rasalexman.core.data.errors

sealed class QException(message: String = "") : Exception(message) {
    class ActivityNullError(message: String = "") : QException(message)

    class IllegalRemoteTypeException : QException("This type of Remote Config Values Doesn't supported")

    class NoInternetConnectionException :
        QException("There is no internet connection. Please try later")

    class EmptyBundleParcelableException(message: String) : QException(message)

    sealed class AuthErrors(message: String = "") : QException(message) {
        class SignInFailedError(message: String = "") : AuthErrors(message)

        object EmptyName : AuthErrors()
        object EmptyEmail : AuthErrors()
        object EmptyPassword : AuthErrors()
        object ShortPassword : AuthErrors()
        object EmptySmsCode : AuthErrors()
        object EmptyPhone : AuthErrors()
        object RepeatedPasswordInvalid : AuthErrors()
        object InvalidEmail : AuthErrors()
        object UserExistError : AuthErrors("No user data")

        class ResultError : AuthErrors()
        class ResultNullToken : AuthErrors()

        class UserNullError : AuthErrors("No user data")
        class EventNullError : AuthErrors("Auth event is broken")

        class InvalidCredentialsError : AuthErrors()
        class TooManyRequestError : AuthErrors()
        class UnauthorizedError(message: String = "") : AuthErrors(message)
    }
}