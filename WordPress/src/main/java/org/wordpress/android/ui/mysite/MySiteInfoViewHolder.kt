package org.wordpress.android.ui.mysite

import android.view.View
import android.view.ViewGroup
import org.wordpress.android.databinding.MySiteInfoBlockBinding
import org.wordpress.android.ui.mysite.MySiteItem.SiteInfoBlock
import org.wordpress.android.ui.mysite.MySiteItem.SiteInfoBlock.IconState
import org.wordpress.android.util.image.ImageManager
import org.wordpress.android.util.image.ImageType.BLAVATAR
import org.wordpress.android.util.viewBinding

class MySiteInfoViewHolder(
    parent: ViewGroup,
    private val imageManager: ImageManager
) : MySiteItemViewHolder<MySiteInfoBlockBinding>(parent.viewBinding(MySiteInfoBlockBinding::inflate)) {
    fun bind(item: SiteInfoBlock) = with(binding) {
        if (item.iconState is IconState.Visible) {
            mySiteBlavatar.visibility = View.VISIBLE
            imageManager.load(mySiteBlavatar, BLAVATAR, item.iconState.url ?: "")
            mySiteIconProgress.visibility = View.GONE
            mySiteBlavatar.setOnClickListener { item.onIconClick.click() }
        } else if (item.iconState is IconState.Progress) {
            mySiteBlavatar.setOnClickListener(null)
            mySiteIconProgress.visibility = View.VISIBLE
            mySiteBlavatar.visibility = View.GONE
        }
        quickStartIconFocusPoint.setVisibleOrGone(item.showIconFocusPoint)
        if (item.onTitleClick != null) {
            siteInfoContainer.title.setOnClickListener { item.onTitleClick.click() }
        } else {
            siteInfoContainer.title.setOnClickListener(null)
        }
        siteInfoContainer.title.text = item.title
        quickStartTitleFocusPoint.setVisibleOrGone(item.showTitleFocusPoint)
        siteInfoContainer.subtitle.text = item.url
        siteInfoContainer.subtitle.setOnClickListener { item.onUrlClick.click() }
        switchSite.setOnClickListener { item.onSwitchSiteClick.click() }
    }
}
