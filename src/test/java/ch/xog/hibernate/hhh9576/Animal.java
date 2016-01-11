package ch.xog.hibernate.hhh9576;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Animal {
    @Id
    Long id;
    String name;
    Integer age;
}
