package ca.junctionbox.sso.commands;

import ca.junctionbox.sso.models.Configurable;

import java.util.LinkedList;

public class CommandManager implements IManager {
    LinkedList<Commandable> _commandQueue;
    Configurable [] _configListeners;

    public CommandManager() {
        _commandQueue = new LinkedList<Commandable>();
    }

    public void addCommand(Commandable $command) {
        synchronized (_commandQueue) {
            _commandQueue.push($command);
            _commandQueue.notifyAll();
        }
    }

    public void executeQueue() throws InterruptedException {
        synchronized (_commandQueue) {
            while(_commandQueue.size() > 0) {
                _commandQueue.removeFirst().execute();
            }
            System.out.println("Wait()");
            _commandQueue.wait();
            System.out.println("Done waiting.");
        }
    }

    public void engage() {
        while(true) {
            try {
                executeQueue();
            } catch (InterruptedException e) {
                /* just break from wait() */
            }
        }
    }
}
