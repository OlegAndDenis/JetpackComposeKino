package com.example.kino.screen.search

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.bumptech.glide.Glide
import com.example.kino.TitleInRecycler
import com.example.kino.databinding.*
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult

class ExpandableAdapter : BaseExpandableListAdapter() {
    private val map: MutableMap<NetworkItem, List<NetworkItem>> = mutableMapOf()

    fun setMap(map: Map<NetworkItem, List<NetworkItem>>) {
        this.map.putAll(map)
    }

    override fun getGroupCount(): Int = map.keys.size

    override fun getChildrenCount(groupPosition: Int): Int =
        map.values.elementAt(groupPosition).size

    override fun getGroup(groupPosition: Int): Any = map.keys.elementAt(groupPosition)

    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        map.values.elementAt(groupPosition)[childPosition]

    override fun getGroupId(groupPosition: Int): Long = map.keys.elementAt(groupPosition).hashCode().toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = map.values.elementAt(groupPosition)[childPosition].hashCode().toLong()

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        return if (convertView != null) {
            val binding = convertView.let { TitleViewHolderBinding.bind(it) }
            binding.title.text =
                "${(map.keys.elementAt(groupPosition) as TitleInRecycler).title} найденео ${
                    (map.keys.elementAt(groupPosition) as TitleInRecycler).count
                }"
            binding.root
        } else {
            parent!!.rootView
        }
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        return when ((getGroup(groupPosition) as TitleInRecycler).title) {
            "Фильмы" -> {
                val binding = convertView?.let { SearchMovieBinding.bind(it) }
                Glide.with(binding!!.root)
                    .load(
                        "https://image.tmdb.org/t/p/w500/${
                            (getChild(
                                groupPosition,
                                childPosition
                            ) as MovieResult).posterPath
                        }"
                    )
                    .thumbnail(0.5f)
                    .centerCrop()
                    .error(
                        Glide
                            .with(binding!!.root)
                            .asBitmap()
                            .load(
                                "https://image.tmdb.org/t/p/w500/${
                                    (getChild(
                                        groupPosition,
                                        childPosition
                                    ) as MovieResult).backdropPath
                                }"
                            )
                    )
                    .into(binding.searchImage)
                binding.searchTitle.text = when {
                    (getChild(groupPosition, childPosition) as MovieResult).title != "no" -> {
                        (getChild(groupPosition, childPosition) as MovieResult).title
                    }
                    (getChild(groupPosition, childPosition) as MovieResult).originalTitle != "no" -> {
                        (getChild(groupPosition, childPosition) as MovieResult).originalTitle
                    }
                    else -> {
                        ""
                    }
                }
                binding.root
            }
            "Сериалы" -> {
                val binding = convertView?.let { SearchSearialsBinding.bind(it) }
                binding!!.root
            }
            "Актер" -> {
                val binding = convertView?.let { SearchPersonBinding.bind(it) }
                binding!!.root
            }
            else -> {
                convertView?.let { SearchResultBinding.bind(it) }!!.root
            }
        }
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false
}