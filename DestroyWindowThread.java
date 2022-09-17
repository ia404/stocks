import java.awt.*;
public class DestroyWindowThread extends Thread {
    private Frame window;
    private long time;
    private Button button;

    /**
     * constructor in order to start the thread
     * @param givenWindow
     * @param givenButton
     * @param givenTime
     */
    public DestroyWindowThread(Frame givenWindow, Button givenButton, long givenTime){
        this.window = givenWindow;
        this.time = givenTime;
        this.button = givenButton;
    }
    
    public void run() {
        try {
            //turns off visibility of button so that duplicates cannot be made 
            if(this.button != null){
                this.button.setVisible(false);
                Thread.sleep(this.time);
                this.button.setVisible(true);
                //dispose the window after time is out (assuming the user is not interacting)
                this.window.dispose();
            } else {
                Thread.sleep(this.time);
                this.window.dispose();
            }
        } catch (InterruptedException e) {
        }
    }
}