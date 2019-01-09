package com.example.android.bakingappudacity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int id = intent.getIntExtra("extra_id",0);
        return new ListRemoteViewFactory(this.getApplicationContext(),intent);
    }

}
