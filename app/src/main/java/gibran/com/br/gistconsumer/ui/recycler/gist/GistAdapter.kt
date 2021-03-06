package gibran.com.br.gistconsumer.ui.recycler.gist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import gibran.com.br.gistconsumer.R
import gibran.com.br.gistconsumer.ui.GlideApp
import gibran.com.br.gistconsumer.ui.inflate
import gibran.com.br.githubservice.model.Gist
import kotlinx.android.synthetic.main.gist_item.view.*

/**
 * Created by gibranlyra on 10/01/18 for gist_consumer.
 */
class GistAdapter(private val items: MutableList<Gist>,
                  private val listener: (Gist, View) -> Unit) : RecyclerView.Adapter<GistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GistViewHolder(parent.inflate(R.layout.gist_item))

    override fun onBindViewHolder(holder: GistViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount() = items.size

    internal fun add(gists: ArrayList<Gist>) {
        val adapterSize = items.size
        items.addAll(gists)
        notifyItemRangeInserted(adapterSize, gists.size)
    }

    internal fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}

class GistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Gist, listener: (Gist, View) -> Unit) = with(itemView) {
        gistDescription.text = item.description
        gistAuthor.text = item.owner?.login
        gistLanguage.text = item.files?.file?.language
        GlideApp.with(this)
                .load(item.owner?.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.github_placeholder)
                .into(authorImage)
        setOnClickListener { listener(item, authorImage) }
    }
}