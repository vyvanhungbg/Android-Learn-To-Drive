package com.suspend.android.learntodrive.ui.tips


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.CircularEffect
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.createPowerMenu
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.utils.extension.color

class MoreMenuTipFactory : PowerMenu.Factory() {

    override fun create(context: Context, lifecycle: LifecycleOwner): PowerMenu {
        return createPowerMenu(context) {
            addItem(PowerMenuItem(context.getString(R.string.title_tip_600), true, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_a1), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_a2), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_a3), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_a4), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_b1), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_b2), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_c), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_def), false, R.drawable.ic_baseline_info_24))
            addItem(PowerMenuItem(context.getString(R.string.title_tip_exam_drive), false, R.drawable.ic_baseline_info_24))
            setSelectedTextColor(context.color(R.color.color_main_700))
            setDivider(ColorDrawable(context.color(R.color.color_main_900)))
            setDividerHeight(1)
            setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
            setMenuRadius(10f) // sets the popup corner radius.
            setMenuShadow(10f)
            setPadding(12)
            setIconSize(24)
            setAutoDismiss(true)
            setLifecycleOwner(lifecycle)
            setTextColor(context.color(R.color.black))
            setTextSize(20)
            setTextGravity(Gravity.START)
            setTextTypeface(Typeface.create("roboto_condensed_bold", Typeface.NORMAL))
            setSelectedTextColor(Color.RED)
            setMenuColor(Color.WHITE)
            setInitializeRule(Lifecycle.Event.ON_CREATE, 0)
            setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
            //setHeaderView(R.layout.dl_setting_in_test)
            setCircularEffect(CircularEffect.BODY)
            setSelectedEffect(true)
        }
    }
}