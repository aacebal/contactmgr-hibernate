package com.adelacebal.contactmgr;

import com.adelacebal.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;


public class Application {
    //Hold a reusable reference to a SessionFactory
    public static final SessionFactory sessionFactory = buildSessionFactory();

    public static SessionFactory buildSessionFactory() {
        //Create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new Contact.ContactBuilder("Adel", "Acebal")
                .withEmail("aacebal@me.com")
                .withPhone(7865553333L)
                .build();

        save(contact);

        // Display a list of contacts
        for(Contact c : fetchAllContacts()) {
            System.out.println(c);
        }

    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);

        // Get a list of contact objects according to criteria object
        List<Contact> contacts = criteria.list();

        //Close session
        session.close();

        return contacts;
    }

    private static void save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the section to save the contact
        session.save(contact);

        //Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }
}
