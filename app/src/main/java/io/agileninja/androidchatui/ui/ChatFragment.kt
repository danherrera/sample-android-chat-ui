package io.agileninja.androidchatui.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.agileninja.androidchatui.R
import io.reactivex.subjects.PublishSubject


class ChatFragment : BaseFragment<MainViewModel>() {
    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    private lateinit var messageEditText: EditText
    private lateinit var sendMessageButton: ImageButton
    private lateinit var chatRecyclerView: RecyclerView

    private val stopStreams = PublishSubject.create<Unit>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        messageEditText = view.findViewById(R.id.messageEditText)
        sendMessageButton = view.findViewById(R.id.sendButton)
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)

        chatRecyclerView.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        val chatAdapter = LayoutViewModelAdapter()
        chatRecyclerView.adapter = chatAdapter

        super.onViewCreated(view, savedInstanceState)

        viewModel.clearText.observe(this,
                Observer { messageEditText.setText("") })

        viewModel.messageHistory.observe(this,
                Observer {
                    chatAdapter.viewModels = it?.toList()
                            ?.also { chatRecyclerView.smoothScrollToPosition(it.size) }
                            ?: emptyList()
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

    override fun onStop() {
        stopStreams.onNext(Unit)
        super.onStop()
    }
}
