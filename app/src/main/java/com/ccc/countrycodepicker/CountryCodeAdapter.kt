package com.ccc.countrycodepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_country_code_picker.view.*

class CountryCodeAdapter : RecyclerView.Adapter<CountryCodeAdapter.ViewHolder>() {

    var mCountries = ArrayList<Country>()
    private var mOnItemClick: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_code_picker, parent, false)
        return ViewHolder(view, mOnItemClick)
    }

    override fun getItemCount(): Int {
        return mCountries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mCountries[position], position)
    }

    fun setOnItemClickListener(onItemClick: OnItemClickListener?) {
        mOnItemClick = onItemClick
    }

    fun updateData(countries: List<Country>) {
        mCountries.clear()
        mCountries.addAll(countries)
        notifyDataSetChanged()
    }

    fun setCountryPicked(country: Country) {
        val index = mCountries.indexOfFirst { it.nameCode == country.nameCode }
        if (index == -1) return
        val countryPicked = mCountries[index]
        mCountries.removeAt(index)
        notifyItemRemoved(index)
        mCountries.add(0, countryPicked)
        notifyItemInserted(0)
    }

    class ViewHolder(
        itemView: View,
        onItemClick: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {
        private var mOnItemClick = onItemClick

        fun bind(country: Country, position: Int) {
            if (position == 0) {
                itemView.horizontalLineView.visibility = View.VISIBLE
            } else {
                itemView.horizontalLineView.visibility = View.GONE
            }
            itemView.flagImageView.setImageResource(Country.getFlagMasterResID(country))
            itemView.nameTextView.text = country.name
            itemView.phoneCodeTextView.text = country.phoneCode

            itemView.setOnClickListener {
                mOnItemClick?.onItemClick(country)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(country: Country)
    }
}