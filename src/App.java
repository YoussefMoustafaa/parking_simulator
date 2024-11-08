import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class ParkingLot {
    static int occupiedSpots = 0;
    static List<Gate> gates = new ArrayList<>();
}

class Gate {
    int countCars = 0;
    
    public void printStats(int gateNumber) {
        System.out.println("Gate " + (gateNumber+1) + " received " + countCars + " cars");
    }
}

class Car extends Thread {
    Semaphore sem;
    String carName;
    int gateNumber;
    int parkTime;
    int arriveTime;

    private long waitTime;

    public Car(Semaphore sem ,String carName,int gateNumber , int parkTime , int arriveTime){
        super(carName);
        this.sem = sem;
        this.carName = carName;
        this.gateNumber = gateNumber;
        this.parkTime = parkTime;
        this.arriveTime=arriveTime;
        this.waitTime = 0;
    }

    public void run() {
        try {
            // Wait until arrive time
            Thread.sleep(arriveTime * 1000);
            
            // Increment gate counter
            ParkingLot.gates.get(gateNumber-1).countCars++;
            
            long waitStartTime = System.currentTimeMillis();
            // Try to park
            synchronized (ParkingLot.class) {
                System.out.println("Car " + carName + " from Gate " + gateNumber + " arrived at time " + arriveTime);
                while (ParkingLot.occupiedSpots >= 4) {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " waiting for a spot");  
                    ParkingLot.class.wait();
                }

                this.waitTime = (System.currentTimeMillis() - waitStartTime) / 1000;
                // System.out.println("waitTime: " + waitTime);
                sem.acquire();
                ParkingLot.occupiedSpots++;
                if (waitTime > 0) {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " after waiting for " + waitTime + " units of time. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                } else {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " parked. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                }
            }

            // Park for specified time
            sleep(parkTime * 1000);

            synchronized (ParkingLot.class) {
                ParkingLot.occupiedSpots--;
                sem.release();
                System.out.println("Car " + carName + " from Gate " + gateNumber + " left after " + parkTime + " units of time. (Parking Status: " + ParkingLot.occupiedSpots + " spots occupied)");
                ParkingLot.class.notifyAll();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    public void printCars() {
        System.out.println("Car " + carName + " arrived at Gate " + gateNumber + 
                         " at time " + arriveTime + " and parks for " + parkTime + " units of time");
    }
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