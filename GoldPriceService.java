import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * 
 */
public class GoldPriceService extends IntentService {

    public GoldPriceService() {
        super("GoldPriceService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();

        }

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());
        final RemoteViews remoteViews = new RemoteViews(getBaseContext().getPackageName(), R.layout.goldwatch_appwidget);
        final ComponentName thisWidget = new ComponentName(getBaseContext(), GoldWatchAppWidgetProvider.class);
        System.out.println("in gold service");
        String url = "http://www.kitco.com/charts/livegold.html";
        String price = "0";

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        Date dt = new Date();



        try {
            final Document doc = Jsoup.connect(url).get();
            System.out.println(doc.text());
            price = doc.select("span#sp-bid").text();

            System.out.println("price: " + price);
            remoteViews.setTextViewText(R.id.textView, "$ " + price);
            remoteViews.setTextViewText(R.id.timeView, "@ " + sdf.format(dt));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }



}
