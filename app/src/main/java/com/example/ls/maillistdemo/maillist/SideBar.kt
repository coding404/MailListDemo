package com.example.ls.maillistdemo.maillist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.example.ls.maillistdemo.R

/**
 * Created by LS on 2017/12/29.
 */

class SideBar(context: Context, attrs: AttributeSet?, defStyle: Int) : View(context, attrs, defStyle) {
    // 触摸事件
    private var onTouchingLetterChangedListener: OnTouchingLetterChangedListener? = null
    private var choose = -1// 选中
    private val paint = Paint()
    //按下后的背景颜色
    private var bgPressColor: Int? = 0
    //手指离开后的背景颜色
    private var bgUnPressColor: Int? = 0
    //选中的字体颜色
    private var textSelectColor: Int? = 0;
    //未选中的字体颜色
    private var textUnSelecColor: Int? = 0;

    private var mTextDialog: TextView? = null
    //判断是否按住的状态，true为按住
    private var isPress = false

    fun setTextView(mTextDialog: TextView) {
        this.mTextDialog = mTextDialog
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context) : this(context, null, 0) {}

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideBar)
            bgPressColor = typedArray.getColor(R.styleable.SideBar_backgroundPress, resources.getColor(R.color.bgPressColor))
            bgUnPressColor = typedArray.getColor(R.styleable.SideBar_backgroundUnPress, resources.getColor(R.color.bgUpColor))
            textSelectColor = typedArray.getColor(R.styleable.SideBar_textcolorSelect, resources.getColor(R.color.textSelectColor))
            textUnSelecColor = typedArray.getColor(R.styleable.SideBar_textColorUnSelect, resources.getColor(R.color.textUnSelectColor))
        } else {
            bgPressColor = resources.getColor(R.color.bgPressColor)
            bgUnPressColor = resources.getColor(R.color.bgUpColor)
            textSelectColor = resources.getColor(R.color.textSelectColor)
            textUnSelecColor = resources.getColor(R.color.textUnSelectColor)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = height// 获取对应高度
        val width = width // 获取对应宽度
        val singleHeight = height / b.size// 获取每一个字母的高度

        for (i in b.indices) {
            paint.color = textUnSelecColor!!
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.isAntiAlias = true
            paint.textSize = 22f
            // 选中的状态
            if (i == choose) {
                paint.color = textSelectColor!!
                paint.isFakeBoldText = true
            }
            // x坐标等于中间-字符串宽度的一半.
            val xPos = width / 2 - paint.measureText(b[i]) / 2
            val yPos = (singleHeight * i + singleHeight).toFloat()
            canvas.drawText(b[i], xPos, yPos, paint)
            paint.reset()// 重置画笔
        }
        setBackgroundColor(if (isPress) {
            bgPressColor!!
        } else {
            bgUnPressColor!!
        })

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val y = event.y// 点击y坐标
        val oldChoose = choose
        val listener = onTouchingLetterChangedListener
        val c = (y / height * b.size).toInt()// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        when (action) {
            MotionEvent.ACTION_UP -> {
                isPress = false
                choose = -1//重置选中项
                invalidate()
                if (mTextDialog != null) {
                    mTextDialog!!.visibility = View.INVISIBLE
                }
            }
            else -> {
                isPress = true
                if (oldChoose != c) {
                    if (c >= 0 && c < b.size) {
                        listener?.onTouchingLetterChanged(b[c])
                        if (mTextDialog != null) {
                            mTextDialog!!.text = b[c]
                            mTextDialog!!.visibility = View.VISIBLE
                        }
                        choose = c
                        invalidate()
                    }
                }
            }
        }
        return true
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    fun setOnTouchingLetterChangedListener(
            onTouchingLetterChangedListener: OnTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener
    }

    /**
     * 接口
     *
     * @author coder
     */
    interface OnTouchingLetterChangedListener {
        fun onTouchingLetterChanged(s: String)
    }

    companion object {
        // 26个字母
        var b = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")
    }

}
