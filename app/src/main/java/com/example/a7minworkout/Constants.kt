package com.example.a7minworkout

object Constants {

    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList= ArrayList<ExerciseModel>()
        val jumpingJacks=ExerciseModel(1,"Jumping Jacks",R.drawable.ic_jumping_jacks,false,false)
        exerciseList.add(jumpingJacks)
        val wallSit=ExerciseModel(2,"Wall Sit",R.drawable.ic_wall_sit, isCompleted = false, isSelected = false)
        exerciseList.add(wallSit)
        val abdominalCrunch=ExerciseModel(3,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,false,false)
        exerciseList.add(abdominalCrunch)
        val stepUpOnChair=ExerciseModel(4,"Step-Up onto Chair",R.drawable.ic_step_up_onto_chair,false,false)
        exerciseList.add(stepUpOnChair)
        val squat=ExerciseModel(5,"Squat",R.drawable.ic_squat,false,false)
        exerciseList.add(squat)
        val tricepsDip=ExerciseModel(6,"Triceps Dip",R.drawable.ic_triceps_dip_on_chair,false,false)
        exerciseList.add(tricepsDip)
        val plank=ExerciseModel(7,"Plank",R.drawable.ic_plank,false,false)
        exerciseList.add(plank)
        val highKnees=ExerciseModel(8,"High Knees",R.drawable.ic_high_knees_running_in_place,false,false)
        exerciseList.add(highKnees)
        val sidePlank=ExerciseModel(9,"Side Plank",R.drawable.ic_side_plank,false,false)
        exerciseList.add(sidePlank)
        val pushUp=ExerciseModel(10,"Push Up",R.drawable.ic_push_up,false,false)
        exerciseList.add(pushUp)
        val lunges=ExerciseModel(11,"Lunges",R.drawable.ic_lunge,false,false)
        exerciseList.add(lunges)
        val pushUpRotation=ExerciseModel(12,"Push Up and Rotation",R.drawable.ic_push_up_and_rotation,false,false)
        exerciseList.add(pushUpRotation)

        return exerciseList



    }
}