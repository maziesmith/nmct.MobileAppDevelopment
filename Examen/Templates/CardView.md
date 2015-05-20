# CardView

## Stap 1: voeg de juiste dependencies toe 

Dit gebeurt in build.gradle (Module: App).

```
compile 'com.android.support:cardview-v7:21.0.+'
```

## Stap 2: Wijzig de layoutfile van een row

```
<android.support.v7.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    android:id="@+id/card_view"
    android:layout_width="fill_parent"
    android:layout_height="match_parent"
    card_view:cardCornerRadius="4dp"
    android:layout_margin="5dp">

    <RelativeLayout
        android:orientation="vertical" android:layout_width="fill_parent"
        android:layout_height="match_parent"
        android:layout_margin="16dp">
```