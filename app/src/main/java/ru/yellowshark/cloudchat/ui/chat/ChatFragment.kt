package ru.yellowshark.cloudchat.ui.chat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.koin.android.viewmodel.ext.android.viewModel
import ru.yellowshark.cloudchat.R
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.databinding.FragmentChatBinding
import ru.yellowshark.cloudchat.domain.models.Message
import ru.yellowshark.cloudchat.domain.models.User
import ru.yellowshark.cloudchat.utils.restartActivity

class ChatFragment : Fragment(R.layout.fragment_chat), MenuItem.OnMenuItemClickListener {
    private val binding by viewBinding(FragmentChatBinding::bind)
    private val viewModel by viewModel<ChatViewModel>()
    private lateinit var firebaseAdapter: FirebaseRecyclerAdapter<Message, MessageViewHolder>
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.didLogin()) openSignIn()
        initLayoutManager()
        initAdapter()
        initListeners()
    }

    override fun onPause() {
        firebaseAdapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        firebaseAdapter.startListening()
        Handler().postDelayed({ showContent() }, 1000)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        FirebaseHelper.auth.signOut()
        FirebaseHelper.signInClient(requireContext()).signOut()
        requireActivity().restartActivity()
        return true
    }

    private fun initAdapter() {
        firebaseAdapter = FirebaseMessageAdapterFactory.create()
        registerDataObserver()
        binding.messagesRv.adapter = firebaseAdapter
    }

    private fun showContent() {
        with(binding) {
            chatContentCl.visibility = View.VISIBLE
            progressBarLayoutLl.root.visibility = View.GONE
        }
    }

    private fun registerDataObserver() {
        val observer = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val messageCount: Int = firebaseAdapter.itemCount
                val lastVisiblePosition: Int =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                    positionStart >= messageCount - 1 &&
                    lastVisiblePosition == positionStart - 1
                ) {
                    binding.messagesRv.scrollToPosition(positionStart)
                }
            }
        }
        firebaseAdapter.registerAdapterDataObserver(observer)
    }

    private fun initLayoutManager() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.messagesRv.layoutManager = linearLayoutManager
    }

    private fun openSignIn() {
        view?.let { Navigation.findNavController(it).navigate(R.id.destination_sign_in) }
    }

    private fun initListeners() {
        with(binding) {
            sendMsgBtn.setOnClickListener {
                val msg = newMessageEt.text.toString()
                if (msg.trim().isNotEmpty()) {
                    viewModel.sendMessage(newMessageEt.text.toString())
                    newMessageEt.setText("")
                }
            }
            toolbar.menu.findItem(R.id.action_logout).setOnMenuItemClickListener(this@ChatFragment)
        }
    }
}