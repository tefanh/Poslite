package com.tefanhaetami.poslite

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tefanhaetami.poslite.database.iface.Category

@BindingAdapter("categoryId")
fun TextView.setCategoryId(item: Category) {
    item?.let {
        text = item.id.toString()
    }
}

@BindingAdapter("categoryTitle")
fun TextView.setCategoryTitle(item: Category) {
    item?.let {
        text = item.title
    }
}

@BindingAdapter("categoryDescription")
fun TextView.setCategoryDescription(item: Category) {
    item?.let {
        text = item.description
    }
}