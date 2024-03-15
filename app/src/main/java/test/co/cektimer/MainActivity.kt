package test.co.cektimer

import SonicCountDownTimerNew
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.progressindicator.CircularProgressIndicator
import test.co.cektimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: SonicCountDownTimerNew
    private lateinit var progressIndicator: CircularProgressIndicator

    private var timeSec: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        clickMethod()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        progressIndicator = findViewById(R.id.progress_indicator)

        initCountdownTimer()

        binding.tvTimerCountFinished.text = "\"00"
        binding.tvTimerCountTotal.text = "|\"$timeSec"
        binding.tvTitleSec.text = "$timeSec"
    }

    @SuppressLint("SetTextI18n")
    private fun clickMethod() {
        binding.btnPlaypauseTimer.setOnClickListener {
            if (countDownTimer.isTimerRunning()) {
                if (countDownTimer.isTimerPaused()) {
                    countDownTimer.resumeCountDownTimer()
                    binding.btnPlaypauseTimer.setImageResource(R.drawable.ic_baseline_pause_24)
                } else {
                    countDownTimer.pauseCountDownTimer()
                    binding.btnPlaypauseTimer.setImageResource(R.drawable.ic_baseline_play_24)
                }
            } else {
                countDownTimer.startCountDownTimer()
                binding.btnPlaypauseTimer.setImageResource(R.drawable.ic_baseline_pause_24)

            }

        }
        binding.btnAddSec.setOnClickListener {
            if (!countDownTimer.isTimerRunning()) {
                timeSec += 10
                progressIndicator.max = timeSec
                countDownTimer.setCountDownTime((timeSec * 1000).toLong())
                binding.tvTimerCountTotal.text = "|\"$timeSec"
                binding.tvTitleSec.text = "$timeSec"
            }

        }

        binding.btnReset.setOnClickListener {
            binding.btnPlaypauseTimer.setImageResource(R.drawable.ic_baseline_play_24)
            countDownTimer.stopCountDownTimer()
            timeSec = 10
            progressIndicator.max = timeSec
            progressIndicator.progress = 0
            binding.tvTimerCountFinished.text = "\"00"
            binding.tvTimerCountTotal.text = "|\"$timeSec"
            binding.tvTitleSec.text = "$timeSec"
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initCountdownTimer() {
        var mCount: Int
        progressIndicator.max = timeSec
        countDownTimer =
            object : SonicCountDownTimerNew((timeSec * 1000).toLong(), 1000) {

                override fun onTimerTick(timeRemaining: Long) {
                    mCount = timeSec - (timeRemaining / 1000).toInt()
                    Log.d("TAG", "onTimerTick: $mCount")
                    progressIndicator.progress = mCount
                    binding.tvTimerCountFinished.text = "\"$mCount"
                    binding.tvTimerCountTotal.text = "|\"$timeSec"

                }

                override fun onTimerFinish() {
                    binding.btnPlaypauseTimer.setImageResource(R.drawable.ic_baseline_play_24)

                    // Timer selesai secara normal
                    Log.d("TAG", "Timer finished normally")
                    SweetAlertDialog(this@MainActivity)
                        .setTitleText("Finish Alert")
                        .show()
                }


            }


    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancelCountDownTimer()
    }
}