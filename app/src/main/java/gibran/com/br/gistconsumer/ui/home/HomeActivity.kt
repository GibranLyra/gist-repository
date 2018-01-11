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
        val fragment = supportFragmentManager.findFragmentByTag(homeId)
                as HomeFragment? ?: HomeFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame, homeId)
        }
        HomePresenter(GistsApi, fragment, SchedulerProvider)
    }

    private fun openAboutFragment() {
        replaceFragmentInActivity(AboutFragment.newInstance(), R.id.contentFrame)
    }

    private fun openFavoriteFragment() {
        val favoriteId = "favoriteId"
        val fragment = supportFragmentManager.findFragmentByTag(favoriteId)
                as FavoriteFragment? ?: FavoriteFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame, favoriteId)
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
