package nz.org.winters.android.custompreference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.EditTextPreference;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class FloatPreference extends EditTextPreference 
{



  public FloatPreference(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    initialize();
  }

  public FloatPreference(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
    initialize();
  }

  private void initialize()
  {
    setPersistent(true);
//    setPositiveButtonText(R.string.color_new_color);
//    setNegativeButtonText(R.string.color_old_color);
  }
  
  @Override
  protected Object onGetDefaultValue(TypedArray a, int index) {
      return a.getFloat(index,(float)0.0);
  }

  @Override
  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
      setText(restoreValue ? Float.toString(getPersistedFloat((float)0.0)) : Float.toString((Float)defaultValue));
  }

  
  @Override 
  public String getText()
  {
    return Float.toString(getPersistedFloat(0));
  }

  @Override 
  public void setText(String text)
  {
    persistFloat(Float.parseFloat(text));
  }
  
  @Override
  protected void onBindView(View view)
  {
   // TextView mSummaryView = (TextView) view.findViewById(android.R.id.summary);
    setSummary(getText());

    
    super.onBindView(view);

  }

  @Override
  protected void onBindDialogView(View view)
  {
    super.onBindDialogView(view);
    
    EditText edit = getEditText();
    
    edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

  }

//  @Override
//  protected void onDialogClosed(boolean positiveResult)
//  {
//    if (positiveResult)
//    {
//      int color = mSelectorView.getColor();
//      persistInt(color);
//      setSummary(Integer.toString(getValue()));
//    }
//    super.onDialogClosed(positiveResult);
//  }
//
//  public int getValue()
//  {
//    return getPersistedInt(0);
//  }

//  @Override
//  protected View onCreateDialogView()
//  {
////    mSelectorView = new ColorSelectorView(getContext());
////    mSelectorView.setColor(getColour());
////    mSelectorView.setOnColorChangedListener(this);
////    return mSelectorView;
//  }


  

}
