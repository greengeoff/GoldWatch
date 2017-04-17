import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class GoldWatchAppWidgetProvider extends AppWidgetProvider {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, GoldWatchAppWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Get the layout for the App Widget and attach an on-click listener
            // to it's icon

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.goldwatch_appwidget);

            views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    // the click events are handled by the onReceive method
    @Override
    public void onReceive(Context context, Intent intent) {

        fetchGoldPrice(context);
        super.onReceive(context, intent);
    }

    private void fetchGoldPrice(Context c) {

        c.startService(new Intent(c, GoldPriceService.class));

    }
}
