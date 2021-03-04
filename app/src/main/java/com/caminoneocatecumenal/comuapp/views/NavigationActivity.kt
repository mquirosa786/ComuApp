package com.caminoneocatecumenal.comuapp.views

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.ActivityNavigationBinding
import com.caminoneocatecumenal.comuapp.viewmodels.NavigationViewModel
import com.caminoneocatecumenal.comuapp.views.menu.MenuDufourFragment
import com.caminoneocatecumenal.comuapp.views.menu.MenuHomeFragment
import com.mquirosa786.bottommenu.BottomMenuLayout.Model

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var navigationActivityViewModel : NavigationViewModel
    val ID_RESUCITO = 1
    val ID_HOME = 2
    val ID_DUFOUR = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_navigation)
        navigationActivityViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        binding.bottomMenu.add(
            Model(
                ID_RESUCITO,
                R.drawable.ic_navigation_resucito,
                resources.getString(R.string.navigation_resucito)
            )
        )
        binding.bottomMenu.add(
            Model(
                ID_HOME,
                R.drawable.ic_navigation_home,
                resources.getString(R.string.navigation_inicio)
            )
        )
        binding.bottomMenu.add(
            Model(
                ID_DUFOUR,
                R.drawable.ic_navigation_dufour,
                resources.getString(R.string.navigaion_dufour)
            )
        )

        setBottomMenuOnClick()
        showHome()
    }

    fun showDufour(){
        binding.bottomMenu.show(ID_DUFOUR, false)
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.contentBox, MenuDufourFragment()).addToBackStack(null).commit()
    }

    private fun setBottomMenuOnClick() {
        binding.bottomMenu.setOnClickMenuListener { model ->
            clearAllFragmentStack()
            when (model.id) {
                ID_DUFOUR -> showDufour()
                else -> showHome()
            }
        }
    }

    fun clearAllFragmentStack() {
        val fm = this.supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun showHome(){
        binding.bottomMenu.show(ID_HOME, false)
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.contentBox, MenuHomeFragment()).addToBackStack(null).commit()
    }

    /*fun showHome() {
        binding.bottomNavigation.show(NavigationActivity.ID_HOME, false)
        goToFragment(HomeFragment())
    }

    fun showProducts() {
        binding.bottomNavigation.show(NavigationActivity.ID_PRODUCTS, false)
        goToFragment(ProductsFragment())
    }

    fun showPayments() {
        binding.bottomNavigation.show(NavigationActivity.ID_PAYMENTS, false)
        goToFragment(PaymentsFragment())
    }

    fun showTransfers() {
        binding.bottomNavigation.show(NavigationActivity.ID_TRANSFERS, false)
        goToFragment(TransfersHomeFragment())
    }

    fun showMore() {
        binding.bottomNavigation.show(NavigationActivity.ID_MORE, false)
        goToFragment(MoreFragment())
    }

    private fun setUpEvents() {
        binding.bottomNavigation.setOnClickMenuListener({ model ->
            clearAllFragmentStack()
            when (model.getId()) {
                NavigationActivity.ID_PRODUCTS -> goToFragment(mProductsFragment)
                NavigationActivity.ID_PAYMENTS -> goToFragment(mPaymentsFragment)
                NavigationActivity.ID_TRANSFERS -> goToFragment(mTransfersHomeFragment)
                NavigationActivity.ID_MORE -> goToFragment(mMoreFragment)
                else -> goToFragment(HomeFragment())
            }
            null
        })
    }*/
}
