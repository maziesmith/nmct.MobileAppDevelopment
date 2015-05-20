# Ervoor zorgen dat gebruiker nooit een lege Activity te zien krijgt bij back

```
@Override
public void onBackPressed()
{
	if(getSupportFragmentManager().getBackStackEntryCount() == 1)
    {
    	finish();
    }

    else
    {
    	super.onBackPressed();
    }
}
```