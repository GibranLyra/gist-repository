package gibran.com.br.gistconsumer.ui.recycler.gist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.inflate
import gibran.com.br.githubservice.model.Gist

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class GistAdapter(private val items: List<Gist>,
                  private val listener: (Gist) -> Unit) : RecyclerView.Adapter<GistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GistViewHolder(parent.inflate(R.layout.gist_view_item))

    override fun onBindViewHolder(holder: GistViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size
}

class GistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Gist, listener: (Gist) -> Unit) = with(itemView) {
        setOnClickListener { listener(item) }
    }
}