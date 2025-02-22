package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }
    private var currentVisibleView: String =
        METRIC_UNITS_VIEW
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarBmiActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //set back button
        supportActionBar?.title = "CALCULATE BMI" // Setting a title in the action bar.
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        makeVisibleMetricUnitsView()


        binding.rgUnits.setOnCheckedChangeListener { _, checkedId: Int ->

            // Here if the checkId is METRIC UNITS view then make the view visible else US UNITS view.
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding.btnCalculateUnits.setOnClickListener {
            calculateUnits()
        }



    }
    private fun calculateUnits(){
        // Handling the current visible view and calculating US UNITS view input values if they are valid.)
        // START
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            // The values are validated.
            if (validateMetricUnits()) {

                // The height value is converted to float value and divided by 100 to convert it to meter.
                val heightValue: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100

                // The weight value is converted to float value
                val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()

                // BMI value is calculated in METRIC UNITS using the height and weight value.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BmiActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {

            // The values are validated.
            if (validateUsUnits()) {

                val usUnitHeightValueFeet: String =
                    binding.etUsMetricUnitHeightFeet.text.toString()
                val usUnitHeightValueInch: String =
                    binding.etUsMetricUnitHeightInch.text.toString()
                val usUnitWeightValue: Float = binding.etUsMetricUnitWeight.text.toString()
                    .toFloat()


                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12


                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BmiActivity,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding.etUsMetricUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsMetricUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }
    // END
    /**
     * Function is used to display the result of METRIC UNITS.
     */
    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding.llDiplayBMIResult.visibility = VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }
    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = VISIBLE
        binding.tilMetricUnitHeight.visibility = VISIBLE
        binding.tilUsMetricUnitWeight.visibility = View.GONE
        binding.tilMetricUsUnitHeightFeet.visibility = View.GONE
        binding.tilMetricUsUnitHeightInch.visibility = View.GONE

        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()

        binding.llDiplayBMIResult.visibility = View.INVISIBLE
    }
    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitHeight.visibility =View.INVISIBLE
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilUsMetricUnitWeight.visibility = VISIBLE
        binding.tilMetricUsUnitHeightFeet.visibility = VISIBLE
        binding.tilMetricUsUnitHeightInch.visibility = VISIBLE

        binding.etUsMetricUnitWeight.text!!.clear()
        binding.etUsMetricUnitHeightFeet.text!!.clear()
        binding.etUsMetricUnitHeightInch.text!!.clear()

        binding.llDiplayBMIResult.visibility = View.INVISIBLE
    }

}