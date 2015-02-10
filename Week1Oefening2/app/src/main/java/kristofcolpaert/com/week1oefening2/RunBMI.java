package kristofcolpaert.com.week1oefening2;

import java.util.Scanner;

/**
 * Created by kristofcolpaert on 09/02/15.
 */
public class RunBMI
{
    public static void main(String[] args) {
        BMIInfo bmi1 = new BMIInfo(1.70f, 70, 0.0f);
        System.out.println(bmi1);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your height in meter:");
        float height = Float.parseFloat(sc.nextLine());
        System.out.println("Enter your mass in kg:");
        int mass = Integer.parseInt(sc.nextLine());

        BMIInfo bmi2 = new BMIInfo(height, mass, 0.0f);
        System.out.println(bmi2);
    }
}
