class Gate extends Thread {
    int countCars = 0;
    
    public void printStats(int gateNumber) {
        System.out.println("Gate " + (gateNumber+1) + " received " + countCars + " cars");
    }
}