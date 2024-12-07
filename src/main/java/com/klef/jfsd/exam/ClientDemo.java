package com.klef.jfsd.exam;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Configure Hibernate
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                                                           .addAnnotatedClass(Project.class)
                                                           .buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            // Insert records
            Transaction tx = session.beginTransaction();

            session.save(new Project("AI Assistant", 12, 150000, "John Doe"));
            session.save(new Project("E-Commerce Platform", 8, 120000, "Jane Smith"));
            session.save(new Project("Mobile Banking App", 10, 95000, "Richard Roe"));
            session.save(new Project("Healthcare Portal", 15, 200000, "Emma Brown"));

            tx.commit();

            // Perform aggregate queries using Criteria API
            Criteria criteria = session.createCriteria(Project.class);

            // Count
            criteria.setProjection(Projections.rowCount());
            System.out.println("Count of projects: " + criteria.uniqueResult());

            // Max
            criteria.setProjection(Projections.max("budget"));
            System.out.println("Maximum budget: " + criteria.uniqueResult());

            // Min
            criteria.setProjection(Projections.min("budget"));
            System.out.println("Minimum budget: " + criteria.uniqueResult());

            // Sum
            criteria.setProjection(Projections.sum("budget"));
            System.out.println("Total budget: " + criteria.uniqueResult());

            // Average
            criteria.setProjection(Projections.avg("budget"));
            System.out.println("Average budget: " + criteria.uniqueResult());
        } finally {
            sessionFactory.close();
        }
    }
}
