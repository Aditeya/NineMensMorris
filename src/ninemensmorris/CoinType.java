/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nmmguisample;

/**
 *
 * @author LENOVO
 */
public enum CoinType {
BLACK(1),WHITE(-1);


final int moveDir;

    private CoinType(int moveDir) {
        this.moveDir = moveDir;
    }

}
