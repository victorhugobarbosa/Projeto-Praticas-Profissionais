package com.example.cotucatdpd;

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.fragment.app.Fragment
import androidx.gridlayout.widget.GridLayout
import com.example.cotucatdpd.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var iconDrawable = R.drawable.cobrinhascript
        var iconBitmap = BitmapFactory.decodeResource(resources, iconDrawable)

        val imageView = binding.btnCobra.apply {
            setImageBitmap(iconBitmap)
            tag = "btnCobra"
            setOnLongClickListener { v ->
                val item = ClipData.Item(v.tag as? CharSequence)

                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item)

                val myShadow = MyDragShadowBuilder(this)

                v.startDragAndDrop(dragData,  // The data to be dragged
                    myShadow,  // The drag shadow builder
                    null,      // No need to use local data
                    0          // Flags (not currently used, set to 0)
                )

                true
            }
        }

        imageView.setOnDragListener { v, e ->

            // Handles each of the expected events.
            when (e.action) {
                DragEvent.ACTION_DRAG_LOCATION ->
                    // Ignore the event.
                    true
                DragEvent.ACTION_DROP -> {
                    val item: ClipData.Item = e.clipData.getItemAt(0)

                    val dragData = item.text

                    (v as? ImageView)?.clearColorFilter()

                    v.invalidate()

                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    (v as? ImageView)?.clearColorFilter()

                    v.invalidate()
                    true
                }
                else -> {
                    Log.e("DragDrop Example", "Unknown action type received by View.OnDragListener.")
                    false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {

        val width: Int = view.width

        val height: Int = view.height

        shadow.setBounds(0, 0, width, height)

        size.set(width, height)

        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {

        shadow.draw(canvas)
    }
}
