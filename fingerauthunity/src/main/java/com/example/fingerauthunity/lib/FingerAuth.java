package com.example.fingerauthunity.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import static android.content.Context.FINGERPRINT_SERVICE;


/**
 * Created by TakeruDesk on 2017/05/30.
 */

public class FingerAuth {

    private static CancellationSignal cancel;

    @TargetApi(Build.VERSION_CODES.M)
    public static void AuthStart() {
        UnityPlayer.UnitySendMessage("FingerManager", "FingerAuthResult", "0");
        final Activity activity = UnityPlayer.currentActivity;
        FingerprintManager fingerprintManager = (FingerprintManager) activity.getSystemService(FINGERPRINT_SERVICE);
        cancel = new CancellationSignal();
        fingerprintManager.authenticate(null, cancel, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                UnityPlayer.UnitySendMessage("FingerManager", "FingerAuthResult", "1");
                cancel.cancel();
                Log.i("", "auth success");
            }

            @Override
            public void onAuthenticationFailed() {
                UnityPlayer.UnitySendMessage("FingerManager", "FingerAuthResult", "2");
                cancel.cancel();
                Log.e("", "failed");
            }
            /*
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                UnityPlayer.UnitySendMessage("FingerManager", "FingerAuthResult", "3");
                cancel.cancel();
                Log.e("", "error " + errorCode + " " + errString);
            }
            */
        }, new Handler());
    }
}
