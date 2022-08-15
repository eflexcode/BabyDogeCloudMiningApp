package com.app.babydogecloudminingapp.mvc

interface CountDown {

    var time : Long


    fun timePassed(realTime : Long){
        time  = realTime
    }

}