/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The Rooms class is used to interact with Rooms double array in a thread safe
 * mannar.
 *
 * @author aditeya
 */
public class Rooms {

    private final ReadWriteLock lock;
    private final Lock writeLock;
    private final Lock readLock;
    private final int[][] rooms;

    /**
     * Room Constructor which takes the number Maximum rooms.
     * 
     * @param rooms Maximum Number of Rooms
     */
    public Rooms(int rooms) {
        this.lock = new ReentrantReadWriteLock();
        this.writeLock = lock.writeLock();
        this.readLock = lock.readLock();

        this.rooms = new int[rooms][2];
    }

    /**
     * Acquires the lock, chooses the room and returns true if successful.
     * If the selected room is not available then a false is returned 
     * to signify failure.
     * 
     * @param ID    Client ID
     * @param room  Room number of the room to be chosen.
     * @return      returns if the operation was a success.
     */
    public boolean chooseRoom(int ID, int room) {
        boolean success = false;

        try {
            writeLock.lock();

            for (int i = 0; i < rooms[room].length; i++) {
                if (rooms[room][i] == 0) {
                    rooms[room][i] = ID;
                    success = true;
                    break;
                }
            }
        } finally {
            writeLock.unlock();
        }

        return success;
    }

    /**
     * Aquires the lock and clears the room.
     * 
     * @param room Room number of the room to be cleared.
     */
    public void clearRoom(int room) {
        try {
            writeLock.lock();

            for (int i = 0; i < rooms[room].length; i++) {
                rooms[room][i] = 0;
            }
        } finally {
            writeLock.unlock();
        }
    }

    public int[][] getRooms() {
        try {
            readLock.lock();
            return this.rooms;
        } finally {
            readLock.unlock();
        }
    }

}
