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
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.EditTextPreference;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class IntegerPreference extends EditTextPreference 
{



  public IntegerPreference(Context context, AttributeSet attrs)
  {
    super(context, attrs);
    initialize();
  }

  public IntegerPreference(Context context, AttributeSet attrs, int defStyle)
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
      return a.getInteger(index,0);
  }

  @Override
  protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
      setText(restoreValue ? Integer.toString(getPersistedInt(0)) : Integer.toString((Integer)defaultValue));
  }

  
  @Override 
  public String getText()
  {
    return Integer.toString(getPersistedInt(0));
  }

  @Override 
  public void setText(String text)
  {
    persistInt(Integer.parseInt(text));
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
    
    edit.setInputType(InputType.TYPE_CLASS_NUMBER);

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
