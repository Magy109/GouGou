package com.example.gougou.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Costo de pasaje de la combi"+
        "\n"+
                "\n"+
        "\nGeneral........$8.00"+
        "\nEstudiantes...$5.50"+
        "\n3ra.Edad........$4.00"+
        "\nDiscapacitados.$0.0"
    }
    val text: LiveData<String> = _text
}