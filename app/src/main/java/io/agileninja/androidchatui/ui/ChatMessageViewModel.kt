package io.agileninja.androidchatui.ui

import android.arch.lifecycle.ViewModel
import io.agileninja.androidchatui.model.ChatMessage

class ChatMessageViewModel(chatMessage: ChatMessage) : ViewModel() {
    val initial = chatMessage.name[0].toString()
    val name = chatMessage.name
    val message = chatMessage.message
}
