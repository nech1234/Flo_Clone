package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentLookBinding


class LookFragment : Fragment() {

    private lateinit var binding: FragmentLookBinding

    private lateinit var chartBtn: Button
    private lateinit var videoBtn: Button
    private lateinit var genreBtn: Button
    private lateinit var situationBtn: Button
    private lateinit var audioBtn: Button
    private lateinit var atmostphereBtn: Button

    private lateinit var buttonList: List<Button>

    private lateinit var chartTv: TextView
    private lateinit var videoTv: TextView
    private lateinit var genreTv: TextView
    private lateinit var situationTv: TextView
    private lateinit var audioTv: TextView
    private lateinit var atmostphereTv: TextView

    private lateinit var textList: List<TextView>

    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 스크롤 뷰 초기화
        scrollView = binding.lookSv

        // 버튼 초기화
        chartBtn = binding.lookChartBtn
        videoBtn = binding.lookVideoBtn
        genreBtn = binding.lookGenreBtn
        situationBtn = binding.lookSituationBtn
        audioBtn = binding.lookAudioBtn
        atmostphereBtn = binding.lookAtmostphereBtn

        buttonList = listOf(chartBtn, videoBtn, genreBtn, situationBtn, audioBtn, atmostphereBtn)

        // 텍스트 초기화
        chartTv = binding.lookChartTv
        videoTv = binding.lookVideoTv
        genreTv = binding.lookGenreTv
        situationTv = binding.lookSituationTv
        audioTv = binding.lookAudioTv
        atmostphereTv = binding.lookAtmostphereTv

        textList = listOf(chartTv, videoTv, genreTv, situationTv, audioTv, atmostphereTv)

        chartBtn.setOnClickListener {
            initButton(0)
        }

        videoBtn.setOnClickListener {
            initButton(1)
        }

        genreBtn.setOnClickListener {
            initButton(2)
        }

        situationBtn.setOnClickListener {
            initButton(3)
        }

        audioBtn.setOnClickListener {
            initButton(4)
        }

        atmostphereBtn.setOnClickListener {
            initButton(5)
        }
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.lookChartSongRv.layoutManager = LinearLayoutManager(requireActivity())
        val lookAlbumRVAdapter = LockerAlbumRVAdapter()


        binding.lookChartSongRv.adapter = lookAlbumRVAdapter
        val songDB = SongDatabase.getInstance(requireActivity())
        if (songDB != null) {
            lookAlbumRVAdapter.addSongs(songDB.songDao().getSongs() as ArrayList<Song>)
        } else {
            Log.e("SongDatabase", "Database is null. Cannot perform database operations.")
        }
    }

    private fun initButton(idx: Int) {
        for (i: Button in buttonList) { // 모든 버튼을 not_select로 초기화
            i.setBackgroundResource(R.drawable.not_selected_button)
        }
        buttonList[idx].setBackgroundResource(R.drawable.selected_button)
        scrollView.smoothScrollTo(0, textList[idx].top)
    }
}