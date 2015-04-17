package tviz.tv.socialtest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKUIHelper;

import io.fabric.sdk.android.Fabric;
import tviz.tv.socialtest.fb.FbCallback;
import tviz.tv.socialtest.twitter.TwitterCallback;
import tviz.tv.socialtest.vk.VkCallback;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private LoginButton fbBtn;
    private TwitterLoginButton twitterBtn;

    private CallbackManager fbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        fbCallbackManager = CallbackManager.Factory.create();

        TwitterAuthConfig authConfig =
                new TwitterAuthConfig("TWITTER_CONSUMER_KEY",
                        "TWITTER_CONSUMER_KEY_SECRET");
        Fabric.with(this, new TwitterCore(authConfig));

        VKUIHelper.onCreate(this);
        VKSdk.initialize(new VkCallback(this),
                "VK_APP_ID_HERE",
                VKAccessToken.tokenFromSharedPreferences(this, VkCallback.TOKEN_KEY));

        setContentView(R.layout.activity_main);


        fbBtn = (LoginButton) findViewById(R.id.loginBtnFb);
        fbBtn.setReadPermissions("user_friends");
        fbBtn.registerCallback(fbCallbackManager, new FbCallback(this));

        twitterBtn = (TwitterLoginButton)
                findViewById(R.id.loginBtnTwitter);
        twitterBtn.setCallback(new TwitterCallback(this));


        findViewById(R.id.loginBtnVK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.authorize(VkCallback.SCOPE, true, false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        fbCallbackManager.onActivityResult(requestCode, resultCode, data);

        twitterBtn.onActivityResult(requestCode, resultCode,
                data);

        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
