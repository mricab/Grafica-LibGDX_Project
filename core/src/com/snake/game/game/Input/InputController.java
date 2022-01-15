package com.snake.game.game.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import javax.swing.event.EventListenerList;

public class InputController extends Thread{

    private boolean listen;
    private EventListenerList listeners;

    public InputController() {
        listeners = new EventListenerList();
        listen = true;
    }

    public void addListener(IInputController i) {
        listeners.add(IInputController.class, i);
    }

    public void removeListener(IInputController i) {
        listeners.remove(IInputController.class, i);
    }

    public void cancel() {
        listen = false;
    }

    public void run() {
        System.out.println("Input Controller running");

        while(listen) {
            try {
//                System.out.println("while");
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP))    InputReceivedDispatcher(new InputEvent(this, Input.Keys.UP));
                if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))  InputReceivedDispatcher(new InputEvent(this, Input.Keys.DOWN));
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT))  InputReceivedDispatcher(new InputEvent(this, Input.Keys.LEFT));
                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) InputReceivedDispatcher(new InputEvent(this, Input.Keys.RIGHT));
                if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) InputReceivedDispatcher(new InputEvent(this, Input.Keys.ENTER));
                if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { InputReceivedDispatcher(new InputEvent(this, Input.Keys.ESCAPE)); break; }
                Thread.sleep(10);
            } catch (Exception e) {
                break;
            }
        }
    }

    protected void InputReceivedDispatcher(InputEvent e) {
        System.out.println("dispatching");

        IInputController[] ls = listeners.getListeners(IInputController.class);
        for (IInputController l: ls) {
            l.OnInputReceived(e);
        }
    }

}
