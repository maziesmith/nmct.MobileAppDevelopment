package be.howest.nmct.android.bmi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMIFragment extends Fragment {

    private EditText yourHeightView;
    private EditText yourMassView;
    private TextView yourIndexView;
    private TextView yourCategoryView;
    private Button updateView;

    public BMIFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bmi, container, false);

        this.yourHeightView = (EditText) v.findViewById(R.id.height);
        this.yourMassView = (EditText) v.findViewById(R.id.mass);
        this.yourIndexView = (TextView) v.findViewById(R.id.index);
        this.yourCategoryView = (TextView) v.findViewById(R.id.category);
        this.updateView = (Button) v.findViewById(R.id.updateBMI);

        this.updateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIndex(v);
                showCategory(v);
            }
        });

        return v;
    }

    private void showIndex(View v)
    {
        double bmi = calculateBMI();
        this.yourIndexView.setText("" + bmi + " BMI");
    }

    private void showCategory(View v)
    {
        double bmi = calculateBMI();
        BMICategory bmiCategory = BMICategory.getCategory(bmi);
        this.yourCategoryView.setText("" + bmiCategory);
    }

    private double calculateBMI()
    {
        double mass = Double.parseDouble(this.yourMassView.getText().toString());
        double height = Double.parseDouble(this.yourHeightView.getText().toString());
        double bmi = (mass / (height * height)) * 100;
        bmi = Math.round(bmi);
        bmi = bmi / 100;
        return bmi;
    }

    private enum BMICategory
    {
        GROOTONDERGEWICHT(0.0f, 15.0f),
        ERNSTIGONDERGEWICHT(15.0f, 16.0f),
        ONDERGEWICHT(16.0f, 18.5f),
        NORMAAL(18.5f, 25.0f),
        OVERGEWICHT(25.0f, 30.0f),
        MATIGOVERGEWICHT(30.0f, 35.0f),
        ERNSTIGOVERGEWICHT(35.0f, 40.0f),
        ZEERGROOTOVERGEWICHT(40.0f, Double.MAX_VALUE);

        private double lowerBoundary;
        private double upperBoundary;

        public double getLowerBoundary() {
            return lowerBoundary;
        }

        public double getUpperBoundary() {
            return upperBoundary;
        }

        BMICategory(double tempLowerBoundary, double tempUpperBoundary)
        {
            this.lowerBoundary = tempLowerBoundary;
            this.upperBoundary = tempUpperBoundary;
        }

        public static BMICategory getCategory(double index)
        {
            for(BMICategory bmiCategory : BMICategory.values())
            {
                if(bmiCategory.isInBoundary(index))
                    return bmiCategory;
            }
            return null;
        }

        public Boolean isInBoundary(double index)
        {
            if(index > lowerBoundary && index <= upperBoundary)
                return true;
            else
                return false;
        }
    }
}
