/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninemensmorris.networking;

import java.io.Serializable;

/**
 *
 * @author aditeya
 */
public enum NetworkCommand implements Serializable{
    LIST_ROOMS,
    CHOOSE_ROOM,
    ROOM_FULL,
    ROOM_ACQ,
    CONFIRM
}
