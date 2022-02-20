package org.study2.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

// SensorEventListener를 구현하도록 추가 그리고 임포트
class MainActivity : AppCompatActivity(), SensorEventListener{

//    가속도 센서 등록
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
}
    private lateinit var tiltView: TiltView

    override fun onCreate(savedInstanceState: Bundle?) {

//        화면이 가로 모드로 고정되게 하기 수퍼클래서의 생성자를 호출하기 전에 설정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//        화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        super.onCreate(savedInstanceState)

//          onCreate() 메서드에서 생성자에 this를 넘겨서 TiltView를 초기화합니다
        tiltView = TiltView(this)
//        기존 메인 대신 tiltView를 setContentVie() 메서드에 전달합니다 tiltView가 전체 레이아웃이 되었습니다
        setContentView(tiltView)

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL)
    }

//     센서 정밀도가 변경되면 호출됩니다.
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

//    센서값이 변경되면 호출됩니다
    override fun onSensorChanged(event: SensorEvent?) {
      /* values[0] : x 축 값 : 위로 기울이면 -10~0, 아래로 기울이면 0~10
      *  values[1] : y 축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
      *  values[2] : z 축 값 : 깊이값 미사용  */
        event?.let{
            Log.d("MainActivity","onSensorChanged" +
            "  x : ${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")

//            TiltView에 센서값 전달
            tiltView.onSensorEvent(event)
        }
    }

    override fun onPause() {
        super.onPause()
        /*unregisterListener 로 센서사용을 해제할수 있으며 인자로 SensorEventListener 객체를 지정합니다
        메인엑티비티에서 이 객체를 구현중이므로 this 를 지정합니다.*/
        sensorManager.unregisterListener(this)
    }
}