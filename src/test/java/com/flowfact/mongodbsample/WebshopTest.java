package com.flowfact.mongodbsample;

import com.mongodb.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WebshopTest {
    private DBCollection customers;

    @Before
    public void setUp() throws Exception {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("webshop");
        customers = db.getCollection("customer");
        customers.remove(new BasicDBObject());
    }

    @Test
    public void shouldWorkWithMongoDB() {
        DBObject customerMaxMustermann = new BasicDBObject()
                .append("id", "4711")
                .append("name","Max Mustermann")
                .append("city","Cologne")
                .append("numberOfOrders", 3);
        customers.insert(customerMaxMustermann);
        DBObject savedCustomer = customers.findOne();
        assertNotNull(savedCustomer);
        assertEquals("4711", savedCustomer.get("id"));
    }

   /* {"id" : 1234, "name": "Otto Normal", city: "Berlin",#
      numberOfOrders: 4, bankData: {accountNumber : "9876543210", bankCode : "30020011", accountHolder : "Otto Normal"}*/

    private void insertCustomerOttoNormal() {
        Customer customer = new Customer();
        customer.setId("1234");
        customer.setName("Otto Normal");
        customer.setCity("Berlin");
        customer.setNumberOfOrders(4);

        BankData bankData = new BankData();
        bankData.setAccountNumber("9876543210");
        bankData.setBankCode("30020011");
        bankData.setAccountHolder("Otto Normal");
        customer.setBankData(bankData);

        customers.setObjectClass(Customer.class);
        customers.insert(customer);
    }

    @Test
    public void shouldSaveAndFindCustomers() {
        insertCustomerOttoNormal();

        // attention: all field names here begin with a capital letter
        DBCursor result = customers.find(new BasicDBObject("City", "Berlin"));
        assertTrue(result.hasNext());
        Customer savedCustomer = (Customer) result.next();
        assertEquals("1234", savedCustomer.getId());
        assertEquals("30020011", savedCustomer.getBankData().getBankCode());
    }

    @Test
    public void shouldIncrementTheNumberOfOrdersIfTheCustomerPlaceAnOrder() {
        insertCustomerOttoNormal();

        customers.update(new BasicDBObject("Id", "1234"),
                new BasicDBObject("$inc",
                        new BasicDBObject("NumberOfOrders", 1)));
        Customer savedCustomer = (Customer) customers
                .findOne(new BasicDBObject("NumberOfOrders", new BasicDBObject("$gt", 4)));
        assertNotNull(savedCustomer);
        assertEquals("Otto Normal", savedCustomer.getName());
        assertEquals(5, savedCustomer.getNumberOfOrders());
    }
}
