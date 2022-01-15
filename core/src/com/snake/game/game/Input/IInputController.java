package com.snake.game.game.Input;

import java.util.EventListener;

public interface IInputController extends EventListener {

    public void OnInputReceived(InputEvent e);


}
