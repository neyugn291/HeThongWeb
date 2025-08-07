/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dhn.hibertnateparking;

import com.dhn.pojo.Booking;
import com.dhn.pojo.Invoice;
import com.dhn.pojo.Notification;
import com.dhn.pojo.ParkingLot;
import com.dhn.pojo.ParkingSlot;
import com.dhn.pojo.Payment;
import com.dhn.pojo.Review;
import com.dhn.pojo.User;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author dhngu
 */
public class HibernateUtils {
    private static final SessionFactory FACTORY;
    
    static {
        Configuration conf = new Configuration();
        Properties props = new Properties();
        
        props.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
        props.put(Environment.JAKARTA_JDBC_DRIVER,"com.mysql.cj.jdbc.Driver");
        props.put(Environment.JAKARTA_JDBC_URL,"jdbc:mysql://localhost/parkingwebdb");
        props.put(Environment.JAKARTA_JDBC_USER,"root");
        props.put(Environment.JAKARTA_JDBC_PASSWORD,"Demo@123");
        props.put(Environment.SHOW_SQL, "true");
        conf.setProperties(props);
        
        conf.addAnnotatedClass(ParkingLot.class);
        conf.addAnnotatedClass(Booking.class);
        conf.addAnnotatedClass(Invoice.class);
        conf.addAnnotatedClass(Notification.class);
        conf.addAnnotatedClass(ParkingSlot.class);
        conf.addAnnotatedClass(Payment.class);
        conf.addAnnotatedClass(Review.class);
        conf.addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        
        FACTORY = conf.buildSessionFactory(serviceRegistry);
    }
    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
    
}
