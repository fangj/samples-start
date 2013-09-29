package try1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created with IntelliJ IDEA.
 * User: jack_fang
 * Date: 13-9-29
 * Time: 下午2:28
 */
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;

    private Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
