package org.wordpress.android.models.usecases

import kotlinx.coroutines.flow.MutableSharedFlow
import org.wordpress.android.fluxc.store.CommentStore.CommentError
import org.wordpress.android.fluxc.store.CommentsStore.CommentsData.DoNotCare
import org.wordpress.android.models.usecases.CommentsUseCaseType.PAGINATE_USE_CASE
import org.wordpress.android.models.usecases.LocalCommentCacheUpdateUseCase.CommentsUpdateState.Idle
import org.wordpress.android.models.usecases.LocalCommentCacheUpdateUseCase.PropagateCommentsUpdateAction
import org.wordpress.android.models.usecases.LocalCommentCacheUpdateUseCase.PropagateCommentsUpdateAction.UpdatedComments
import org.wordpress.android.usecase.FlowFSMUseCase
import org.wordpress.android.usecase.UseCaseResult
import org.wordpress.android.usecase.UseCaseResult.Success
import javax.inject.Inject

class LocalCommentCacheUpdateUseCase @Inject constructor() : FlowFSMUseCase<Any, Any, PropagateCommentsUpdateAction,
        Any, CommentsUseCaseType, CommentError>(
        initialState = Idle,
        DoNotCare
) {
    override suspend fun runInitLogic(parameters: Any) {
        manageAction(UpdatedComments)
    }

    sealed class CommentsUpdateState : StateInterface<Any, PropagateCommentsUpdateAction, Any, CommentsUseCaseType,
            CommentError> {
        object Idle : CommentsUpdateState() {
            override suspend fun runAction(
                resourceProvider: Any,
                action: PropagateCommentsUpdateAction,
                flowChannel: MutableSharedFlow<UseCaseResult<CommentsUseCaseType, CommentError, Any>>
            ): StateInterface<Any, PropagateCommentsUpdateAction, Any, CommentsUseCaseType, CommentError> {
                return when (action) {
                    is UpdatedComments -> {
                        flowChannel.emit(Success(PAGINATE_USE_CASE, DoNotCare))
                        Idle
                    }
                }
            }
        }
    }

    sealed class PropagateCommentsUpdateAction {
        object UpdatedComments : PropagateCommentsUpdateAction()
    }
}
