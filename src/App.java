import java.util.ArrayList;
import java.util.List;

class ParkingLot {
    static int occupiedSpots = 0;
    static List<Gate> gates = new ArrayList<>();
}


public class App {
    public static void main(String[] args) throws Exception {
        // Initialize gates
        ParkingLot.gates.add(new Gate());
        ParkingLot.gates.add(new Gate());
        ParkingLot.gates.add(new Gate());
        
        List<Car> carsList = InputParser.readCars("src/Cars.txt");
        
        // Start all car threads
        for(Car car : carsList) {
            car.start();
            Thread.sleep(100);
        }
        
        // Wait for all cars to finish
        for(Car car : carsList) {
            car.join();
        }

        System.out.println("Total number of cars: " + carsList.size());
        
        // Print final statistics
        for(Gate gate : ParkingLot.gates) {
            gate.printStats(ParkingLot.gates.indexOf(gate));
        }
    }
}