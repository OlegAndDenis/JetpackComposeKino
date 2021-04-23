//package com.example.kino.screen.search
//
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.kino.adapter.holder.BindHolder
//import com.example.kino.network.model.common.NetworkItem
//import com.example.kino.network.model.movie.MovieResult
//import com.example.kino.network.model.person.PersonResult
//import com.example.kino.network.model.serial.SerialsResult
//import com.example.kino.screen.moviefragment.MovieViewHolder
//import com.example.kino.screen.serialfragment.SerialsViewHolder
//
//class SearchAdapter : RecyclerView.Adapter<BindHolder<NetworkItem>>() {
//
//    private var mTList: MutableList<NetworkItem> = mutableListOf()
//    private var mType: String = "search"
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
//        return when (viewType) {
//            1 -> SearchPersonViewHolder(parent)
//            2 -> {
//                if (mType == "person") {
//                    OtherMovieAndSerialImageViewHolder(parent)
//                } else {
//                    SearchMovieViewHolder(parent)
//                }
//            }
//            3 -> {
//                if (mType == "person") {
//                    OtherMovieAndSerialImageViewHolder(parent)
//                } else {
//                    SearchSerialsViewHolder(parent)
//                }
//            }
//            else -> SearchViewHolder(parent)
//        }
//    }
//
//    override fun onBindViewHolder(holder: BindHolder<NetworkItem>, position: Int) {
//        return when (holder.itemViewType) {
//            1 -> (holder as SearchPersonViewHolder).bind(
//                getTList()[position] as PersonResult,
//                position
//            )
//            2 -> {
//                if (mType == "person") {
//                    (holder as OtherMovieAndSerialImageViewHolder).bind(
//                        getTList()[position],
//                        position
//                    )
//                } else {
//                    (holder as SearchMovieViewHolder).bind(
//                        getTList()[position] as MovieResult,
//                        position
//                    )
//                }
//            }
//            3 -> {
//                if (mType == "person") {
//                    (holder as OtherMovieAndSerialImageViewHolder).bind(
//                        getTList()[position],
//                        position
//                    )
//                } else {
//                    (holder as SearchSerialsViewHolder).bind(
//                        getTList()[position] as SerialsResult,
//                        position
//                    )
//                }
//            }
//            else -> (holder as SearchViewHolder).bind(getTList()[position], position)
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (getTList()[position]::class) {
//            PersonResult::class -> 1
//            MovieResult::class -> 2
//            SerialsResult::class -> 3
//            else -> 0
//        }
//    }
//
//
//    fun getTList(): List<NetworkItem> {
//        return mTList
//    }
//
//    fun setTList(tList: List<NetworkItem>) {
//        mTList.clear()
//        mTList.addAll(tList)
//    }
//
//    fun setType(type: String) {
//        mType = type
//    }
//
//    override fun getItemCount(): Int {
//        return getTList().size
//    }
//}