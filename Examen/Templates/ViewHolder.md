# ViewHolder

```
class ViewHolder
{
	private TextView textViewStreet;
    private TextView textViewCity;
    private TextView textViewMonth;
    private TextView textViewNumberOfTrackings;

    public ViewHolder(View view)
    {
    	this.textViewStreet = (TextView) view.findViewById(R.id.textViewStreet);
        this.textViewCity = (TextView) view.findViewById(R.id.textViewCity);
        this.textViewMonth = (TextView) view.findViewById(R.id.textViewMonth);
        this.textViewNumberOfTrackings = (TextView) view.findViewById(R.id.textViewNumberOfTrackings);
    }
}