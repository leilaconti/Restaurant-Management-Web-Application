package team14.arms.backend.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Customer data for identifying individual customers.
 */
@Entity
public class Customer extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    private String fullName;

    @Size(max = 255)
    private String details;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
