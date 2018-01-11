package gibran.com.br.gistconsumer.ui.home

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import br.com.net.nowonline.presentation.util.schedulers.SchedulerProvider
import gibran.com.br.gistconsumer.AppContext
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.about.AboutFragment
import gibran.com.br.gistconsumer.ui.favorite.FavoriteFragment
import gibran.com.br.gistconsumer.ui.favorite.FavoritePresenter
import gibran.com.br.gistconsumer.ui.replaceFragmentInActivity
import gibran.com.br.gistconsumer.ui.setupActionBar
import gibran.com.br.gistconsumer.util.tests.EspressoIdlingResource
import gibran.com.br.githubservice.gists.GistsApi
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
        }
        bottomNavigation.setOnNavigationItemSelectedListener {
            changeFragment(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        val homeId = "home"
        var fragment: HomeFragment? = supportFragmentManager.findFragmentByTag(homeId) as HomeFragment?
        if (fragment == null) {
            // Create the fragment
            fragment = HomeFragment.newInstance()
            replaceFragmentInActivity(fragment, R.id.contentFrame, homeId)
        }
        HomePresenter(GistsApi, fragment, SchedulerProvider)
    }

    private fun openAboutFragment() {
        replaceFragmentInActivity(AboutFragment.newInstance(), R.id.contentFrame)
    }

    private fun openFavoriteFragment() {
        val favoriteId = "favoriteId"
        var fragment: FavoriteFragment? = supportFragmentManager.findFragmentByTag(favoriteId) as FavoriteFragment?
        if (fragment == null) {
            // Create the fragment
            fragment = FavoriteFragment.newInstance()
            replaceFragmentInActivity(fragment, R.id.contentFrame, favoriteId)
        }
        FavoritePresenter(AppContext.instance.database, fragment, SchedulerProvider)
    }

    private fun changeFragment(@IdRes itemId: Int) {
        when (itemId) {
            R.id.action_home -> openHomeFragment()
            R.id.action_favorites -> openFavoriteFragment()
            R.id.action_about -> openAboutFragment()
        }
    }

    @VisibleForTesting
    fun getCountingIdlingResource(): IdlingResource {
        return EspressoIdlingResource.idlingResource
    }
}
