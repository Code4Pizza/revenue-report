package com.fr.fbsreport.ui.branch

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fr.fbsreport.R
import com.fr.fbsreport.base.BaseRecyclerAdapter
import com.fr.fbsreport.extension.inflate
import com.fr.fbsreport.model.Branch
import kotlinx.android.synthetic.main.item_view_branch.view.*

class BranchAdapter : BaseRecyclerAdapter<Branch, BranchAdapter.BranchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BranchAdapter.BranchViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(items[position])
    }

    class BranchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_view_branch)) {

        fun bind(branch: Branch) {
            itemView.txt_code.text = branch.code
            itemView.txt_name.text = branch.name
            itemView.txt_address.text = branch.address
        }
    }
}