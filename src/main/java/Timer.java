import com.pi4j.io.gpio.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Timer implements Runnable {

    private final static GpioController gpio = GpioFactory.getInstance();
    private final static GpioPinDigitalOutput SENSORS = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
    private static char sensorState = 'L';

    private final AtomicInteger timeLeft = new AtomicInteger(0);
    public static Timer instance = new Timer();


    private Timer() {
    }


    @Override
    public void run() {
        while (true) {
            while (timeLeft.get() > 0) {
                try {
                    disable();
                    Thread.sleep(1000);
                    timeLeft.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            enable();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void set(int time) {
        timeLeft.set(time);
    }

    public int get() {
        return timeLeft.get();
    }

    public static Timer getInstance() {
        return instance;
    }

    private void disable() {
        if (sensorState == 'H')
            return;
        SENSORS.high();
        sensorState = 'H';
    }

    private void enable() {
        if (sensorState == 'L')
            return;
        SENSORS.low();
        sensorState = 'L';
    }
}
