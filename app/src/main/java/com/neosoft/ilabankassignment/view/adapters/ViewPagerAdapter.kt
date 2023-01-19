package com.neosoft.ilabankassignment.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.neosoft.ilabankassignment.databinding.PagerRowBinding
import com.neosoft.ilabankassignment.model.ImageData

class ViewPagerAdapter(imageList: List<ImageData>) : PagerAdapter() {

    private var imageList: List<ImageData>? = imageList

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return imageList!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val itemView = PagerRowBinding.inflate(LayoutInflater.from(container.context), container, false)


        itemView.imgIndicator.setImageResource(imageList!![position].imageUrl!!)
        container.addView(itemView.root)
        return itemView.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}