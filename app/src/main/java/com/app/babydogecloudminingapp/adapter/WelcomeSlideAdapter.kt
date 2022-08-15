package com.app.babydogecloudminingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.babydogecloudminingapp.databinding.SlideItemBinding

class WelcomeSlideAdapter(private val imageList: ArrayList<Int>) :
    RecyclerView.Adapter<WelcomeSlideAdapter.WelcomeSlideViewHolder>() {

    class WelcomeSlideViewHolder(binding: SlideItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            val mBinding = binding;

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeSlideViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val slideItemBinding: SlideItemBinding = SlideItemBinding.inflate(layoutInflater,parent,false)

        return WelcomeSlideViewHolder(slideItemBinding)

    }

    override fun onBindViewHolder(holder: WelcomeSlideViewHolder, position: Int) {

        val resourceId = imageList[position]

        holder.mBinding.welcomeSlideImage.setImageResource(resourceId)

    }

    override fun getItemCount(): Int {
        return imageList.size
    }

}