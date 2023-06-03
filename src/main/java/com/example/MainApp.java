package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // Create Hibernate SessionFactory
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Create a new person
        Person person = new Person();
        person.setName("John Doe");
        person.setAge(30);

        // Save the person using Hibernate
        savePerson(sessionFactory, person);

        // Retrieve and display all persons
        System.out.println("All persons:");
        displayAllPersons(sessionFactory);

        // Update the person
        person.setName("Jane Doe");
        person.setAge(35);
        updatePerson(sessionFactory, person.getId(), person);

        // Retrieve and display the updated person
        System.out.println("Updated person:");
        displayPerson(sessionFactory, person.getId());

        // Delete the person
        deletePerson(sessionFactory, person.getId());

        // Retrieve and display all persons after deletion
        System.out.println("All persons after deletion:");
        displayAllPersons(sessionFactory);

        // Close the SessionFactory
        sessionFactory.close();
    }

    private static void savePerson(SessionFactory sessionFactory, Person person) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();
        session.close();
        System.out.println("Person saved successfully.");
    }

    private static void displayAllPersons(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Person> persons = session.createQuery("FROM Person").list();
        for (Person person : persons) {
            System.out.println(person);
        }
        session.getTransaction().commit();
        session.close();
    }

    private static void displayPerson(SessionFactory sessionFactory, Long personId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person person = session.get(Person.class, personId);
        System.out.println(person);
        session.getTransaction().commit();
        session.close();
    }

    private static void updatePerson(SessionFactory sessionFactory, Long personId, Person updatedPerson) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, personId);
        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        session.update(person);
        transaction.commit();
        session.close();
        System.out.println("Person updated successfully.");
    }

    private static void deletePerson(SessionFactory sessionFactory, Long personId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Person person = session.get(Person.class, personId);
        session.delete(person);
        transaction.commit();
        session.close();
        System.out.println("Person deleted successfully.");
    }
}
