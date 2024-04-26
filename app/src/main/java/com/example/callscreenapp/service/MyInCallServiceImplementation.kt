package com.example.callscreenapp.service

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.telecom.Call
import android.telecom.InCallService
import com.example.callscreenapp.ui.activity.MyDialerActivity

class MyInCallServiceImplementation : InCallService() {
    companion object {
        const val ACTION_CALL_STATE_CHANGED = "com.example.callscreenapp.ACTION_CALL_STATE_CHANGED"
        const val EXTRA_CALL_STATE = "extra_call_state"
        var currentCall: Call? = null
    }

    private var ringtone: Ringtone? = null

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        currentCall = call
        ringtone = RingtoneManager.getRingtone(applicationContext, getDefaultRingtoneUri())
        ringtone?.play()
        startActivity(Intent(this, MyDialerActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or Intent.FLAG_ACTIVITY_NO_HISTORY
        })
        sendCallStateBroadcast(call.state)
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        currentCall = null
        ringtone?.stop()
        sendCallStateBroadcast(Call.STATE_DISCONNECTED)
    }

    private fun sendCallStateBroadcast(state: Int) = sendBroadcast(Intent(ACTION_CALL_STATE_CHANGED).apply {
        putExtra(EXTRA_CALL_STATE, state)
    })

    private fun getDefaultRingtoneUri(): Uri {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
    }

}