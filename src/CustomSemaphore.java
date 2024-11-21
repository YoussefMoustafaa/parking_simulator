public class CustomSemaphore {
    protected int value;

    protected CustomSemaphore() {
        this.value = 0;
    }

    protected CustomSemaphore(int initial) {
        this.value = initial;
    }

    public synchronized void aquire() {
        this.value--;
        if (value < 0) {
            try {
                wait();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void release() {
        this.value++;
        if (value <= 0)
            notify();
    }
}
