package nz.org.winters.android.custompreference;
/*
 * Copyright 2013 Mathew Winters

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 vYou may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class IntervalPickerPreference extends DialogPreference implements TimePicker.OnTimeChangedListener
{

  /**
   * The validation expression for this preference
   */
//  private static final String VALIDATION_EXPRESSION = "[0-2]*[0-9]:[0-5]*[0-9]";

  /**
   * The default value for this preference
   */
  private int                 mDefaultValue;
  private int                 mValue = 0;
  /**
   * Store the original value, in case the user chooses to abort the
   * {@link DialogPreference} after making a change.
   */
  private int                 mOriginalMinutes = 0;

  /**
   * @param context
   * @param attrs
   */
  public IntervalPickerPreference(final Context context, final AttributeSet attrs)
  {
    super(context, attrs);
    initialize();
  }

  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public IntervalPickerPreference(final Context context, final AttributeSet attrs, final int defStyle)
  {
    super(context, attrs, defStyle);
    initialize();
  }

  /**
   * Initialize this preference
   */
  private void initialize()
  {
    setPersistent(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see android.preference.DialogPreference#onCreateDialogView()
   */
  @Override
  protected View onCreateDialogView()
  {

    TimePicker tp = new TimePicker(getContext());
    tp.setIs24HourView(true);
    
    mOriginalMinutes = getMinutes();
    if (mOriginalMinutes >= 0)
    {
      tp.setCurrentHour(getHour());
      tp.setCurrentMinute(getMinute());
    }
    tp.setOnTimeChangedListener(this);

    return tp;
  }

  /**
   * @see android.widget.TimePicker.OnTimeChangedListener#onTimeChanged(android
   *      .widget.TimePicker, int, int)
   */
  @Override
  public void onTimeChanged(final TimePicker view, final int hour, final int minute)
  {
    mValue = (hour * 60) + minute;
    persistInt(mValue);
    callChangeListener(mValue);
  }

  /**
   * If not a positive result, restore the original value before going to
   * super.onDialogClosed(positiveResult).
   */
  @Override
  protected void onDialogClosed(boolean positiveResult)
  {
    if(positiveResult && getMinutes() < 10)
    {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
      builder.setTitle(getTitle());
      builder.setMessage("Please select 10 minutes or more!");
      builder.setCancelable(false);
      //builder.setIcon(R.drawable.ic_launcher);
      builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
      {

        public void onClick(DialogInterface dialog, int which)
        {
          dialog.dismiss();
        }
      });
      AlertDialog alert = builder.create();
      alert.show();
      
      positiveResult = false;
    }
    
    if (!positiveResult)
    {
      mValue = mOriginalMinutes;
      persistInt(mValue);
      callChangeListener(mValue);
    }
    super.onDialogClosed(positiveResult);
  }

  /**
   * @see android.preference.Preference#setDefaultValue(java.lang.Object)
   */
  @Override
  public void setDefaultValue(final Object defaultValue)
  {
    // BUG this method is never called if you use the 'android:defaultValue'
    // attribute in your XML preference file, not sure why it isn't

    super.setDefaultValue(defaultValue);

    if (!(defaultValue instanceof Integer))
    {
      return;
    }

    this.mDefaultValue = (Integer) defaultValue;
  }

  /**
   * Get the hour value (in 24 hour time)
   * 
   * @return The hour value, will be 0 to 23 (inclusive) or -1 if illegal
   */
  public int getHour()
  {
    int minutes = getMinutes();
    return minutes / 60;
  }

  /**
   * Get the minute value
   * 
   * @return the minute value, will be 0 to 59 (inclusive) or -1 if illegal
   */
  public int getMinute()
  {
    int minutes = getMinutes();
    return minutes % 60;    
  }

  /**
   * Get the time. It is only legal, if it matches
   * {@link #VALIDATION_EXPRESSION}.
   * 
   * @return the time as hh:mm
   */
  public int getMinutes()
  {
    return getPersistedInt(this.mDefaultValue);
  }

  public String getIntervalText()
  {
    int hour = getHour();
    int min =  getMinute();
    
    String minStr = "";
    if(min > 0)
    {
      minStr = min + " minute";
      if(min > 1)
        minStr = minStr + "s";
    }
    
    String hourStr = "";
    if(hour > 0)
    {
      hourStr = hour + " hour";
      if(hour > 1)
        hourStr = hourStr + "s";
    }
    
    if(TextUtils.isEmpty(hourStr) && TextUtils.isEmpty(minStr))
    {
      return "Invalid interval";
    }else
    {
      if(TextUtils.isEmpty(hourStr))
      {
        return minStr;
      }else if(TextUtils.isEmpty(minStr))
      {
        return hourStr;
      }else
      {
        return hourStr + " and " + minStr;
      }
    }
    
  }
}
