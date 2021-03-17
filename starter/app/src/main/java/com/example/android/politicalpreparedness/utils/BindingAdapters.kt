package com.example.android.politicalpreparedness

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("textDate")
fun bindAsteroidStatusImage(textView: TextView, date: Date?) {
    if (date == null)
        textView.text = ""
    else {
        val format = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US)
        textView.text = format.format(date)
    }
}

@BindingAdapter("followText")
fun bindAsteroidStatusImage(button: AppCompatButton, isFollow: Boolean) {
    if(isFollow)
        button.setText(R.string.txt_unfollow_election)
    else
        button.setText(R.string.txt_follow_election)

}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    val adapter = recyclerView.adapter as ElectionListAdapter
    data?.let { adapter.addHeaderAndSubmitList(it) }
    recyclerView.scrollToPosition(0)
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, it: Any?) {
    view.visibility = if (it == null) View.GONE else View.VISIBLE
}
