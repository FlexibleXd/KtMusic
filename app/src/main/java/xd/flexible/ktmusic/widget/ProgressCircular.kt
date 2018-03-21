package xd.flexible.ktmusic.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import flexible.xd.android_base.utils.ScreenUtils

/**
 * Created by Flexible on 2018/3/5 0005.
 */
class ProgressCircular : View {
    private lateinit var redPaint: Paint
    private lateinit var grayPaint: Paint
    private lateinit var trianglePaint: Paint
    private var isPlaying = false
    var progress: Int = 0
        set(value) {
            field = value
            invalidate()
        }
    private var radius = 0f
    private var mHeight = 0
    private var mWidth = 0
    private var stroke = 1f
    private var sWidth = ScreenUtils.getScreenWidth()
    private var paddingVertical: Float = 20f

    constructor(context: Context) : super(context) {
        initPaint()
    }

    constructor(context: Context, mAttributeSet: AttributeSet) : super(context, mAttributeSet) {
        initPaint()
    }


    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }


    private fun initPaint() {
        //红色圆弧画笔
        redPaint = Paint()
        redPaint.strokeWidth = stroke
        redPaint.color = Color.RED
        redPaint.style = Paint.Style.STROKE
        redPaint.isAntiAlias = true
        //外圈正圆画笔
        grayPaint = Paint()
        grayPaint.color = Color.GRAY
        grayPaint.strokeWidth = stroke
        grayPaint.style = Paint.Style.STROKE
        grayPaint.isAntiAlias = true
        //三角形画笔
        trianglePaint = Paint()
        trianglePaint.color = Color.GRAY
        trianglePaint.strokeWidth = stroke
        trianglePaint.style = Paint.Style.STROKE
        trianglePaint.isAntiAlias = true
    }


    @SuppressLint("SwitchIntDef")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
//        val heightMode = View.MeasureSpec.getMode(widthMeasureSpec)
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        radius = mHeight / 2 - paddingVertical
//        when (widthMode) {
//            MeasureSpec.AT_MOST -> {
//                mWidth = layoutParams.width
//            }
//            MeasureSpec.EXACTLY -> mWidth = width
//        }
//        when (heightMode) {
//            MeasureSpec.AT_MOST -> mHeight = layoutParams.height
//            MeasureSpec.EXACTLY -> mHeight = mHeight
//        }
        setMeasuredDimension(mWidth, mHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        var recf: RectF = RectF(sWidth - 10 - radius * 2, paddingVertical, sWidth - 10.toFloat() + paddingRight, height - paddingVertical)
//        var recf: RectF = RectF(left.toFloat(), paddingVertical, left + radius * 2, paddingVertical + radius * 2)
//        canvas!!.drawCircle(left + radius, -top - radius, radius, grayPaint)
        canvas!!.drawArc(recf, 0f, 360f, false, grayPaint)
        canvas!!.drawArc(recf, -90f, progress * 360 / 100f, false, redPaint)

        if (isPlaying) {
            val path = Path()
            path.moveTo(sWidth  -10- radius - 16, paddingVertical + radius - 18f)
            path.lineTo(sWidth-10 - radius - 16, paddingVertical + radius + 18f)
            path.moveTo(sWidth -10- radius + 16, paddingVertical + radius - 18f)
            path.lineTo(sWidth -10 - radius + 16, paddingVertical + radius + 18f)
            canvas.drawPath(path, redPaint)
        } else {
            val path = Path()
            path.moveTo(sWidth - 5 - radius - 16, paddingVertical + radius - 18f)
            path.lineTo(sWidth - 5 - radius - 16, paddingVertical + radius + 18f)
            path.lineTo(sWidth - 5 - radius + 16, paddingVertical + radius)
            path.close()
            canvas.drawPath(path, trianglePaint)
        }
    }

    fun play() {
        isPlaying = true
        invalidate()
    }

    fun stop() {
        isPlaying = false
        invalidate()
    }

}