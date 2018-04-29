package io.agileninja.androidchatui.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageButton
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.agileninja.androidchatui.R
import io.agileninja.androidchatui.getViewModel
import io.reactivex.subjects.PublishSubject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var messageEditText: EditText
    private lateinit var sendMessageButton: ImageButton
    private lateinit var chatRecyclerView: RecyclerView

    private val stopStreams = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageEditText = findViewById(R.id.messageEditText)
        sendMessageButton = findViewById(R.id.sendButton)
        chatRecyclerView = findViewById(R.id.chatRecyclerView)

        chatRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        val chatAdapter = ChatMessageAdapter()
        chatRecyclerView.adapter = chatAdapter

        viewModel = getViewModel(MainViewModel::class.java)

        viewModel.clearText.observe(this,
                Observer { messageEditText.setText("") })

        viewModel.messageHistory.observe(this,
                Observer {
                    chatAdapter.chatMessages = it?.map { ChatMessageViewModel(it) }
                            .let { it?.toMutableList() ?: mutableListOf() }
                })

        viewModel.sendButtonEnabled.observe(this,
                Observer { sendMessageButton.isEnabled = it ?: false })

        RxTextView
                .textChanges(messageEditText)
                .takeUntil(stopStreams)
                .subscribe { viewModel.typeText(it.toString()) }

        RxView
                .clicks(sendMessageButton)
                .takeUntil(stopStreams)
                .subscribe { viewModel.clickSendButton() }
    }


    override fun onDestroy() {
        stopStreams.onNext(Unit)
        super.onDestroy()
    }
}
