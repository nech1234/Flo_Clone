package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.gson.Gson


class HomeFragment : Fragment(), CommunicationInterface {

    private lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.homeAlbumImgIv1.setOnClickListener {
//            (context as MainActivity)
//                .supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
//        }
        inputDummyAlbums()
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())


        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayClick(album: Album) {
                sendData(album)
            }
        })


        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pannelVPAdapter = PannelVPAdapter(this)
        pannelVPAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelVPAdapter.addFragment(PannelFragment(R.drawable.img_first_album_default))
        binding.homePannelBackgroundVp.adapter = pannelVPAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeBannelIndicator.setViewPager(binding.homeBannerVp)
        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)
    }

    override fun sendData(album: Album) {
        if (activity is MainActivity) {
            val activity = activity as MainActivity
            activity.updataMainPlayerCl(album)
        }
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity)
            .supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val songs = songDB.albumDao().getAlbums()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                1,
                "Lilac",
                "아이유 (IU)",
                R.drawable.img_album_exp2,

                )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "Flu",
                "태연 (Tae Yeon)",
                R.drawable.img_album_exp6,

                )
        )
        songDB.albumDao().insert(
            Album(
                3,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp,

                )
        )
        songDB.albumDao().insert(
            Album(
                4,
                "Next Level",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3,

                )
        )
        songDB.albumDao().insert(
            Album(
                5,
                "Boy with Luv",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp5,

                )
        )
        songDB.albumDao().insert(
            Album(
                6,
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5,

                )
        )
        val songDBData = songDB.albumDao().getAlbums()
        Log.d("DB data", songDBData.toString())
    }

}