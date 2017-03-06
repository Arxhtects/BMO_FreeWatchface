
package archtectsproductions.bmo_freewatchface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class WatchfaceConfigActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private int time_set_format = 0x1;
    private int display_face_int = 0x1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watchface_config);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();


        RadioGroup timesetting = (RadioGroup) findViewById(R.id.rg_time);
        timesetting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged (RadioGroup timesetting, int i) {
                switch (i) {
                    default:
                    case R.id.r12hr:
                        time_set_format = 0x1;
                        break;
                    case R.id.r24hr:
                        time_set_format = 0x2;
                        break;
                }
                sendParamsAndFinish();
            }
        });

        RadioGroup face_setting = (RadioGroup) findViewById(R.id.rg_face);
        face_setting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged (RadioGroup face_setting, int e) {
                switch (e) {
                    default:
                    case R.id.rhappy:
                        display_face_int = 0x1;
                        break;
                    case R.id.rsleep:
                        display_face_int = 0x2;
                        break;
                    case R.id.rcheerful:
                        display_face_int = 0x3;
                        break;
                    case R.id.rconfused:
                        display_face_int = 0x4;
                        break;
                    case R.id.rdevious:
                        display_face_int = 0x5;
                        break;
                    case R.id.rproud:
                        display_face_int = 0x6;
                        break;
                    case R.id.rreally:
                        display_face_int = 0x7;
                        break;
                    case R.id.rterrified:
                        display_face_int = 0x8;
                        break;
                    case R.id.runeasy:
                        display_face_int = 0x9;
                        break;
                }
                sendParamsAndFinish_1();
            }
        });


    }




    // sends data through Google API
    private void sendParamsAndFinish() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/watch_face_config_cliu");
        putDataMapReq.getDataMap().putInt("time_format", time_set_format);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

        finish();
    }


    // sends data through Google API
    private void sendParamsAndFinish_1() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/watch_face_config_cliu_1");
        putDataMapReq.getDataMap().putInt("display_face", display_face_int);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);

        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

}