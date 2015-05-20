# RecyclerView

## Stap 1: voeg de juiste dependencies toe 

Dit gebeurt in build.gradle (Module: App).

```
compile 'com.android.support:recyclerview-v7:21.0.+'
```

## Stap 2: maak een Layout-file aan met RecyclerView

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/container">

    <android.support.v7.widget.RecyclerView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"/>

</LinearLayout>
```

## Stap 3: initialiseer de RecyclerView in code

* Laat het Fragment overerven van de klasse Fragment.
* Haal de RecyclerView op in de onCreateView.
* Stel de LayoutManager in.

```
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
{
    View view = inflater.inflate(R.layout.fragment_student_huizen, container, false);

    this.recyclerViewList = (RecyclerView) view.findViewById(R.id.recyclerViewList);
    recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity());

	return view;
}
```

## Stap 4: maak een passende Adapter aan

```
/*
    ** Adapter
     */

    class StudentHuizenAdapter extends RecyclerView.Adapter<StudentHuizenViewHolder>
    {
        private final Cursor studentHuizenCursor;

        public StudentHuizenAdapter(Cursor studentHuizenCursor)
        {
            this.studentHuizenCursor = studentHuizenCursor;
        }

        @Override
        public StudentHuizenViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_huis, parent, false);
            return new StudentHuizenViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StudentHuizenViewHolder holder, int position)
        {
            this.studentHuizenCursor.moveToPosition(position);

            int col1 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_ADRES);
            int col2 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_HUISNR);
            int col3 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_GEMEENTE);
            int col4 = studentHuizenCursor.getColumnIndex(Contract.StudentHuizenContract.COLUMN_AANTAL_KAMERS);

            holder.textViewStraat.setText(studentHuizenCursor.getString(col1) + " " + studentHuizenCursor.getString(col2));
            holder.textViewGemeente.setText(studentHuizenCursor.getString(col3));
            holder.textViewAantalKamers.setText("Aantal kamers: " + studentHuizenCursor.getString(col4));
        }

        @Override
        public int getItemCount()
        {
            return studentHuizenCursor.getCount();
        }
    }

    class StudentHuizenViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewStraat;
        public TextView textViewGemeente;
        public TextView textViewAantalKamers;


        public StudentHuizenViewHolder(View itemView)
        {
            super(itemView);

            this.textViewStraat = (TextView) itemView.findViewById(R.id.textViewStraat);
            this.textViewGemeente = (TextView) itemView.findViewById(R.id.textViewGemeente);
            this.textViewAantalKamers = (TextView) itemView.findViewById(R.id.textViewAantalKamers);
        }
    }
```

## Stap 5: Zorg ervoor dat de adapter gelinkt wordt aan de data

Dit doen we op het moment dat we de cursor met data terugkrijgen.

```
public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
{
    this.adapter = new StudentHuizenAdapter(cursor);
	recyclerViewList.setAdapter(this.adapter);
}
```

## Stap 6: Opvangen van clicks

Het opvangen van clicks kan in de ViewHolder:

```
	class StudentHuizenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView textViewStraat;
        public TextView textViewGemeente;
        public TextView textViewAantalKamers;


        public StudentHuizenViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            this.textViewStraat = (TextView) itemView.findViewById(R.id.textViewStraat);
            this.textViewGemeente = (TextView) itemView.findViewById(R.id.textViewGemeente);
            this.textViewAantalKamers = (TextView) itemView.findViewById(R.id.textViewAantalKamers);
        }

        @Override
        public void onClick(View v)
        {
            TextView tempTextView = (TextView) v.findViewById(R.id.textViewStraat);
            Toast.makeText(getActivity(), tempTextView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
```