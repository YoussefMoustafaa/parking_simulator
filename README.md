
# Parking Lot Simulation

This project simulates a parking lot with multiple gates and limited parking spots, using multithreading in Java. Cars arrive at the parking lot, wait if no spots are available, park for a specified duration, and then leave. The synchronization is managed using a custom semaphore to ensure proper access to parking spots.

---

## Features

- Simulates cars arriving from different gates at specified times.
- Cars wait if all parking spots are occupied and log their waiting status.
- Fair parking allocation managed by a custom semaphore.
- Detailed logging of car activities, including arrival, waiting, parking, and departure.
- Thread-safe handling of parking spots.

---

## How It Works

1. **Custom Semaphore**: 
   - Controls access to limited parking spots.
   - Handles fairness and ensures only one car modifies shared resources at a time.

2. **Car Threads**: 
   - Each car runs in its own thread.
   - Simulates waiting, parking, and leaving the parking lot.
   - Logs actions with timestamps and parking lot status.

3. **Parking Lot**: 
   - Shared resource that tracks the number of occupied spots and handles synchronization.
   - Stores the gates and their corresponding cars.

---

## Classes

### 1. **Car**
   - Represents a car as a thread.
   - Attributes:
     - `carName`: Name of the car.
     - `gateNumber`: The gate the car enters from.
     - `parkTime`: Time the car stays parked.
     - `arriveTime`: Time the car arrives at the parking lot.
   - Behavior:
     - Arrives, checks for available spots, and waits if necessary.
     - Parks and leaves after the specified time.

### 2. **CustomSemaphore**
   - A custom implementation of a semaphore to control parking spot allocation.
   - Methods:
     - `acquire()`: Decrements the semaphore count; blocks if no permits are available.
     - `release()`: Increments the semaphore count.

### 3. **ParkingLot**
   - Tracks the number of occupied spots and manages synchronization.
   - Stores the gates and their corresponding cars.

---

## Setup and Usage

### Prerequisites
- Java Development Kit (JDK) 8 or later.
- A Java IDE (e.g., IntelliJ, Eclipse) or a text editor with terminal access.

### Running the Simulation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/parking-lot-simulation.git
   cd parking-lot-simulation
   ```

2. Compile and Run

3. Observe the output logs in the console to see car activities.

---

## Example Output

```plaintext
Car 0 from Gate 1 arrived at time 0
Car 0 parked. (Parking Status: 1 spots occupied)
Car 1 from Gate 1 arrived at time 1
Car 1 parked. (Parking Status: 2 spots occupied)
Car 2 from Gate 2 arrived at time 1
Car 2 parked. (Parking Status: 3 spots occupied)
Car 3 from Gate 3 arrived at time 1
Car 3 parked. (Parking Status: 4 spots occupied)
Car 4 from Gate 3 arrived at time 2
Car 4 waiting for a spot
Car 3 left after 1 units of time. (Parking Status: 3 spots occupied)
Car 4 parked after waiting for 1 units of time. (Parking Status: 4 spots occupied)
```

---

## Configuration

Modify the following parameters in the `Car` class to customize the simulation:

- `gateNumber`: Gate number from which the car enters.
- `arriveTime`: Arrival time of the car (in seconds).
- `parkTime`: Duration the car stays parked (in seconds).

---

## Key Learnings

- **Thread Synchronization**: Using semaphores to manage concurrent access to shared resources.
- **Multithreading**: Simulating real-world scenarios with threads.
- **Fair Resource Allocation**: Ensuring fairness using semaphore mechanisms.

---

## Future Enhancements

- Add a graphical user interface (GUI) to visualize the parking lot in real-time.
- Enable dynamic configuration of parking spots and gates.
- Log car activities to a file for later analysis.
- Implement priority-based parking (e.g., VIP cars get priority).

---

## Author

This project was created as a learning exercise in multithreading and synchronization in Java.  
For questions or suggestions, please feel free to reach out!

---
