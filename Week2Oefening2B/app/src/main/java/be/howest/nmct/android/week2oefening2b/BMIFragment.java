package be.howest.nmct.android.week2oefening2b;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kristofcolpaert on 19/02/15.
 */
public class BMIFragment extends Fragment
{
    private EditText editTextHeight;
    private EditText editTextMass;
    private Button buttonUpdate;
    private TextView textViewIndex;
    private TextView textViewCategory;
    private ImageView imageViewBMI;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);

        editTextHeight = (EditText) v.findViewById(R.id.editTextHeight);
        editTextMass = (EditText) v.findViewById(R.id.editTextMass);
        buttonUpdate = (Button) v.findViewById(R.id.buttonUpdate);
        textViewIndex = (TextView) v.findViewById(R.id.textViewIndex1);
        textViewCategory = (TextView) v.findViewById(R.id.textViewCategory1);
        imageViewBMI = (ImageView) v.findViewById(R.id.imageViewBMI);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        return v;
    }

    private void showInfo()
    {
        double height = Double.parseDouble(editTextHeight.getText().toString());
        double mass = Double.parseDouble(editTextMass.getText().toString());
        double index = (mass / (height * height)) * 100;
        index = Math.round(index);
        index = index / 100;

        textViewIndex.setText(index + " BMI");
        BMI bmi = BMI.getCategory(index);
        textViewCategory.setText(bmi + "");
        imageViewBMI.setImageResource(bmi.getImage());
    }

    private enum BMI
    {
        GROOTONDERGEWICHT(0, 0.0, 15.0, R.drawable.silhouette_1),
        ERNSTIGONDERGEWICHT(1, 15.0, 16.0, R.drawable.silhouette_2),
        ONDERGEWICHT(2, 16.0, 18.5, R.drawable.silhouette_3),
        NORMAAL(3, 18.5, 25.0, R.drawable.silhouette_4),
        OVERGEWICHT(4, 25.0, 30.0, R.drawable.silhouette_5),
        MATIGOVERGEWICHT(5, 30.0, 35.0, R.drawable.silhouette_6),
        ERNSTIGOVERGEWICHT(6, 35.0, 40.0, R.drawable.silhouette_7),
        ZEERGROOTOVERGEWICHT(7, 40.0, Integer.MAX_VALUE, R.drawable.silhouette_8);

        private double upperBoundary;
        private double lowerBoundary;
        private int index;
        private int image;

        public double getLowerBoundary() {
            return lowerBoundary;
        }

        public double getUpperBoundary() {
            return upperBoundary;
        }

        public int getImage() {
            return image;
        }

        BMI(int tempIndex, double tempLowerBoundary, double tempUpperBoundary, int tempImage)
        {
            this.index = tempIndex;
            this.upperBoundary = tempUpperBoundary;
            this.lowerBoundary = tempLowerBoundary;
            this.image = tempImage;
        }

        private boolean isInBoundary(double index)
        {
            if(index > this.lowerBoundary && index <= this.upperBoundary)
                return true;
            return false;
        }

        public static BMI getCategory(double index)
        {
            for (BMI bmi : BMI.values())
            {
                if(bmi.isInBoundary(index))
                {
                    return bmi;
                }
            }
            return null;
        }
    }
}
