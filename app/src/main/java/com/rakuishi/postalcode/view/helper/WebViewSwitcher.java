package com.rakuishi.postalcode.view.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.rakuishi.postalcode.view.activity.WebViewActivity;

public class WebViewSwitcher {

    public WebViewSwitcher() {

    }

    public static void launchUrl(Context context, String url) {
        Uri uri = Uri.parse(url);

        switch (uri.getScheme()) {
            case "http":
            case "https":
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, uri);
                break;
            case "file":
                Intent intent = WebViewActivity.newIntent(context, url);
                context.startActivity(intent);
                break;
        }
    }
}
