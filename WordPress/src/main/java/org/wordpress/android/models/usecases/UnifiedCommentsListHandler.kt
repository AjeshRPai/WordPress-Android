package org.wordpress.android.models.usecases

import kotlinx.coroutines.flow.merge
import org.wordpress.android.models.usecases.BatchModerateCommentsUseCase.ModerateCommentsAction.OnModerateComments
import org.wordpress.android.models.usecases.BatchModerateCommentsUseCase.Parameters.ModerateCommentsParameters
import org.wordpress.android.models.usecases.ModerateCommentWithUndoUseCase.ModerateCommentsAction
import org.wordpress.android.models.usecases.ModerateCommentWithUndoUseCase.ModerateCommentsAction.OnUndoModerateComment
import org.wordpress.android.models.usecases.ModerateCommentWithUndoUseCase.Parameters.ModerateCommentParameters
import org.wordpress.android.models.usecases.PaginateCommentsUseCase.PaginateCommentsAction.OnGetPage
import org.wordpress.android.models.usecases.PaginateCommentsUseCase.PaginateCommentsAction.OnReloadFromCache
import org.wordpress.android.models.usecases.PaginateCommentsUseCase.Parameters.GetPageParameters
import org.wordpress.android.models.usecases.PaginateCommentsUseCase.Parameters.ReloadFromCacheParameters
import javax.inject.Inject

class UnifiedCommentsListHandler @Inject constructor(
    private val paginateCommentsUseCase: PaginateCommentsUseCase,
    val batchModerationUseCase: BatchModerateCommentsUseCase,
    val moderationWithUndoUseCase: ModerateCommentWithUndoUseCase
) {
    private val useCases = listOf(paginateCommentsUseCase, batchModerationUseCase, moderationWithUndoUseCase)

    fun subscribe() = useCases.map { it.subscribe() }.merge()

    suspend fun requestPage(parameters: GetPageParameters) = paginateCommentsUseCase.manageAction(
            OnGetPage(parameters)
    )

    suspend fun moderateComments(parameters: ModerateCommentsParameters) = batchModerationUseCase.manageAction(
            OnModerateComments(parameters)
    )

    suspend fun moderateWithUndoSupport(action: ModerateCommentsAction) = moderationWithUndoUseCase.manageAction(
            action
    )

    suspend fun undoCommentModeration(parameters: ModerateCommentParameters) = moderationWithUndoUseCase.manageAction(
            OnUndoModerateComment(parameters)
    )

    suspend fun refreshFromCache(parameters: ReloadFromCacheParameters) = paginateCommentsUseCase.manageAction(
            OnReloadFromCache(parameters)
    )
}
