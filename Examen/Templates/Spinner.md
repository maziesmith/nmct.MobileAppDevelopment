# Spinner aanmaken

## Stap 1: Spinner tonen

```
public void onActivityCreated()
{
	ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    actionBar.setDisplayShowTitleEnabled(true);

    this.dropdownList = new ArrayList<String>();
    this.dropdownList.add("Toon alle");
    this.dropdownList.addAll(Months.getListOfMonthNames());

    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spinner, dropdownList);
}
```

## Stap 2: Spinner callbacks aanmaken

```
public void onActivityCreated()
{
	ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    actionBar.setDisplayShowTitleEnabled(true);

    this.dropdownList = new ArrayList<String>();
    this.dropdownList.add("Toon alle");
    this.dropdownList.addAll(Months.getListOfMonthNames());

    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_spinner, R.id.textViewSpinnerItem, dropdownList);
	actionBar.setListNavigationCallbacks(spinnerAdapter, new ActionBar.OnNavigationListener()
    {
    	@Override
   		public boolean onNavigationItemSelected(int itemPosition, long itemId)
        {
        	return false;
        }
    });
}
```

## Stap 3: XML

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="5dp"
    android:background="#3498db">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="text"
        android:id="@+id/textViewSpinnerItem"
        android:textColor="#FFFFFF" />
</LinearLayout>
```
