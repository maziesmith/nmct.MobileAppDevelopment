#Google Maps gebruiken

##Stap 1: Pas het manifest aan

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.kristofcolpaert.comicbookroutebrussels" >

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >

        <activity
            android:name=".HomeActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyBORbmxvnaaN9IAzn2-0tX4uwZquwcK8xI"/>

    </application>

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true"/>

</manifest>
```

## Stap 2: Pas build.gradle aan

Pas build.gradle (Module: app) aan en voeg bij dependencies: 

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:22.1.0'
    compile 'com.google.android.gms:play-services:7.3.0'
}
```

## Stap 3: Voorzie het passende Google Maps Fragment

Voorzie een fragment met het Google Maps fragment: 

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/map"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.google.android.gms.maps.MapFragment"/>

</LinearLayout>
```

## Stap 4: Voorzie een Google Maps API key

Ga naar terminal en vul het volgende commando in. Kopieer de SHA1-key:

```
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

Stel een debug-key op als volgt:

```
31:45:59:B6:8E:2E:24:B4:0C:98:7A:20:FD:01:A4:55:38:C6:84:E7;be.howest.nmct.snelheidscontroleskortrijk
```

* Ga naar console.developers.google.com
* Maak een nieuw project aan
* Kies links voor API's & Auth
* Kies voor de Google Maps Android API v2
* Selecteer Enable API
* Ga naar credentials
* Kies Create new Key
* Kies Android Key
* Voer de hierboven aangemaakte debug-key in.
* Je krijgt nu een API-key terug: vul die in in het manifest. 

## Stap 5: Spreek de Google Maps kaart aan

```
private GoogleMap googleMap;
private static View view;

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
{
    if(view != null)
    {
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null)
            parent.removeView(view);
    }

    try
    {
        view = inflater.inflate(R.layout.fragment_velocity_map, container, false);
    }

    catch(InflateException ex)
    {
        Log.d("An error occured: ", ex.getMessage());
    }

    this.googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();

    return view;
}
```

## Stap 6: Voeg markers toe aan de Google Maps kaart

```
private void addMarker(double x, double y, String straat, int aantalControles, int gepasseerdeVoertuigen, int vtgInOvertreding)
{
    MarkerOptions markerOptions = new MarkerOptions()
        .position(lambert72toWGS84(x, y))
        .title("Aantal controles in " + straat + ": " + aantalControles)
        .snippet("Voertuigen: " + gepasseerdeVoertuigen + ", Overtredingen: " + vtgInOvertreding)
        .flat(true)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

    googleMap.addMarker(markerOptions).showInfoWindow();

    CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(lambert72toWGS84(x, y))
        .zoom(14)
        .build();

    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
}
```
