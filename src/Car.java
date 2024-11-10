import java.util.concurrent.Semaphore;

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
            // the car waits until the arrive time
            Thread.sleep(arriveTime * 1000);
            
            ParkingLot.gates.get(gateNumber-1).countCars++;

            long waitStartTime = 0;            
            
            synchronized (ParkingLot.class) {
                if (ParkingLot.occupiedSpots >= 4) {
                    waitStartTime = System.currentTimeMillis();
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " arrived at time " + arriveTime);
                    while (ParkingLot.occupiedSpots >= 4) {
                        System.out.println("Car " + carName + " from Gate " + gateNumber + " waiting for a spot");  
                        ParkingLot.class.wait();
                    }
                }

                this.waitTime = (System.currentTimeMillis() - waitStartTime) / 1000;
                System.out.println("waitTime: " + waitTime);
                sem.acquire();
                ParkingLot.occupiedSpots++;
                if (waitTime > 0) {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " after waiting for " + waitTime + " units of time. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                } else {
                    System.out.println("Car " + carName + " from Gate " + gateNumber + " parked. (Parking Status: " + (ParkingLot.occupiedSpots) + " spots occupied)");
                }
            }

            sleep(parkTime * 1000);

            synchronized (ParkingLot.class) {
                ParkingLot.occupiedSpots--;
                sem.release();
                System.out.println("Car " + carName + " from Gate " + gateNumber + " left after " + parkTime + " units of time. (Parking Status: " + ParkingLot.occupiedSpots + " spots occupied)");
                ParkingLot.class.notify();
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