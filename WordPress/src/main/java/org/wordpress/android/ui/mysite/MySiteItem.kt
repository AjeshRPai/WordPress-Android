package org.wordpress.android.ui.mysite

import org.wordpress.android.ui.mysite.MySiteItem.Type.SITE_INFO_BLOCK
import org.wordpress.android.ui.utils.ListItemInteraction

sealed class MySiteItem(val type: Type) {
    enum class Type {
        SITE_INFO_BLOCK, HEADER, LIST_ITEM
    }

    data class SiteInfoBlock(
        val title: String,
        val url: String,
        val iconUrl: String? = null,
        val onTitleClick: ListItemInteraction? = null,
        val onIconClick: ListItemInteraction,
        val onUrlClick: ListItemInteraction,
        val onSwitchSiteClick: ListItemInteraction
    ) : MySiteItem(SITE_INFO_BLOCK)
}
