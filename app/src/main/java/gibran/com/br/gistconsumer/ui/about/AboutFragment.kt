package gibran.com.br.gistconsumer.ui.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.GlideApp
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */

const val ABOUT_IMAGE_URL = "https://avatars0.githubusercontent.com/u/5739609?s=460&v=4"
class AboutFragment: Fragment() {

    companion object {
        fun newInstance(): AboutFragment = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(this)
                .load(ABOUT_IMAGE_URL)
                .centerCrop()
                .into(aboutImage)
    }
}