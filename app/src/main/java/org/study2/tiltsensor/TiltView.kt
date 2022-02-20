package org.study2.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    //    중심점
    private var cX: Float = 0f
    private var cY: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f


    init {
        greenPaint.color = Color.GREEN

        blackPaint.style = Paint.Style.STROKE
    }


    override fun onDraw(canvas: Canvas?) {
//        바깥 원
        canvas?.drawCircle(cX, cY, 100f, blackPaint)
//        녹색 원 xCoord yCoord 더해 좌표를 수정해줌
        canvas?.drawCircle(xCoord + cX, yCoord + cY, 100f, greenPaint)
//        가운데 십자가
        canvas?.drawLine(cX - 20, cY, cX + 20, cY, blackPaint)
        canvas?.drawLine(cX, cY - 20, cX, cY + 20, blackPaint)
        super.onDraw(canvas)
    }

    // 뷰의 크기가 변경될때호출됩니다. w,h 변경된가로세로길이 oldw,oldh 변경전 가로세로
//    중심점구하기 (width / 2, height / 2) = 중심점
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cX = w / 2f
        cY = h / 2f
    }

    //    SensorEvent 값을 인자로 받습니다.
    fun onSensorEvent(event: SensorEvent) {
        /* 화면에 방향을 가로 모드로 회전시켰기 때문에 x 축과 y 축을 서로 바꿔야 이해하기 편합니다
        * 20을 곱한 이유는 센서에 범위를 그대로 좌표로 사용하면 범위가 너무적어서 녹색원의 움직임을
        * 알아보기 어렵기 때문입니다*/
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20

//        invalidate()메서드는 뷰의 onDraw() 메서드를 다시 호출하는 메서드입니다 뷰를 다시그림
        invalidate()

    }
}