
class Car extends Thread {
    CustomSemaphore sem;
    String carName;
    int gateNumber;
    int parkTime;
    int arriveTime;

    public Car(CustomSemaphore sem ,String carName,int gateNumber , int parkTime , int arriveTime){
        super(carName);
        this.sem = sem;
        this.carName = carName;
        this.gateNumber = gateNumber;
        this.parkTime = parkTime;
        this.arriveTime=arriveTime;
    }

    @Override
    public void run() {
        try {
            // the car waits until the arrive time
            Thread.sleep(arriveTime * 1000);
            
            long waitStartTime = 0;            
            waitStartTime = System.currentTimeMillis();
            
            System.out.println("Car " + carName + " from Gate " + gateNumber + " arrived at time " + arriveTime);
            
            if (ParkingLot.occupiedSpots >= 4) {
                System.out.println("Car " + carName + " from Gate " + gateNumber + " waiting for a spot");  
            }
            
            sem.acquire();
            
            ParkingLot.gates.get(gateNumber-1).addCar();  // add car to its gate
            int waitTime = (int) ((System.currentTimeMillis() - waitStartTime) / 1000);

            synchronized (ParkingLot.class) {
                ParkingLot.occupiedSpots++;
                if (waitTime > 0) {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " parked after waiting for " + waitTime + " units of time. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                } else {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " parked. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                }
            }

            sleep(parkTime * 1000 + 300);

            synchronized (ParkingLot.class) {
                ParkingLot.occupiedSpots--;
                System.out.println("Car " + carName + " from Gate " + gateNumber + " left after " + parkTime + " units of time. (Parking Status: " + ParkingLot.occupiedSpots + " spots occupied)");
            }
            
            sem.release();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    public void printCars() {
        System.out.println("Car " + carName + " arrived at Gate " + gateNumber + 
                         " at time " + arriveTime + " and parks for " + parkTime + " units of time");
    }
}