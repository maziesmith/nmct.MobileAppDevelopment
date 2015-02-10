package kristofcolpaert.com.week1oefening2;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class BMIInfo
{
    private float height;
    private int mass;
    private float bmiIndex;

    public BMIInfo(float height, int mass, float bmiIndex)
    {
        this.height = height;
        this.mass = mass;
        this.bmiIndex = bmiIndex;
    }

    public BMIInfo()
    {
        this(1.70f, 70, 0.0f);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public void setBmiIndex(float bmiIndex) {
        this.bmiIndex = bmiIndex;
    }

    public float recalculate()
    {
        return getMass() / (getHeight() * getHeight());
    }

    @Override
    public String toString() {
        return "Uw BMI bedraagt: " + recalculate() + ", uw categorie is: " + Category.getCategory(recalculate());
    }

    /**
     * Enumeratie met alle categorieën.
     */
    public enum Category
    {
        GROOTONDERGEWICHT(0, 15),
        ERNSTIGONDERGEWICHT(15, 16),
        ONDERGEWICHT(16, 18.5f),
        NORMAAL(18.5f, 25),
        OVERGEWICHT(25, 30),
        MATIGOVERGEWICHT(30, 35),
        ERNSTIGOVERGEWICHT(35, 40),
        ZEERGROOTOVERGEWICHT(40, 10000);

        private float lowerBoundary;
        private float upperBoundary;

        Category(float lowerBoundary, float upperBoundary)
        {
            this.lowerBoundary = lowerBoundary;
            this.upperBoundary = upperBoundary;
        }

        public float getLowerBoundary() {
            return lowerBoundary;
        }

        public float getUpperBoundary() {
            return upperBoundary;
        }

        public Boolean isInBoundary(float index)
        {
            if(index > getLowerBoundary() && index <= getUpperBoundary())
                return true;
            return false;
        }

        public static Category getCategory(float index)
        {
            for(Category category : Category.values())
            {
                if(category.isInBoundary(index))
                    return category;
            }
            return null;
        }
    }
}
