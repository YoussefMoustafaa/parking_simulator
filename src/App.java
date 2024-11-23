import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class ParkingLot {
    static int occupiedSpots = 0;
    static List<Gate> gates = new ArrayList<>();
    static PriorityQueue<Car> waitingQueue = new PriorityQueue<>(Comparator.comparingInt(car -> car.arriveTime));
}


public class App {
    public static void main(String[] args) throws Exception {
        // Initialize gates
        for (int i = 0; i < 3; i++) {
            ParkingLot.gates.add(new Gate());
        }
        
        List<Car> carsList = InputParser.readCars("src/Cars.txt");
        
        // Start all car threads
        for(Car car : carsList) {
            car.start();
            Thread.sleep(50);
        }
        
        // Wait for all cars to finish
        for(Car car : carsList) {
            car.join();
        }

        System.out.println("\nTotal Cars Served: " + carsList.size());
        System.out.println("Current Cars in Parking: 0");
        System.out.println("Details:");
        
        // Print final statistics
        for(Gate gate : ParkingLot.gates) {
            gate.printStats(ParkingLot.gates.indexOf(gate));
        }
    }
}