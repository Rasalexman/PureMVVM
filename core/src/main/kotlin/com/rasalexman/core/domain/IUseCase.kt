package com.rasalexman.core.domain

import com.rasalexman.coroutinesmanager.IAsyncTasksManager

interface IUseCase : IAsyncTasksManager {
    interface SingleIn<in Input> :
        IUseCase {
        suspend fun execute(data: Input)
    }

    interface DoubleInOut<in FirstInput, in SecondInput, out Output> :
        IUseCase {
        suspend fun execute(firstParam: FirstInput, secondParam: SecondInput): Output
    }

    interface SingleInOut<in Input, out Output> :
        IUseCase {
        suspend fun execute(data: Input): Output
    }

    interface Out<out Output> : IUseCase {
        suspend fun execute(): Output
    }
}
