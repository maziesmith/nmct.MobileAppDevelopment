# Omzetten naar tablet

## Stap 1: Maak een tweede MainActivity aan

Plaats hierin twee FrameLayouts.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:layout_width="350dp"
        android:layout_height="fill_parent"
        android:id="@+id/left"/>

    <FrameLayout
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:id="@+id/right"/>

</LinearLayout>
```

## Stap 2: Voeg een isTablet-methode toe aan MainActivity

```
    public static boolean isTablet(Context context)
    {
        return(context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
```

## Stap 3: Implementeer nu zodat alles correct wordt weergegeven