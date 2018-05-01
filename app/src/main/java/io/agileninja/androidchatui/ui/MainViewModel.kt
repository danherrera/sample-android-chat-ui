package io.agileninja.androidchatui.ui

import android.arch.lifecycle.MutableLiveData
import io.agileninja.androidchatui.R
import io.agileninja.androidchatui.model.ChatMessage

class MainViewModel : BaseViewModel(), LayoutViewModel {
    override val layout: Int = R.layout.fragment_chat

    private val typedText = MutableLiveData<String>()

    val name = "Danilo"
    val clearText = MutableLiveData<Unit>()
    val messageHistory = MutableLiveData<List<ChatMessageViewModel>>()
    val sendButtonEnabled = MutableLiveData<Boolean>()

    private fun clearText() {
        clearText.postValue(Unit)
    }

    fun clickSendButton() {
        typedText.value?.also { text ->
            messageHistory.postValue(
                    messageHistory.value.orEmpty() + listOf(ChatMessageViewModel(ChatMessage(name, text)))
            )
        }
        clearText()
    }

    fun typeText(text: String) {
        typedText.postValue(text)
        sendButtonEnabled.postValue(text.isNotEmpty())
    }
}
