package gibran.com.br.gistconsumer.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.net.nowonline.presentation.util.schedulers.SchedulerProvider
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.replaceFragmentInActivity
import gibran.com.br.githubservice.gists.GistsApi

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewFragment()
    }

    private fun setupViewFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as HomeFragment? ?: HomeFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }
        HomePresenter(GistsApi, fragment, SchedulerProvider)

    }

}
