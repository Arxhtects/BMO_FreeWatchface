//copyright of archtectsproductions 2017, do not redistribute this code as your own. This code is here for your own learning.

package archtectsproductions.bmo_freewatchface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BMO extends CanvasWatchFaceService {

    Bitmap mBackgroundimage;
    Bitmap mambient_Backgroundimage;
    Bitmap mBackgroundScaledBitmap;
    Bitmap mBackgroundScaledBitmap_ambient;

    Bitmap face_changeable_1;
    Bitmap face_changeable_2;
    Bitmap face_changeable_3;
    Bitmap face_changeable_4;
    Bitmap face_changeable_5;
    Bitmap face_changeable_6;
    Bitmap face_changeable_7;
    Bitmap face_changeable_8;
    Bitmap face_changeable_9;


    Bitmap face_changeable_1_bient;
    Bitmap face_changeable_2_bient;
    Bitmap face_changeable_3_bient;
    Bitmap face_changeable_4_bient;
    Bitmap face_changeable_5_bient;
    Bitmap face_changeable_6_bient;
    Bitmap face_changeable_7_bient;
    Bitmap face_changeable_8_bient;
    Bitmap face_changeable_9_bient;

    Bitmap face_scaledBitmap_1;
    Bitmap face_scaledBitmap_2;
    Bitmap face_scaledBitmap_3;
    Bitmap face_scaledBitmap_4;
    Bitmap face_scaledBitmap_5;
    Bitmap face_scaledBitmap_6;
    Bitmap face_scaledBitmap_7;
    Bitmap face_scaledBitmap_8;
    Bitmap face_scaledBitmap_9;

    Bitmap face_scaledBitmap_1_bient;
    Bitmap face_scaledBitmap_2_bient;
    Bitmap face_scaledBitmap_3_bient;
    Bitmap face_scaledBitmap_4_bient;
    Bitmap face_scaledBitmap_5_bient;
    Bitmap face_scaledBitmap_6_bient;
    Bitmap face_scaledBitmap_7_bient;
    Bitmap face_scaledBitmap_8_bient;
    Bitmap face_scaledBitmap_9_bient;
    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);

    private static final int MSG_UPDATE_TIME = 0;

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private static class EngineHandler extends Handler {
        private final WeakReference<BMO.Engine> mWeakReference;

        public EngineHandler(BMO.Engine reference) {
            mWeakReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            BMO.Engine engine = mWeakReference.get();
            if (engine != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        engine.handleUpdateTimeMessage();
                        break;
                }
            }
        }
    }

    private class Engine extends CanvasWatchFaceService.Engine implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        private GoogleApiClient mGoogleApiClient;

        int time_set_format;
        int display_face_int;

        final Handler mUpdateTimeHandler = new EngineHandler(this);
        boolean mRegisteredTimeZoneReceiver = false;
        Paint mBackgroundPaint;
        Paint mTextPaint;
        Paint mTextPaint_1;
        boolean mAmbient;
        Calendar mCalendar;
        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mCalendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            }
        };
        float mXOffset;
        float mYOffset;

        float mXOffset_min;
        float mYOffset_min;

        float mYOffset_battery;

        float mYOffset_background;


        boolean mLowBitAmbient;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            Resources resources = BMO.this.getResources();

            Drawable backgroundDrawable = resources.getDrawable(R.drawable.bmo_background, null);
            mBackgroundimage = ((BitmapDrawable) backgroundDrawable).getBitmap();

            Drawable ambient_backgroundDrawable = resources.getDrawable(R.drawable.ambient, null);
            mambient_Backgroundimage = ((BitmapDrawable) ambient_backgroundDrawable).getBitmap();

            Drawable face_happy = resources.getDrawable(R.drawable.happy_face, null);
            Drawable face_asleep = resources.getDrawable(R.drawable.asleep_face, null);
            Drawable face_cheerful = resources.getDrawable(R.drawable.cheerful_face, null);
            Drawable face_confused = resources.getDrawable(R.drawable.confused_face, null);
            Drawable face_devious = resources.getDrawable(R.drawable.devious_face, null);
            Drawable face_proud = resources.getDrawable(R.drawable.proud_face, null);
            Drawable face_really = resources.getDrawable(R.drawable.really_face, null);
            Drawable face_terrifed = resources.getDrawable(R.drawable.terrified_face, null);
            Drawable face_uneasy = resources.getDrawable(R.drawable.uneasy_face, null);


            Drawable face_happy_bient = resources.getDrawable(R.drawable.happy_face_bient, null);
            Drawable face_asleep_bient = resources.getDrawable(R.drawable.asleep_face_bient, null);
            Drawable face_cheerful_bient = resources.getDrawable(R.drawable.cheerful_face_bient, null);
            Drawable face_confused_bient = resources.getDrawable(R.drawable.confused_face_bient, null);
            Drawable face_devious_bient = resources.getDrawable(R.drawable.devious_face_bient, null);
            Drawable face_proud_bient = resources.getDrawable(R.drawable.proud_face_bient, null);
            Drawable face_really_bient = resources.getDrawable(R.drawable.really_face_bient, null);
            Drawable face_terrifed_bient = resources.getDrawable(R.drawable.terrified_face_bient, null);
            Drawable face_uneasy_bient = resources.getDrawable(R.drawable.uneasy_face_bient, null);


            face_changeable_1 = ((BitmapDrawable) face_happy).getBitmap();
            face_changeable_2 = ((BitmapDrawable) face_asleep).getBitmap();
            face_changeable_3 = ((BitmapDrawable) face_cheerful).getBitmap();
            face_changeable_4 = ((BitmapDrawable) face_confused).getBitmap();
            face_changeable_5 = ((BitmapDrawable) face_devious).getBitmap();
            face_changeable_6 = ((BitmapDrawable) face_proud).getBitmap();
            face_changeable_7 = ((BitmapDrawable) face_really).getBitmap();
            face_changeable_8 = ((BitmapDrawable) face_terrifed).getBitmap();
            face_changeable_9 = ((BitmapDrawable) face_uneasy).getBitmap();


            face_changeable_1_bient = ((BitmapDrawable) face_happy_bient).getBitmap();
            face_changeable_2_bient = ((BitmapDrawable) face_asleep_bient).getBitmap();
            face_changeable_3_bient = ((BitmapDrawable) face_cheerful_bient).getBitmap();
            face_changeable_4_bient = ((BitmapDrawable) face_confused_bient).getBitmap();
            face_changeable_5_bient = ((BitmapDrawable) face_devious_bient).getBitmap();
            face_changeable_6_bient = ((BitmapDrawable) face_proud_bient).getBitmap();
            face_changeable_7_bient = ((BitmapDrawable) face_really_bient).getBitmap();
            face_changeable_8_bient = ((BitmapDrawable) face_terrifed_bient).getBitmap();
            face_changeable_9_bient = ((BitmapDrawable) face_uneasy_bient).getBitmap();

            setWatchFaceStyle(new WatchFaceStyle.Builder(BMO.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .setAcceptsTapEvents(true)
                    .build());


            mGoogleApiClient = new GoogleApiClient.Builder(BMO.this)
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();



            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(resources.getColor(R.color.background));

            mTextPaint = new Paint();
            mTextPaint = createTextPaint(resources.getColor(R.color.digital_text));

            mTextPaint_1 = new Paint();
            mTextPaint_1 = createTextPaint(resources.getColor(R.color.digital_text));

            mCalendar = Calendar.getInstance();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        private Paint createTextPaint(int textColor) {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Adventure.ttf");
            Paint paint = new Paint();
            paint.setColor(textColor);
            paint.setTypeface(font);
            paint.setAntiAlias(true);
            return paint;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                registerReceiver();
                mGoogleApiClient.connect();

                mCalendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            } else {
                unregisterReceiver();
                releaseGoogleApiClient();
            }

            updateTimer();
        }

        private void registerReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
            mRegisteredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            BMO.this.registerReceiver(mTimeZoneReceiver, filter);
            }

            if (!mBatrecive)
            {
                mBatrecive = true;
                IntentFilter filterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                BMO.this.registerReceiver(bat_recive, filterBattery);
            }
        }

        private void unregisterReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                mRegisteredTimeZoneReceiver = false;
                BMO.this.unregisterReceiver(mTimeZoneReceiver);
            }
            if (mBatrecive)
            {
                mBatrecive = false;
                BMO.this.unregisterReceiver(bat_recive);
            }
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            super.onApplyWindowInsets(insets);

            // Load resources that have alternate values for round watches.
            Resources resources = BMO.this.getResources();
            boolean isRound = insets.isRound();
            mXOffset = resources.getDimension(isRound
                    ? R.dimen.digital_x_offset_round : R.dimen.digital_x_offset);
            mYOffset_background = resources.getDimension(isRound
                    ? R.dimen.background_y_offset_round : R.dimen.background_y_offet);
            mYOffset = resources.getDimension(isRound
                    ? R.dimen.digital_y_offset_round : R.dimen.digital_y_offset);

            mXOffset_min = resources.getDimension(isRound
                    ? R.dimen.digital_x_date_round : R.dimen.digital_x_date);

            mYOffset_min = resources.getDimension(isRound
                    ? R.dimen.digital_y_date_round : R.dimen.digitial_y_date);

            mYOffset_battery = resources.getDimension(isRound
                    ? R.dimen.battery_y_round : R.dimen.battery_y);

            float textSize = resources.getDimension(isRound
                    ? R.dimen.digital_text_size_round : R.dimen.digital_text_size);
            float textSize_1 = resources.getDimension(isRound
                    ? R.dimen.digital_text_size_round_date : R.dimen.digital_text_size_date);

            mTextPaint.setTextSize(textSize);
            mTextPaint_1.setTextSize(textSize_1);
        }

        @Override
        public void onSurfaceChanged(
                SurfaceHolder holder, int format, int width, int height) {
            if (mBackgroundScaledBitmap == null
                    || mBackgroundScaledBitmap.getWidth() != width
                    || mBackgroundScaledBitmap.getHeight() != height) {
                mBackgroundScaledBitmap = Bitmap.createScaledBitmap(mBackgroundimage,
                        width, height, true /* filter */);
            }

            if (mBackgroundScaledBitmap_ambient == null
                    || mBackgroundScaledBitmap_ambient.getWidth() != width
                    || mBackgroundScaledBitmap_ambient.getHeight() != height) {
                mBackgroundScaledBitmap_ambient = Bitmap.createScaledBitmap(mambient_Backgroundimage,
                        width, height, true /* filter */);
            }

            if (face_scaledBitmap_1 == null
                    || face_scaledBitmap_1.getWidth() != width
                    || face_scaledBitmap_1.getHeight() != height) {
                face_scaledBitmap_1 = Bitmap.createScaledBitmap(face_changeable_1,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_2 == null
                    || face_scaledBitmap_2.getWidth() != width
                    || face_scaledBitmap_2.getHeight() != height) {
                face_scaledBitmap_2 = Bitmap.createScaledBitmap(face_changeable_2,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_3 == null
                    || face_scaledBitmap_3.getWidth() != width
                    || face_scaledBitmap_3.getHeight() != height) {
                face_scaledBitmap_3 = Bitmap.createScaledBitmap(face_changeable_3,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_4 == null
                    || face_scaledBitmap_4.getWidth() != width
                    || face_scaledBitmap_4.getHeight() != height) {
                face_scaledBitmap_4 = Bitmap.createScaledBitmap(face_changeable_4,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_5 == null
                    || face_scaledBitmap_5.getWidth() != width
                    || face_scaledBitmap_5.getHeight() != height) {
                face_scaledBitmap_5 = Bitmap.createScaledBitmap(face_changeable_5,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_6 == null
                    || face_scaledBitmap_6.getWidth() != width
                    || face_scaledBitmap_6.getHeight() != height) {
                face_scaledBitmap_6 = Bitmap.createScaledBitmap(face_changeable_6,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_7 == null
                    || face_scaledBitmap_7.getWidth() != width
                    || face_scaledBitmap_7.getHeight() != height) {
                face_scaledBitmap_7 = Bitmap.createScaledBitmap(face_changeable_7,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_8 == null
                    || face_scaledBitmap_8.getWidth() != width
                    || face_scaledBitmap_8.getHeight() != height) {
                face_scaledBitmap_8 = Bitmap.createScaledBitmap(face_changeable_8,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_9 == null
                    || face_scaledBitmap_9.getWidth() != width
                    || face_scaledBitmap_9.getHeight() != height) {
                face_scaledBitmap_9 = Bitmap.createScaledBitmap(face_changeable_9,
                        width, height, true /* filter */);
            }

            //ambient

            if (face_scaledBitmap_1_bient == null
                    || face_scaledBitmap_1_bient.getWidth() != width
                    || face_scaledBitmap_1_bient.getHeight() != height) {
                face_scaledBitmap_1_bient = Bitmap.createScaledBitmap(face_changeable_1_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_2_bient == null
                    || face_scaledBitmap_2_bient.getWidth() != width
                    || face_scaledBitmap_2_bient.getHeight() != height) {
                face_scaledBitmap_2_bient = Bitmap.createScaledBitmap(face_changeable_2_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_3_bient == null
                    || face_scaledBitmap_3_bient.getWidth() != width
                    || face_scaledBitmap_3_bient.getHeight() != height) {
                face_scaledBitmap_3_bient = Bitmap.createScaledBitmap(face_changeable_3_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_4_bient == null
                    || face_scaledBitmap_4_bient.getWidth() != width
                    || face_scaledBitmap_4_bient.getHeight() != height) {
                face_scaledBitmap_4_bient = Bitmap.createScaledBitmap(face_changeable_4_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_5_bient == null
                    || face_scaledBitmap_5_bient.getWidth() != width
                    || face_scaledBitmap_5_bient.getHeight() != height) {
                face_scaledBitmap_5_bient = Bitmap.createScaledBitmap(face_changeable_5_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_6_bient == null
                    || face_scaledBitmap_6_bient.getWidth() != width
                    || face_scaledBitmap_6_bient.getHeight() != height) {
                face_scaledBitmap_6_bient = Bitmap.createScaledBitmap(face_changeable_6_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_7_bient == null
                    || face_scaledBitmap_7_bient.getWidth() != width
                    || face_scaledBitmap_7_bient.getHeight() != height) {
                face_scaledBitmap_7_bient = Bitmap.createScaledBitmap(face_changeable_7_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_8_bient == null
                    || face_scaledBitmap_8_bient.getWidth() != width
                    || face_scaledBitmap_8_bient.getHeight() != height) {
                face_scaledBitmap_8_bient = Bitmap.createScaledBitmap(face_changeable_8_bient,
                        width, height, true /* filter */);
            }


            if (face_scaledBitmap_9_bient == null
                    || face_scaledBitmap_9_bient.getWidth() != width
                    || face_scaledBitmap_9_bient.getHeight() != height) {
                face_scaledBitmap_9_bient = Bitmap.createScaledBitmap(face_changeable_9_bient,
                        width, height, true /* filter */);
            }
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);
            mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;


                if (mLowBitAmbient) {
                    mTextPaint.setAntiAlias(!inAmbientMode);
                    mTextPaint_1.setAntiAlias(!inAmbientMode);
                }
                invalidate();
            }

            updateTimer();
        }

        int screen = 1;
        int clock_on = 2;
        String bat = "";

        boolean mBatrecive = false;

        private BroadcastReceiver bat_recive = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context arg0, Intent intent)
            {
                bat = String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0));
            }
        };

        @Override
        public void onTapCommand(int tapType, int x, int y, long eventTime) {
            switch (tapType) {
                case TAP_TYPE_TOUCH:
                    // The user has started touching the screen.
                    break;
                case TAP_TYPE_TOUCH_CANCEL:
                    // The user has started a different gesture or otherwise cancelled the tap.
                    break;
                case TAP_TYPE_TAP:
                        if (screen == 1) {
                            screen = 2;
                        } else if (screen == 2) {
                            screen = 1;
                        }
                    break;
            }
            invalidate();
        }
        int ambient_screen = 1;
        int face_image = 1;
        int time_format = 1;
        String time = "hh";
        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            // Draw the background.
            if (isInAmbientMode()) {
                ambient_screen = 1;
                clock_on = 2;
                canvas.drawColor(Color.BLACK);
                mTextPaint.setColor(Color.WHITE);
                mTextPaint_1.setColor(Color.WHITE);
                canvas.drawBitmap(mBackgroundScaledBitmap_ambient, mYOffset_background, 0, null);
            } else {
                ambient_screen = 0;
                mTextPaint.setColor(Color.BLACK);
                mTextPaint_1.setColor(Color.BLACK);
                canvas.drawRect(0, 0, bounds.width(), bounds.height(), mBackgroundPaint);
                canvas.drawBitmap(mBackgroundScaledBitmap, mYOffset_background, 0, null);
            }

            // Draw H:MM in ambient mode or H:MM:SS in interactive mode.
            long now = System.currentTimeMillis();
            mCalendar.setTimeInMillis(now);

            if (time_format == 1) {
                time = "hh";
            } else if (time_format == 2) {
                time = "HH";
            }

            String Hour = mAmbient
                    ? new SimpleDateFormat(time, Locale.getDefault()).format(mCalendar.getTime())
                    : new SimpleDateFormat(time, Locale.getDefault()).format(mCalendar.getTime());

            String Min = mAmbient
                    ? new SimpleDateFormat("mm", Locale.getDefault()).format(mCalendar.getTime())
                    : new SimpleDateFormat("mm", Locale.getDefault()).format(mCalendar.getTime());
            if (screen == 1) {
                canvas.drawText(Hour, mXOffset, mYOffset, mTextPaint);
                canvas.drawText(Min, mXOffset_min, mYOffset_min, mTextPaint);
            } else if (screen == 2) {
                canvas.drawText(bat, mXOffset, mYOffset_battery, mTextPaint);
            }

            if (ambient_screen == 0) { //ambient screen = flase

                if (face_image == 1) {
                    canvas.drawBitmap(face_scaledBitmap_1, mYOffset_background, 0, null);
                } else if (face_image == 2) {
                    canvas.drawBitmap(face_scaledBitmap_2, mYOffset_background, 0, null);
                } else if (face_image == 3) {
                    canvas.drawBitmap(face_scaledBitmap_3, mYOffset_background, 0, null);
                } else if (face_image == 4) {
                    canvas.drawBitmap(face_scaledBitmap_4, mYOffset_background, 0, null);
                } else if (face_image == 5) {
                    canvas.drawBitmap(face_scaledBitmap_5, mYOffset_background, 0, null);
                } else if (face_image == 6) {
                    canvas.drawBitmap(face_scaledBitmap_6, mYOffset_background, 0, null);
                } else if (face_image == 7) {
                    canvas.drawBitmap(face_scaledBitmap_7, mYOffset_background, 0, null);
                } else if (face_image == 8) {
                    canvas.drawBitmap(face_scaledBitmap_8, mYOffset_background, 0, null);
                } else if (face_image == 9) {
                    canvas.drawBitmap(face_scaledBitmap_9, mYOffset_background, 0, null);
                }
            } else if (ambient_screen == 1) { //ambient screen = true

                if (face_image == 1) {
                    canvas.drawBitmap(face_scaledBitmap_1_bient, mYOffset_background, 0, null);
                } else if (face_image == 2) {
                    canvas.drawBitmap(face_scaledBitmap_2_bient, mYOffset_background, 0, null);
                } else if (face_image == 3) {
                    canvas.drawBitmap(face_scaledBitmap_3_bient, mYOffset_background, 0, null);
                } else if (face_image == 4) {
                    canvas.drawBitmap(face_scaledBitmap_4_bient, mYOffset_background, 0, null);
                } else if (face_image == 5) {
                    canvas.drawBitmap(face_scaledBitmap_5_bient, mYOffset_background, 0, null);
                } else if (face_image == 6) {
                    canvas.drawBitmap(face_scaledBitmap_6_bient, mYOffset_background, 0, null);
                } else if (face_image == 7) {
                    canvas.drawBitmap(face_scaledBitmap_7_bient, mYOffset_background, 0, null);
                } else if (face_image == 8) {
                    canvas.drawBitmap(face_scaledBitmap_8_bient, mYOffset_background, 0, null);
                } else if (face_image == 9) {
                    canvas.drawBitmap(face_scaledBitmap_9_bient, mYOffset_background, 0, null);
                }
            }
        }


        private void updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }
        private void handleUpdateTimeMessage() {
            invalidate();
            if (shouldTimerBeRunning()) {
                long timeMs = System.currentTimeMillis();
                long delayMs = INTERACTIVE_UPDATE_RATE_MS
                        - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
                mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
            }
        }

        private void releaseGoogleApiClient() {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Wearable.DataApi.removeListener(mGoogleApiClient, onDataChangedListener);
                mGoogleApiClient.disconnect();
            }
        }
        private final DataApi.DataListener onDataChangedListener = new DataApi.DataListener() {
            @Override
            public void onDataChanged(DataEventBuffer dataEvents) {
                for (DataEvent event : dataEvents) {
                    if (event.getType() == DataEvent.TYPE_CHANGED) {
                        DataItem item = event.getDataItem();
                        if ((item.getUri().getPath()).equals("/watch_face_config_cliu")) {
                            updateParamsForDataItem_time(item);
                        }
                        if ((item.getUri().getPath()).equals("/watch_face_config_cliu_1")) {
                            updateParamsForDataItem_face(item);
                        }

                    }
                }

                dataEvents.release();
                if (isVisible() && !isInAmbientMode()) {
                    invalidate();
                }
            }
        };

        @Override
        public void onConnected(Bundle bundle) {
            Wearable.DataApi.addListener(mGoogleApiClient, onDataChangedListener);
            Wearable.DataApi.getDataItems(mGoogleApiClient).setResultCallback(onConnectedResultCallback);
        }

        private final ResultCallback<DataItemBuffer> onConnectedResultCallback = new ResultCallback<DataItemBuffer>() {
            @Override
            public void onResult(DataItemBuffer dataItems) {
                for (DataItem item : dataItems) {
                    updateParamsForDataItem_time(item);
                    updateParamsForDataItem_face(item);
                }

                dataItems.release();
                if (isVisible() && !isInAmbientMode()) {
                    invalidate();
                }
            }
        };


        private void updateParamsForDataItem_time(DataItem item) {
            DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
            if (dataMap.containsKey("time_format")) {
                int td = dataMap.getInt("time_format");
                time_set_format = td;

                if (time_set_format == 0x1) {
                    time_format = 1;
                } else if (time_set_format == 0x2) {
                    time_format = 2;
                }

            }

            invalidate();
        }

        private void updateParamsForDataItem_face(DataItem item) {
            DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
            if (dataMap.containsKey("display_face")) {
                int td = dataMap.getInt("display_face");
                display_face_int = td;

                    if (display_face_int == 0x1) {
                        face_image = 1;
                    } else if (display_face_int == 0x2) {
                        face_image = 2;
                    } else if (display_face_int == 0x3) {
                        face_image = 3;
                    } else if (display_face_int == 0x4) {
                        face_image = 4;
                    } else if (display_face_int == 0x5) {
                        face_image = 5;
                    } else if (display_face_int == 0x6) {
                        face_image = 6;
                    } else if (display_face_int == 0x7) {
                        face_image = 7;
                    } else if (display_face_int == 0x8) {
                        face_image = 8;
                    } else if (display_face_int == 0x9) {
                        face_image = 9;
                    }


            }

            invalidate();
        }



        @Override
        public void onConnectionSuspended(int i) {
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
        }

    }
}
