package tviz.tv.socialtest.fb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

public class FbCallback implements FacebookCallback<LoginResult> {
    private static final String TAG = FbCallback.class.getSimpleName();
    private Context mContext;

    public FbCallback(Context context) {
        mContext = context;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(mContext,
                "Logged via facebook as: " + Profile.getCurrentProfile().getName(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Log.e(TAG, "Facebook login cancelled");
    }

    @Override
    public void onError(FacebookException e) {
        Log.e(TAG, "Facebook login failed\n" + e.getMessage());
    }
}
