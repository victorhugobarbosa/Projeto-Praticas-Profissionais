package com.example.cotucatdpd;

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.gridlayout.widget.GridLayout
import com.example.cotucatdpd.databinding.FragmentThirdBinding


class ThirdFragment : Fragment(), SurfaceHolder.Callback {

    private var _binding: FragmentThirdBinding? = null

    private val binding get() = _binding!!

    lateinit var surfaceHolder: SurfaceHolder

    lateinit var loop: GameLoop

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        surfaceHolder = binding.surfaceView.holder
        //surfaceHolder.addCallback(this)

        loop = GameLoop(this, surfaceHolder)
        binding.surfaceView.focusable = 1

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        loop.startLoop()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    fun draw(canvas: Canvas){
        binding.surfaceView.draw(canvas)
    }

    fun update(){

    }

}
