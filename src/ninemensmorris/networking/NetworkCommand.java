/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;

/**
 * NetworkCommand Enum Class is for NetworkCommands 
 *
 * @author aditeya
 */
public enum NetworkCommand implements Serializable{
    LIST_ROOMS,     // List all the rooms
    CHOOSE_ROOM,    // Choose a room
    ROOM_FULL,      // The room is full
    ROOM_ACQ,       // The room is acquired
    CONFIRM         // Confirm client is ready
}
