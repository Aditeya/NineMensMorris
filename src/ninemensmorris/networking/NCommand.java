/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;

/**
 * NCommand Class is used to send commands, room numbers and room lists.
 * 
 * @author aditeya
 */
public class NCommand implements Serializable {

    private NetworkCommand command;
    private int room;
    private int[][] rooms;

    //<editor-fold defaultstate="collapsed" desc="getters">
    public NetworkCommand getCommand() {
        return command;
    }

    public int getRoom() {
        return room;
    }

    public int[][] getRooms() {
        return rooms;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="setters">
    public void setCommand(NetworkCommand command) {
        this.command = command;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setRooms(int[][] rooms) {
        this.rooms = rooms;
    }
    //</editor-fold>
}
