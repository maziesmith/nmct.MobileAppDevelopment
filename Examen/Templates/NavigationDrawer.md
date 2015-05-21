# NavigationDrawer

## Stap 1: Fragment aanmaken

Maak een Fragment aan voor de NavigationDrawer.

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="left|start"
    android:background="#FFFFFF">


    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Kies een diplomagraad:"
        android:id="@+id/textViewDiplomagraad" />

    <ListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@android:id/list"/>
</LinearLayout>
```
## Stap 2: Pas de MainActivity-layout aan

Doe dat als volgt:

```
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin" tools:context=".MainActivity"
    android:id="@+id/drawer_layout">

    <FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/container"
        tools:context=".MainActivity"
        tools:ignor="MergeRootFrame"/>

    <fragment
        android:name="be.howest.nmct.scoresstudenten.DiplomagraadFragment"
        android:layout_width="240dp"
        android:layout_height="match_parent"
        android:id="@+id/diplomagraad_fragment"
        android:layout_gravity="start"
        android:dividerHeight="0dp"/>

</android.support.v4.widget.DrawerLayout>
```

## Stap 3: Maak een ListFragmentklasse aan die de NavigationDrawer opvult

* DrawerLayout opvragen. 
* Adapter aanmaken.
* Adapter opvullen met juiste data. 
* Adapter instellen op ListFragment.
* Clicks opvangen.

```
public class DiplomagraadFragment extends ListFragment
{
    /*
    ** Fields
     */

    private ListAdapter adapter;

    /*
    ** Events
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_diplomagraad, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Student.DIPLOMAGRAAD[] graden = Student.DIPLOMAGRAAD.values();
        this.adapter = new DiplomagraadAdapter(graden);
        setListAdapter(this.adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Student.DIPLOMAGRAAD[] graden = Student.DIPLOMAGRAAD.values();

        //Clicks doorzenden
        StudentenFragment studentenFragment = (StudentenFragment) getFragmentManager().findFragmentByTag("studentenFragment");
        studentenFragment.setDiplomagraad(graden[position]);

        //Sluit drawer
        MainActivity.drawerLayout.closeDrawer(Gravity.LEFT);
    }

    /*
    ** Adapter
     */

    class DiplomagraadAdapter extends ArrayAdapter<Student.DIPLOMAGRAAD>
    {
        private Student.DIPLOMAGRAAD[] graden;

        public DiplomagraadAdapter(Student.DIPLOMAGRAAD[] objects)
        {
            super(getActivity(), R.layout.row_diplomagraad, R.id.textViewDiplomagraadItem, objects);
            this.graden = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);

            TextView textViewDiplomagraadItem = (TextView) view.findViewById(R.id.textViewDiplomagraadItem);
            textViewDiplomagraadItem.setText(graden[position].getNaam());

            return view;
        }
    }
}
```
## Stap 4: Pas het ontvangende fragment aan

* Voeg een setter toe voor de property die zojuist werd ingesteld.
* Herstart het laden met de Bundle met de property.

```
    public void setDiplomagraad(Student.DIPLOMAGRAAD diplomagraad)
    {
        this.diplomagraad = diplomagraad;

        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.EXTRA_DIPLOMAGRAAD, diplomagraad.ordinal());
        getLoaderManager().restartLoader(1, bundle, this);
    }
```

Zorg er nu voor dat onCreateLoader ook aangepast wordt naar een versie met Bundle.

```
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        if(args == null)
            return new StudentLoader(getActivity());

        else
        {
            Student.DIPLOMAGRAAD graad = Student.DIPLOMAGRAAD.values()[args.getInt(DIPLOMAGRAAD)];
            return new StudentLoader(getActivity(), graad);
        }
    }
```

# Stap 5: Pas de Loader aan zodat alles werkt

```
	public StudentLoader(Context context)
    {
        super(context);

        columns = new String[]
        {
            BaseColumns._ID,
            Contract.StudentContract.COLUMN_NAAM,
            Contract.StudentContract.COLUMN_VOORNAAM,
            Contract.StudentContract.COLUMN_EMAIL,
            Contract.StudentContract.COLUMN_SCORE_TOTAAL
        };
    }

    public StudentLoader(Context context, Student.DIPLOMAGRAAD diplomagraad) {
        super(context);
        this.diplomagraad = diplomagraad;

        columns = new String[]
        {
            BaseColumns._ID,
            Contract.StudentContract.COLUMN_NAAM,
            Contract.StudentContract.COLUMN_VOORNAAM,
            Contract.StudentContract.COLUMN_EMAIL,
            Contract.StudentContract.COLUMN_SCORE_TOTAAL
        };
    }

    @Override
    protected void onStartLoading()
    {
        if(studentCursor == null)
            forceLoad();
    }

    @Override
    public Cursor loadInBackground()
    {
        if(diplomagraad == null)
            return getStudenten();

        else
            return getStudenten(this.diplomagraad);
    }
``


