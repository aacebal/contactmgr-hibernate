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

        int id = save(contact);

        // Display a list of contacts before the update
        System.out.printf("%n%nBefore the update%n%n");
        fetchAllContacts().stream().forEach(System.out::println);

        // Get the persisted contact
        Contact c = findContactById(id);

        // Update the contact
        c.setFirstName("Ivelise");

        //Persist the changes
        System.out.printf("%n%nUpdating ...%n%n");
        update(c);
        System.out.printf("%n%nUpdate complete!%n%n");

        //Display a list of contacts after the update
        System.out.printf("%n%nAfter the update%n%n");
        fetchAllContacts().stream().forEach(System.out::println);
    }

    private static Contact findContactById(int id) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        // Close the session
        session.close();

        // Return the object
        return contact;
    }

    private static void update(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin the transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.update(contact);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
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

    private static int save(Contact contact) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the section to save the contact
        int id = (int) session.save(contact);

        //Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();

        return id;
    }
}
