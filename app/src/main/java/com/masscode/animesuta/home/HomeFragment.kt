package com.masscode.animesuta.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.masscode.animesuta.R
import com.masscode.animesuta.core.data.Resource
import com.masscode.animesuta.core.domain.model.Anime
import com.masscode.animesuta.core.ui.AnimeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        println("maduro - fragment - onCreate")
//        super.onCreate(savedInstanceState)
//    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        println("maduro - fragment - onActivityCreated")
//        super.onActivityCreated(savedInstanceState)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        println("maduro - fragment - onDestroy -------")
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        println("maduro - fragment - onAttach")
//    }
//
//    override fun onAttachFragment(childFragment: Fragment) {
//        super.onAttachFragment(childFragment)
//        println("maduro - fragment - onAttachFragment | $childFragment")
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        println("maduro - fragment - onDetach -------")
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        println("maduro - fragment - onDestroyView -------")
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        println("maduro - fragment - onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        println("maduro - fragment - onViewCreated")
//        homeViewModel.teste()
        if (activity != null) {
            val animeAdapter = AnimeAdapter { item -> showDetail(item) }

            homeViewModel.anime.observe(viewLifecycleOwner) { anime ->
                if (anime != null) {
                    println("maduro = ${anime.data?.size}")
                    when (anime) {
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            progress_bar.visibility = View.GONE
                            animeAdapter.setData(anime.data)
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            view_error.visibility = View.VISIBLE
                            tv_error.text = anime.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            with(rv_anime) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
                adapter = animeAdapter
            }
        }
    }

    private fun showDetail(anime: Anime) {
        Timber.d("OnClick : ${anime.canonicalTitle}")
        this.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailAnimeActivity(anime))
    }
}