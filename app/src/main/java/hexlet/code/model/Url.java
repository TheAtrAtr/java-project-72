package hexlet.code.model;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;


@Entity
public class Url extends Model {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @WhenCreated
    private Instant createdAt;
}
