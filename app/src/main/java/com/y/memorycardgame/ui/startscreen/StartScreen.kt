package com.y.memorycardgame.ui.startscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.slider.Slider
import com.y.memorycardgame.R
import com.y.memorycardgame.app.App
import kotlinx.android.synthetic.main.fragment_start_screen.*
import moxy.MvpAppCompatFragment

import moxy.presenter.InjectPresenter

class StartScreen : MvpAppCompatFragment(R.layout.fragment_start_screen), StartScreenView{

    @InjectPresenter
    lateinit var presenter : StartScreenPresenter

    companion object {
        fun getInstance(): StartScreen = StartScreen()
    }

//    override fun onCreateView(
//        inflater: LayoutInflater?,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater?.inflate(R.layout.fragment_start_screen, container, false);
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.inject(this)

        btn_start_game.setOnClickListener { presenter.startGameClick()}

        sl_field_span_selector.addOnChangeListener { _: Slider, fl: Float, _: Boolean ->
            presenter.setFiledSpan(fl)
        }
    }

    override fun setSettingsSpan(span: Int) {
        sl_field_span_selector.value = span.toFloat()
    }
}