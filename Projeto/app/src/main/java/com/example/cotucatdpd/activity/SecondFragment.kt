package com.example.cotucatdpd.activity;

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment;
import com.example.cotucatdpd.GameActivity
import com.example.cotucatdpd.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    lateinit var extra : Bundle
    var nome = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(activity?.intent?.extras != null) {
            extra = activity?.intent?.extras!!
            nome = extra!!.getString("nickname").toString()
        }
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.btnPD.setOnClickListener {
            val intent = Intent(context, GameActivity::class.java)
            if(nome == "")
                intent.putExtra("nickname", "no-name")
            else
                intent.putExtra("nickname", nome)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
