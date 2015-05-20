# Search Widget

## Stap 1: XML

Voeg het volgende menu-item toe aan de XML.

```
<item android:id="@+id/my_search"
        android:title="Zoeken"
        android:icon="@drawable/abc_ic_search_api_mtrl_alpha"
        app:showAsAction="collapseActionView|ifRoom"
        app:actionViewClass="android.widget.SearchView" />
```

## Stap 2: Zet het optionsMenu aan voor het fragment

```
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }
```

## Stap 3: Initialiseer het menu en filter de cursor

```
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.my_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        final int resource_edit_text = searchView.getResources().getIdentifier("android:id/search_src_text", null, null);
        ((EditText) searchView.findViewById(resource_edit_text)).addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String currentString = s.toString();
                if(currentString.isEmpty())
                {
                    adapter.swapCursor(baseCursor);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String currentString = s.toString();
                if(currentString.isEmpty())
                {
                    adapter.swapCursor(baseCursor);
                }
            }
        });
    }

    private void setupSearchView(MenuItem searchItem)
    {
        if(isAlwaysExpanded())
        {
            searchView.setIconifiedByDefault(false);
        }

        else
        {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCursor(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCursor(newText);
                return false;
            }
        });
    }

    private boolean isAlwaysExpanded()
    {
        return true;
    }

    private void filterCursor(String query)
    {
        String[] tempColumnNames = new String[]
                {
                        BaseColumns._ID,
                        Contract.StudentHuizenContract.COLUMN_ADRES,
                        Contract.StudentHuizenContract.COLUMN_HUISNR,
                        Contract.StudentHuizenContract.COLUMN_GEMEENTE,
                        Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS
                };

        MatrixCursor matrixCursor = new MatrixCursor(tempColumnNames);
        int col1 = baseCursor.getColumnIndex(BaseColumns._ID);
        int col2 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_ADRES);
        int col3 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_HUISNR);
        int col4 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_GEMEENTE);
        int col5 = baseCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS);

        baseCursor.moveToFirst();
        do
        {
            if(baseCursor.getString(col2).toLowerCase().contains(query.toLowerCase().trim()))
            {
                matrixCursor.newRow()
                        .add(baseCursor.getInt(col1))
                        .add(baseCursor.getString(col2))
                        .add(baseCursor.getString(col3))
                        .add(baseCursor.getString(col4))
                        .add(baseCursor.getString(col5));
            }
        } while (baseCursor.moveToNext());

        this.adapter.swapCursor(matrixCursor);
    }
```