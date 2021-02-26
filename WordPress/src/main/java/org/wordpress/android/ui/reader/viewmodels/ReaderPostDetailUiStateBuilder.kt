package org.wordpress.android.ui.reader.viewmodels

import dagger.Reusable
import org.wordpress.android.models.ReaderPost
import org.wordpress.android.ui.reader.discover.ReaderPostCardAction.SecondaryAction
import org.wordpress.android.ui.reader.discover.ReaderPostCardActionType
import org.wordpress.android.ui.reader.discover.ReaderPostUiStateBuilder
import org.wordpress.android.ui.reader.viewmodels.ReaderPostDetailViewModel.ReaderPostDetailsUiState
import org.wordpress.android.ui.reader.views.ReaderPostDetailsHeaderViewUiStateBuilder
import javax.inject.Inject

@Reusable
class ReaderPostDetailUiStateBuilder @Inject constructor(
    private val postDetailsHeaderViewUiStateBuilder: ReaderPostDetailsHeaderViewUiStateBuilder,
    private val postUiStateBuilder: ReaderPostUiStateBuilder
) {
    fun mapPostToUiState(
        post: ReaderPost,
        moreMenuItems: List<SecondaryAction>? = null,
        onButtonClicked: (Long, Long, ReaderPostCardActionType) -> Unit,
        onBlogSectionClicked: (Long, Long) -> Unit,
        onFollowClicked: () -> Unit,
        onTagItemClicked: (String) -> Unit
    ) = ReaderPostDetailsUiState(
            postId = post.postId,
            blogId = post.blogId,
            headerUiState = buildPostDetailsHeaderUiState(
                    post,
                    onBlogSectionClicked,
                    onFollowClicked,
                    onTagItemClicked
            ),
            moreMenuItems = moreMenuItems,
            actions = buildPostActions(post, onButtonClicked)
    )

    private fun buildPostDetailsHeaderUiState(
        post: ReaderPost,
        onBlogSectionClicked: (Long, Long) -> Unit,
        onFollowClicked: () -> Unit,
        onTagItemClicked: (String) -> Unit
    ) = postDetailsHeaderViewUiStateBuilder.mapPostToUiState(
            post,
            onBlogSectionClicked,
            onFollowClicked,
            onTagItemClicked
    )

    fun buildPostActions(
        post: ReaderPost,
        onButtonClicked: (Long, Long, ReaderPostCardActionType) -> Unit
    ) = postUiStateBuilder.mapPostToActions(post, onButtonClicked)
}
