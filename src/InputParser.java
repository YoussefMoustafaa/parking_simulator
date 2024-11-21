import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputParser {
    public static List<Car> readCars(String fileName) {
        List<Car> cars = new ArrayList<>();
        CustomSemaphore sem = new CustomSemaphore(4);
        // Semaphore semaphore = new Semaphore(4); // 4 parking spots available

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                int gateNumber = Integer.parseInt(parts[0].split(" ")[1]);
                String carName = parts[1].split(" ")[1];
                int arriveTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkTime = Integer.parseInt(parts[3].split(" ")[1]);

                // Create a Car object
                Car car = new Car(sem, carName, gateNumber, parkTime, arriveTime);
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cars.sort((car1, car2) -> {
            int arrivalCompare = Integer.compare(car1.arriveTime, car2.arriveTime);
            if (arrivalCompare == 0) {
                // Extract numeric part from car names
                int num1 = Integer.parseInt(car1.carName.replaceAll("\\D+", ""));
                int num2 = Integer.parseInt(car2.carName.replaceAll("\\D+", ""));
                return Integer.compare(num1, num2);
            }
            return arrivalCompare;
        });

        return cars;
    }
}