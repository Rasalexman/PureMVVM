package com.rasalexman.providers.data.models

inline class UserName(val value: String)
inline class UserEmail(val value: String)
inline class UserPassword(val value: String)

fun String.toUserName() = UserName(this)
fun String.toUserEmail() = UserEmail(this)
fun String.toUserPassword() = UserPassword(this)