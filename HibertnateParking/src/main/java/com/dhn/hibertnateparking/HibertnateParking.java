/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.dhn.hibertnateparking;

import com.dhn.repositories.impl.ParkingLotRepositoryImpl;
import com.dhn.repositories.impl.UserRepositoryImpl;
import java.util.HashMap;
import java.util.Map;



/**
 *
 * @author dhngu
 */
public class HibertnateParking {
    

    public static void main(String[] args) {
//        ParkingLotRepositoryImpl p = new ParkingLotRepositoryImpl();
//        p.getParkingLots().forEach(c -> System.out.println(c.getName()));

        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        ParkingLotRepositoryImpl s = new ParkingLotRepositoryImpl();
        s.getParkingLots(params).forEach(p -> System.out.printf("%d - %s\n",p.getLotId(), p.getName()));
        
        Map<String, String> prs = new HashMap<>();
        
        prs.put("kw","a");
        UserRepositoryImpl a = new UserRepositoryImpl();
        a.getUsers(prs).forEach(p -> System.out.printf("%d - %s\n",p.getUserId(), p.getFullName()));
    }
}
