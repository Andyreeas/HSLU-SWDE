package ch.hslu.swde.wda.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "City")
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("serial")
public class City extends AuditModel{

    @Id
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    public City() {
    }

    public City(Long id, @NotBlank @Size(min = 3, max = 50) String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
