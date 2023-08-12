package com.example.githubshowcaseapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubshowcaseapp.databinding.LayoutItemListBinding
import com.example.network_module.model.Item

class GithubRepoAdapter(
    private val itemList: List<Item>,
    private val onItemClick: (item: Item?) -> Unit
) :
    RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GithubRepoViewHolder(
            LayoutItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    inner class GithubRepoViewHolder(private val binding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemData: Item) {
            binding.item = itemData
            binding.cvRepoParentLayout.setOnClickListener {
                onItemClick.invoke(itemData)
            }
        }
    }
}