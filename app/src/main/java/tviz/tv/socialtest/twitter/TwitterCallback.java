package tviz.tv.socialtest.twitter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class TwitterCallback extends Callback<TwitterSession> {
    private static final String TAG = TwitterCallback.class.getSimpleName();
    private Context mContext;

    public TwitterCallback(Context context) {
        mContext = context;
    }

    @Override
    public void success(Result<TwitterSession> result) {
        Toast.makeText(mContext,
                "Logged via twitter as: " + result.data.getUserName(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(TwitterException exception) {
        Toast.makeText(mContext,
                "Twitter - failed to login",
                Toast.LENGTH_SHORT).show();
        Log.e(TAG, exception.getMessage());
    }
}
