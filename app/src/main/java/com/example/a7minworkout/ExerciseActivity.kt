package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? =null
    private var restProgress =0
    private var restTimerDuration:Long=1

    private var exerciseTimer: CountDownTimer? =null
    private var exerciseProgress =0
    private var exerciseTimerDuration:Long=1

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var dialogBinding: DialogCustomBackConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBarExercise)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        exerciseList=Constants.defaultExerciseList()

        binding.toolBarExercise.setNavigationOnClickListener{
            customDialogForBackButton()
        }
        tts = TextToSpeech(this, this)

        setRestView()
        setUpExerciseStatusRV()
    }
    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        dialogBinding= DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()





    }


    private fun setUpExerciseStatusRV(){
        binding.rvExerciseStatus.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding.rvExerciseStatus.adapter=exerciseAdapter
    }
    private fun setRestView(){




        binding.flRestView.visibility=View.VISIBLE
        binding.textTittle.visibility=View.VISIBLE
        binding.restExerciseName.visibility=View.VISIBLE
        binding.tvUpComingExercise.visibility=View.VISIBLE
        binding.tvExerciseName.visibility=View.INVISIBLE
        binding.flExerciseView.visibility=View.INVISIBLE
        binding.ivImage.visibility=View.INVISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        binding.restExerciseName.text=exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }
    private fun setExerciseView(){
        binding.flRestView.visibility=View.INVISIBLE
        binding.textTittle.visibility=View.INVISIBLE
        binding.restExerciseName.visibility=View.INVISIBLE
        binding.tvUpComingExercise.visibility=View.INVISIBLE
        binding.tvExerciseName.visibility=View.VISIBLE
        binding.flExerciseView.visibility=View.VISIBLE
        binding.ivImage.visibility=View.VISIBLE
        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        //speakOut(exerciseList!![currentExercisePosition].getName())

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }
    private fun setRestProgressBar() {

        binding.progressBar.progress = restProgress // Sets the current progress to the specified value.


        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++ // It is increased by 1
                binding.progressBar.progress = 10 - restProgress // Indicates progress bar progress
                binding.tvTimer.text = (10 - restProgress).toString()  // Current progress is set to text view in terms of seconds.
                //if(restProgress==9) speakOut(exerciseList!![currentExercisePosition+1].getName())
//                if(restProgress==0){
//                    try{
//                        val soundURI= Uri.parse(
//                            "android.resource://com.example.a7minworkout/"+R.raw.press_start)
//                        player=MediaPlayer.create(applicationContext,soundURI)
//                        player?.isLooping=false
//                        player?.start()
//                    }catch (e:Exception){
//                        e.printStackTrace()
//                    }
//
//                }
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true) // Current Item is selected
                exerciseAdapter?.notifyDataSetChanged()
                //Toast.makeText(this@ExerciseActivity,"Here now we will start the exercise.",Toast.LENGTH_SHORT).show()
                setExerciseView()

            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }


    }
    private fun setExerciseProgressBar() {

        binding.progressBarExercise.progress = exerciseProgress // Sets the current progress to the specified value.


        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++ // It is increased by 1
                binding.progressBarExercise.progress = 30 - exerciseProgress // Indicates progress bar progress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString() // Current progress is set to text view in terms of seconds.
                //if(exerciseProgress>=1)speakOut((30-exerciseProgress).toString())
            }

            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false) // exercise is completed so selection is set to false
                    exerciseList!![currentExercisePosition].setIsCompleted(true) // updating in the list that this exercise is completed
                    exerciseAdapter?.notifyDataSetChanged()
                    setRestView()
                }
                else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)                }

            }

        }.start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }

    }
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}