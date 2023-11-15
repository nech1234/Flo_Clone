package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private var gson: Gson = Gson()

    private var isLiked: Boolean = false

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        isLiked = isLikedAlbum(album.id)
        setInit(album)
        setOnClickListener(album)

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text = album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
    }

    private fun getJwt(): Int {
        val spf = requireActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf.getInt("jwt", 0)
    }

    private fun likeAlbum(userId: Int, albumId: Int) {
        val likeDB = SongDatabase.getInstance(requireActivity())!!
        val like = Like(userId, albumId)

        likeDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean {
        val likeDB = SongDatabase.getInstance(requireActivity())
        val userId = getJwt()
        return if (likeDB != null) {
            val likeId: Int? = likeDB.albumDao().isLikedAlbum(userId, albumId)
            likeId != null
        } else {
            Log.e("SongDatabase", "Database is null. Cannot perform database operations.")
            false
        }
    }

    private fun disLikeAlbum(albumId: Int) {
        val likeDB = SongDatabase.getInstance(requireActivity())!!
        val userId = getJwt()
        likeDB.albumDao().disLikedAlbum(userId, albumId)

    }

    private fun setOnClickListener(album: Album) {
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if (isLiked) {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikeAlbum(album.id)
            } else {
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }
}
