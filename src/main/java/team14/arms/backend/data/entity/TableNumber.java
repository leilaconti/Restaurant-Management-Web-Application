package team14.arms.backend.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Represents the table number assigned to the order.
 */
@Entity
public class TableNumber extends AbstractEntity {

    @Size(max = 255)
    @NotBlank
    @Column(unique = false)
    private String number;
    
    public TableNumber() {}

    public TableNumber(String number) {
      this.number = number;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
