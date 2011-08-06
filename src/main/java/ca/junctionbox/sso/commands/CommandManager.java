package ca.junctionbox.sso.commands;

import java.util.LinkedList;

public class CommandManager implements Runnable {
    LinkedList<Commandable> _commandQueue;

    public void executeQueue() throws InterruptedException {
        while(_commandQueue.size() != 0) {
            _commandQueue.removeFirst().execute();
        }
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        while(true) {
            try {
                executeQueue();
            } catch (InterruptedException e) {
                /* just break from sleep */
            }
        }
    }
}
