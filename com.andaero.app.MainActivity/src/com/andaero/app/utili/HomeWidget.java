package com.andaero.app.utili;

import com.andaero.app.MainActivity;
import com.andaero.app.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class HomeWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget);
		// When we click the widget, we want to open our main activity.
		Intent launchActivity = new Intent(context, MainActivity.class);
		launchActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchActivity, 0);
		remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);;

		ComponentName thisWidget = new ComponentName(context, HomeWidget.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thisWidget, remoteViews);
	}
}
