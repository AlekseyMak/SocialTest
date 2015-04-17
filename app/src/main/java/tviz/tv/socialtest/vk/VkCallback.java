package tviz.tv.socialtest.vk;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.dialogs.VKCaptchaDialog;

public class VkCallback extends VKSdkListener {
    public static final String TOKEN_KEY = "VK_ACCESS_TOKEN";
    public static final String[] SCOPE = new String[] {
            VKScope.FRIENDS,
            VKScope.NOHTTPS
    };

    private Context mContext;

    public VkCallback(Context context) {
        mContext = context;
    }

    @Override
    public void onCaptchaError(VKError captchaError) {
        new VKCaptchaDialog(captchaError).show();
    }

    @Override
    public void onTokenExpired(VKAccessToken expiredToken) {
        VKSdk.authorize(SCOPE);
    }

    @Override
    public void onAccessDenied(VKError authorizationError) {
        new AlertDialog.Builder(VKUIHelper.getTopActivity())
                .setMessage(authorizationError.toString())
                .show();
    }

    @Override
    public void onReceiveNewToken(VKAccessToken newToken) {
        newToken.saveTokenToSharedPreferences(mContext, TOKEN_KEY);

        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUser user = ((VKList<VKApiUser>)response.parsedModel).get(0);
                Toast.makeText(mContext,
                        "Logged via vk as: " + user.first_name + " " + user.last_name,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onAcceptUserToken(VKAccessToken token) {
        VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUser user = ((VKList<VKApiUser>) response.parsedModel).get(0);
                Toast.makeText(mContext,
                        "Logged via vk as: " + user.first_name + " " + user.last_name,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
