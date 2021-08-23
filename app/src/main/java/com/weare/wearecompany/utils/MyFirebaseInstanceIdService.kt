package com.weare.wearecompany.utils

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstanceIdService: FirebaseMessagingService() {

    private val TAG = "InstanceIdService"

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    override fun onNewToken(p0: String) { //Added onNewToken method
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: $refreshedToken")
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String?) {
        //디바이스 토큰이 생성되거나 재생성 될 시 동작할 코드 작성
    }
}