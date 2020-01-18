package com.rasalexman.core.domain

import com.rasalexman.core.common.typealiases.ResultList
import com.rasalexman.coroutinesmanager.IAsyncTasksManager
import kotlinx.coroutines.flow.Flow

interface IUseCase {
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

    interface SingleInOutList<in Input, out Output> :
        IUseCase {
        suspend fun execute(data: Input): ResultList<Output>
    }

    interface SingleFlowInOut<in Input, out Output> :
        IUseCase {
        suspend fun execute(data: Input): Flow<Output>
    }

    interface SingleFlowInOutList<in Input, out Output> :
        IUseCase {
        suspend fun execute(data: Input): Flow<ResultList<Output>>
    }

    interface Out<out Output> : IUseCase {
        suspend fun execute(): Output
    }

    interface OutList<out Output> : IUseCase {
        suspend fun execute(): ResultList<Output>
    }
}
