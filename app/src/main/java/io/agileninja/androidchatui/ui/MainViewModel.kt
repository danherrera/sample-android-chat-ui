package io.agileninja.androidchatui.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.agileninja.androidchatui.model.ChatMessage

class MainViewModel : ViewModel() {

    private val typedText = MutableLiveData<String>()

    val name = "Danilo"
    val clearText = MutableLiveData<Unit>()
    val messageHistory = MutableLiveData<MutableList<ChatMessage>>()
    val sendButtonEnabled = MutableLiveData<Boolean>()

    private fun clearText() {
        clearText.postValue(Unit)
    }

    fun clickSendButton() {
        typedText.value?.also { text ->
            messageHistory.postValue(
                    messageHistory.value?.apply {
                        add(0, ChatMessage(name, text))
                    } ?: mutableListOf(ChatMessage(name, text))
            )
        }
        clearText()
    }

    fun typeText(text: String) {
        typedText.postValue(text)
        sendButtonEnabled.postValue(text.isNotEmpty())
    }
}
