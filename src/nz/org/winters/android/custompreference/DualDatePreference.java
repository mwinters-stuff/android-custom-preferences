package nz.org.winters.android.custompreference;

import java.util.Date;
import java.util.GregorianCalendar;

import nz.org.winters.android.custompreferences.R;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

public class DualDatePreference extends DialogPreference
{
  DatePicker mDatePickerStart;
  DatePicker mDatePickerEnd;
  
  public Date mDateStart;
  public Date mDateEnd;
  
  public OnClickListener mClickListener;
  
  public DualDatePreference(final Context context, final AttributeSet attrs)
  {
    super(context, attrs);
    initialize();
  }

  /**
   * @param context
   * @param attrs
   * @param defStyle
   */
  public DualDatePreference(final Context context, final AttributeSet attrs, final int defStyle)
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
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    View view = inflater.inflate(R.layout.dual_date, null);

    mDatePickerStart = (DatePicker)view.findViewById(R.id.datePickerStart);
    mDatePickerEnd = (DatePicker)view.findViewById(R.id.datePickerEnd);
    
    GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
    cal.add(GregorianCalendar.YEAR,-1);
    mDatePickerStart.updateDate(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH));

    
    cal.add(GregorianCalendar.YEAR,1);
    
    mDatePickerEnd.updateDate(cal.get(GregorianCalendar.YEAR),cal.get(GregorianCalendar.MONTH),cal.get(GregorianCalendar.DAY_OF_MONTH));
    
    return view;
  }

  @Override
  protected void onDialogClosed(boolean positiveResult)
  {
    if(positiveResult)
    {
      
      GregorianCalendar cal = new GregorianCalendar(mDatePickerStart.getYear(), mDatePickerStart.getMonth(), mDatePickerStart.getDayOfMonth());

      mDateStart = cal.getTime();
      cal = new GregorianCalendar(mDatePickerEnd.getYear(), mDatePickerEnd.getMonth(), mDatePickerEnd.getDayOfMonth());
      mDateEnd  = cal.getTime();
      
      if(mClickListener != null)
      { 
        mClickListener.onClick(getDialog(), android.R.id.button1);
      }
        
        
    }else{
      mDateStart = null;
      mDateEnd = null;
    }
    super.onDialogClosed(positiveResult);
  }

  @Override
  public void setDefaultValue(final Object defaultValue)
  {
    super.setDefaultValue(defaultValue);
  }

}
