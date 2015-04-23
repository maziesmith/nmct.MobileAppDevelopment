package com.kristofcolpaert.horoscoop.Data;
import android.graphics.drawable.Drawable;

import com.kristofcolpaert.horoscoop.R;

public class Data {

	public enum Horoscoop {
		WATERMAN("Waterman", "21 januari", "20 februari", R.drawable.waterman),
        VISSEN("Vissen", "21 februari", "20 maart", R.drawable.vissen),
        RAM("Ram", "21 maart", "20 april", R.drawable.ram),
        STIER("Stier", "21 april", "20 mei", R.drawable.stier),
        TWEELING("Tweeling", "21 mei", "20 juni", R.drawable.tweeling),
        KREEFT("Kreeft", "21 juni", "22 juli", R.drawable.kreeft),
        LEEUW("Leeuw", "23 juli", "22 augustus", R.drawable.leeuw),
        MAAGD("Maagd", "23 augustus", "22 september", R.drawable.maagd),
        WEEGSCHAAL("Weegschaal", "23 september", "22 oktober", R.drawable.weegschaal),
        SCHORPIOEN("Schorpioen", "23 oktober", "22 november", R.drawable.schorpioen),
        BOOGSCHUTTER("Boogschutter", "23 november", "21 december", R.drawable.boogschutter),
        STEENBOK("Steenbok", "22 december", "20 januari", R.drawable.steenbok);

		private String naamHoroscoop;
		private String beginDatum;
		private String eindDatum;
        private int drawable;

		Horoscoop(String naamHoroscoop, String begindatum, String einddatum, int drawable) {
			this.naamHoroscoop = naamHoroscoop;
			this.beginDatum = begindatum;
			this.eindDatum = einddatum;
            this.drawable = drawable;
		}

		public String getNaamHoroscoop() {
			return naamHoroscoop;
		}

		public String getBeginDatum() {
			return beginDatum;
		}

		public String getEindDatum() {
			return eindDatum;
		}

        public int getDrawable() { return drawable; }

        public static Horoscoop getHoroscopeByDrawable(int drawable)
        {
            for(Horoscoop horoscope : Horoscoop.values())
            {
                if(horoscope.drawable == drawable)
                    return horoscope;
            }
            return null;
        }
    }
}
