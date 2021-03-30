import java.util.concurrent.BlockingQueue;

public class AddThreadValues extends Thread{

    BlockingQueue<String> blockingQueue;
    int calculatedValue = 0;

    public AddThreadValues(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        for (String value: blockingQueue){
            calculatedValue += Integer.parseInt(value);
        }
        try {
            blockingQueue.put(String.valueOf(calculatedValue));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
