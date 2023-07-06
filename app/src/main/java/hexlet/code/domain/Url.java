package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;


@Entity
public final class Url extends Model {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @WhenCreated
    private Instant createdAt;
    @OneToMany
    private List<UrlCheck> urlChecks;

    public Url() {
    }

    public Url(String urlFromInputField) {
        this.name = urlFromInputField;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public String getName() {
        return name;
    }

    public List<UrlCheck> getUrlChecks() {
        return this.urlChecks;
    }

    public long getId() {
        return id;
    }

    public Instant getLastCheckDate() {
        if (!urlChecks.isEmpty()) {
            return urlChecks.get(urlChecks.size() - 1).getCreatedAt();
        }
        return null;
    }

    public Integer getLastCheckStatus() {
        if (!urlChecks.isEmpty()) {
            return urlChecks.get(urlChecks.size() - 1).getStatusCode();
        }
        return null;
    }
}
