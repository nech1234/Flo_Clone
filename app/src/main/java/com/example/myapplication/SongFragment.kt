package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.databinding.FragmentSongBinding


class SongFragment : Fragment() {

    private lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSongBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.songMixoffTg.setOnClickListener{
            setMixStatus(false)
        }
        binding.songMixonTg.setOnClickListener{
            setMixStatus(true)
        }
    }
    fun setMixStatus(isMixing:Boolean){
        if(!isMixing){
            binding.songMixoffTg.visibility= View.GONE
            binding.songMixonTg.visibility=View.VISIBLE
        }
        else{
            binding.songMixoffTg.visibility= View.VISIBLE
            binding.songMixonTg.visibility=View.GONE
        }
    }
}

