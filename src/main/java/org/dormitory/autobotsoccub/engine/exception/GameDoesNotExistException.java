package org.dormitory.autobotsoccub.engine.exception;

public class GameDoesNotExistException extends RuntimeException {

    public GameDoesNotExistException(Integer userId) {
        super(String.format("Game for userId: %d does not exist", userId));
    }
}
