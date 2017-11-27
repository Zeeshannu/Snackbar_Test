package com.example.zeeshan.snackbar_test;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.*;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import com.example.zeeshan.snackbar_test.receiver.NetworkStateChangeReceiver;

import static com.example.zeeshan.snackbar_test.receiver.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE;

public class MainActivity extends AppCompatActivity {

CoordinatorLayout coordinatorLayout;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            coordinatorLayout=findViewById(R.id.coordinateLayout);
            IntentFilter intentFilter = new IntentFilter(NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
            LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                    String networkStatus = isNetworkAvailable ? "connected" : "disconnected";

                    final Snackbar snackbar;
                    snackbar = Snackbar.make(coordinatorLayout,"Network is : " +networkStatus,
                            Snackbar.LENGTH_LONG);
                    if(networkStatus.equals("connected")){
                        Snackbar snackbar1 = null;
                        snackbar1.make(coordinatorLayout,"Network is : Connected",Snackbar
                                .LENGTH_SHORT).show();


                    }
                    if(networkStatus.equals("disconnected")){


                        final Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

                        // Changing  text color
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.RED);


                        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                ViewGroup.LayoutParams lp = layout.getLayoutParams();
                                if (lp instanceof CoordinatorLayout.LayoutParams) {
                                    ((CoordinatorLayout.LayoutParams) lp).setBehavior(new DisableSwipeBehavior());
                                    layout.setLayoutParams(lp);
                                }
                            }
                        });
                        snackbar.show();
                    }
                }
            }, intentFilter);
        }
    public class DisableSwipeBehavior extends SwipeDismissBehavior<Snackbar.SnackbarLayout> {
        @Override
        public boolean canSwipeDismissView(@NonNull View view) {
            return false;
        }
    }

public boolean againCheckConnection(){
    boolean isConnectionFlag=false;
    ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

//mobile
    NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

//wifi
    NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

    if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
        Toast.makeText(this, "Mobile data is used", Toast.LENGTH_SHORT).show();
        isConnectionFlag=true;
        //mobile
    }else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
        //wifi
        isConnectionFlag=true;
        Toast.makeText(this, "Wifi data is used", Toast.LENGTH_SHORT).show();
    }
    if(!isConnectionFlag){
        Toast.makeText(this, "No any Connection", Toast.LENGTH_SHORT).show();
    }
return isConnectionFlag;
}
}


