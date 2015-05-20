# Voorbeeld van een custom ArrayAdapter

```
class HoroscoopAdapter extends ArrayAdapter<Data.Horoscoop>
{
    private Data.Horoscoop[] horoscopen;

    public HoroscoopAdapter(Data.Horoscoop[] horoscopen)
    {
        super(getActivity(), R.layout.row_horoscoop, R.id.textViewHoroscoop, horoscopen);
        this.horoscopen = horoscopen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if(viewHolder == null)
        {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Data.Horoscoop horoscoop = horoscopen[position];
        viewHolder.textViewHoroscoop.setText(horoscoop.getNaamHoroscoop());

        return view;
    }
}
```