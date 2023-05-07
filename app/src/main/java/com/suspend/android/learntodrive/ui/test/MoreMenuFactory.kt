package com.suspend.android.learntodrive.ui.test

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.CircularEffect
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu
import com.suspend.android.learntodrive.R

class MoreMenuFactory : PowerMenu.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner): PowerMenu {
        return createPowerMenu(context) {
            addItem(PowerMenuItem(context.getString(R.string.title_submit), false, R.drawable.ic_test_exam_24dp))
            addItem(PowerMenuItem(context.getString(R.string.title_exit), false, R.drawable.ic_exit_border_24dp))
            addItem(PowerMenuItem(context.getString(R.string.title_setting), false, R.drawable.ic_setting_black_24dp))
            addItem(PowerMenuItem(context.getString(R.string.title_info), false, R.drawable.ic_baseline_info_24))
            setDivider(ColorDrawable(ContextCompat.getColor(context, R.color.color_main_900)))
            setDividerHeight(1)
            setMenuRadius(10f) // sets the popup corner radius.
            setMenuShadow(10f)
            setPadding(12)
            setIconSize(24)
            setAutoDismiss(true)
            setLifecycleOwner(lifecycle)
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setTextSize(20)
            setTextGravity(Gravity.START)
            setTextTypeface(Typeface.create("roboto_condensed_bold", Typeface.NORMAL))
            setSelectedTextColor(Color.CYAN)
            setMenuColor(Color.WHITE)
            setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
            setAnimation(MenuAnimation.FADE)
            //setHeaderView(R.layout.dl_setting_in_test)
            //setCircularEffect(CircularEffect.BODY)
        }
    }
}