package com.amazon.ata.trees.dao;

import com.amazon.ata.trees.model.Contact;
import com.amazon.ata.trees.model.Name;
import com.amazon.ata.trees.model.SortBy;
import com.amazon.ata.trees.model.SortOrder;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class for accessing contacts data.
 * Note that we are making this class a Singleton since we're storing our contacts in-memory inside of this class in
 * TreeMaps. If this wasn't a Singleton, then every request would get a new copy of ContactDao with empty TreeMaps.
 */
@Singleton
public class ContactDao {

    private final SortedMap<Name, Contact> contactsSortedByFirstName;
    private final SortedMap<Name, Contact> contactsSortedByLastName;


    @Inject
    public ContactDao() {
        this.contactsSortedByFirstName = new TreeMap<>(Comparator.comparing(Name::getFirstName)); //null;
        this.contactsSortedByLastName = new TreeMap<>(Comparator.comparing(Name::getLastName)); //null;
    }

    /**
     * Saves the given contact.
     * @param contact to add.
     * @return The added contact.
     */
    public Contact addContact(Contact contact) {
        // put a key value pair into contactsSortedByFirstName (contact.getName(), contact)
        // put a key value pair into contactsSortedByLastName (contact.getName(), contact)
        this.contactsSortedByFirstName.put(contact.getName(), contact);
        this.contactsSortedByLastName.put(contact.getName(), contact);
        return contact;
    }

    /**
     * Retrieves all contacts in the provided sort by order. If no sort parameters are given, returns the default
     * of sorting by last name in ascending order.
     * @param sortBy Attribute to sort contacts by, e.g. first name, last name.
     * @param sortOrder order to return contacts, either ascending or descending.
     * @return map of name to contacts in requested sorted order.
     */
    public SortedMap<Name, Contact> getContacts(SortBy sortBy, SortOrder sortOrder) {
        // TODO implement
        SortedMap<Name, Contact> baseMap = null;
        // sort by? first name or last name? (enums)
        if (sortBy == SortBy.FIRST_NAME) {
            baseMap = contactsSortedByFirstName;
        } else {
            baseMap = contactsSortedByLastName;
        }

        // sort order? ascending or descending? (Also enums)
        if (sortOrder == SortOrder.DESCENDING) {
            return new TreeMap<>(baseMap).descendingMap();
        } else {
            return new TreeMap<>(baseMap);
        }

        //return null;
    }

    /**
     * Removes all contacts.
     */
    public void clearContacts() {
        this.contactsSortedByFirstName.clear();
        this.contactsSortedByLastName.clear();
    }

    /**
     * Retrieves a portion of the contacts whose Name is equal to or after the given startKey based on the sorting
     * parameters.
     *
     * @param startKey The Name that should be the first contact in the returned Map
     * @param sortBy Attribute to sort contacts by, e.g. first name, last name.
     * @param sortOrder order to return contacts, either ascending or descending.
     * @return portion of contacts that includes startKey and all contacts after startKey based on requested sorted
     * order.
     */
    public SortedMap<Name, Contact> getContactsStartingAt(Name startKey, SortBy sortBy, SortOrder sortOrder) {
        // TODO implement
        return getContacts(sortBy, sortOrder).tailMap(startKey);
        //return null;
    }

}
