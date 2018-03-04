/**
 * This program has the objective to simulate a human clicking a mouse button
 * It was built to cheat on the clicking games (click simulator)
 * However, it can be fit to different objectives.
 * 
 * @author Mauricio de Castro Pasquotto
 * @since 29/11/2015
 * 
 * @last_date 04/03/2018
 * Added some docs to the class and constants at the begging of the bot
 */
package botautoclicker;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class BotAutoClicker implements NativeKeyListener, NativeMouseMotionListener, NativeMouseListener{

    /**
     * Amount of clicks the bot will do
     * Everytime you click the button
     */
    private static final int Iterations = 250;
    
    /**
     * Time between each iteration
     * in Milliseconds
     */
    private static final int IterarionPauseTime = 5;
    
    /**
     * Escape key to close the program
     * You can only stop after the iterations are done
     * To do: A function to stop while iterating
     */
    private static final int StopKey = NativeKeyEvent.VC_ESCAPE;
    
    /**
     * Special key that will start the iterations
     */
    private static final int StartKey = NativeKeyEvent.VC_F9;
    
    /**
     * JnativeHook which registers the new hook 'BotAutoClicker'
     * 
     * @author Mauricio de Castro Pasquotto
     * @since 29/11/2015
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Change the level for all handlers attached to the default logger.
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.OFF);
        }
        
        try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        
        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(new BotAutoClicker());
        GlobalScreen.addNativeMouseMotionListener(new BotAutoClicker());
    }

    /**
     * Hook for key released
     * It fire after any key is released
     * If StopKey is released: The bot Stops
     * If StartKey is released: The bot runs the iterations
     * 
     * @author Mauricio de Castro Pasquotto
     * @since 29/11/2015
     * @param nke 
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        
        // Get the keyCode from the event
        // Print it out
        int pressedKeyCode = nke.getKeyCode();
        Object pressedKeyCodeObject = new Integer(pressedKeyCode);
        String pressedKeyText = nke.getKeyText((Integer)pressedKeyCodeObject);
        System.out.println(pressedKeyText);
        
        // Try to stop
        if (nke.getKeyCode() == BotAutoClicker.StopKey) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                Logger.getLogger(BotAutoClicker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Nuke the clicks
        if(nke.getKeyCode() == BotAutoClicker.StartKey)
            nuke(MouseInfo.getPointerInfo());
    }
    
    /**
     * Nuke the mouse click x times
     * 
     * @author Mauricio de Castro Pasquotto
     * @since 29/11/2015
     * @param x 
     */
    static void nuke(PointerInfo x){
        Robot r = null;
        boolean repeat = true;
        
        // Create a new robot which is the mouse
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(BotAutoClicker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Make the click happens x times
        // Make the Thread sleep x ms
        for (int i = 0; i < BotAutoClicker.Iterations; i++){
            try {
                Thread.sleep(BotAutoClicker.IterarionPauseTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(BotAutoClicker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Unused">
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        
    }
    
    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        
    }
    
    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {
        
    }
    
    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {
        
    }
    
    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        
    }
    
    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {
        
    }
    //</editor-fold>
    
}
