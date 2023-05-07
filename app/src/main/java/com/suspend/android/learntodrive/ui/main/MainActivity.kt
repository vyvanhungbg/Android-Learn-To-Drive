package com.suspend.android.learntodrive.ui.main


import android.graphics.Color
import android.os.Build
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseActivity
import com.suspend.android.learntodrive.databinding.ActivityMainBinding
import com.suspend.android.learntodrive.utils.extension.color
import com.suspend.android.learntodrive.utils.extension.hide
import com.suspend.android.learntodrive.utils.extension.show


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initData() {
        // impl
    }

    override fun handleEvent() {
        // impl
    }

    override fun bindData() {
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        binding.navView.setupWithNavController(popupMenu.menu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_simulation,
                R.id.navigation_setting -> binding.navView.show()
                else -> binding.navView.hide()
            }
        }
       // binding.navView.isVisible = false
        setUpHideStatusBar()
       // hideSystemUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    private fun setUpHideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = color(R.color.color_main)
        }
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}
