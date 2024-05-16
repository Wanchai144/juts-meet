package com.example.myapplication

import android.app.Activity
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.flow.collectLatest

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel: LiveKitMeetConferenceViewModel by viewModels {
        LiveKitMeetConferenceViewModelFactory(this)
    }



    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestNeededPermissions {
            setUpMeetConference()
        }
        viewModel.setUpConferenceMeet("wss://test-a76dbpxh.livekit.cloud")
    }

    override fun onClickListener() {

    }

    override fun initViewModel() {
        Log.d("TAG_initViewModel", "initViewModel: ")
        viewModel.cameraEnabled.observe(this) { enabled ->
            binding.ivVideo.setOnClickListener { viewModel.setCameraEnabled(!enabled) }
            binding.ivVideo.setImageResource(
                if (enabled) {
                    R.drawable.outline_videocam_24
                } else {
                    R.drawable.outline_videocam_off_24
                },
            )
//            binding.ivFlipCamera.isEnabled = enabled
        }

        viewModel.micEnabled.observe(this) { enabled ->
            binding.ivMicrophone.setOnClickListener { viewModel.setMicEnabled(!enabled) }
            binding.ivMicrophone.setImageResource(
                if (enabled) {
                    R.drawable.outline_mic_24
                } else {
                    R.drawable.outline_mic_off_24
                },
            )
        }
    }


    private fun setUpMeetConference() {
//        binding.tvRoomName.text = intent.getStringExtra(KEY_INPUT_ROOM_NAME)
        // Audience row setup
        val audienceAdapter = GroupieAdapter()
        binding.audienceRow.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = audienceAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.participants
                .collect { participants ->
                    val items = participants.map { participant ->
                        ParticipantItem(
                            viewModel.room,
                            participant
                        )
                    }
                    audienceAdapter.update(items)
                }
        }

        // speaker view setup
        val speakerAdapter = GroupieAdapter()
        binding.speakerView.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = speakerAdapter
        }
        lifecycleScope.launchWhenCreated {
            viewModel.primarySpeaker.collectLatest { speaker ->
                val items = listOfNotNull(speaker)
                    .map { participant ->
                        ParticipantItem(
                            viewModel.room,
                            participant,
                            speakerView = true
                        )
                    }
                speakerAdapter.update(items)
            }
        }
    }

    override fun onDestroy() {
        binding.audienceRow.adapter = null
        binding.speakerView.adapter = null
        super.onDestroy()
    }
}