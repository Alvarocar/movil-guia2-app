package dev.alvarocar.temperature_sensor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.alvarocar.temperature_sensor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var temperature: Sensor? = null
    private lateinit var binding: ActivityMainBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val temp = event.values[0].toString()
            binding.temperatureInfo.text = temp
            binding.container
                .setBackgroundColor(Color.parseColor(changeColorBackground(temp.toFloat())))
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun changeColorBackground(temp: Float): String {
        //-273..100
        return when(temp) {
            in -275f..-200f -> "#00BCD4"
            in -200f..-100f -> "#26C6DA"
            in -100f..-70f -> "#4DD0E1"
            in -70f..0f -> "#4FC3F7"
            in 0f..18f -> "#E0F7FA"
            in 18f..25f -> "#FFF3E0"
            in 25f..30f -> "#FFE0B2"
            in 30f..40f -> "#FFB74D"
            in 40f..50f -> "#FBE9E7"
            in 50f..70f -> "#FFCCBC"
            in 70f..101f -> "#FFAB91"
            else -> "#F5F5F5"
        }
    }
}