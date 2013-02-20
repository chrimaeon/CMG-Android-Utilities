package com.cmgapps.android.util;

import android.app.Activity;
import android.view.View;

public class UiUtilities
{
  private UiUtilities()
  {
  }

  @SuppressWarnings("unchecked")
  public static <T extends View> T getView(View parent, int resource_id)
  {
    return (T) checkView(parent.findViewById(resource_id));
  }

  @SuppressWarnings("unchecked")
  public static <T extends View> T getView(Activity parent, int resource_id)
  {
    return (T) checkView(parent.findViewById(resource_id));
  }

  @SuppressWarnings("unchecked")
  public static <T extends View> T getViewOrNull(View parent, int resource_id)
  {
    return (T) parent.findViewById(resource_id);
  }

  @SuppressWarnings("unchecked")
  public static <T extends View> T getViewOrNull(Activity parent, int resource_id)
  {
    return (T) parent.findViewById(resource_id);
  }

  private static View checkView(View v)
  {
    if (v == null)
      throw new IllegalArgumentException("View does not exist");

    return v;
  }

}
