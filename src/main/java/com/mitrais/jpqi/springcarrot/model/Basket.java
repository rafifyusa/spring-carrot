package com.mitrais.jpqi.springcarrot.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Document("baskets")
public class Basket {
//    @Autowired
//    SequenceService sequenceService;

    @Transient
    public static final String SEQUENCE_NAME = "basket_sequence";

    @Id
  //  @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @DBRef
    private Employee employee;
    private double carrot_amt;

/*
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Basket> event) {
        event.getSource().setId(sequenceService.generateSequence(Basket.SEQUENCE_NAME));
    }
*/


    //Constructors
    public Basket() {
    }

    public Basket(int id, String name, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public double getCarrot_amt() {
        return carrot_amt;
    }

    public void setCarrot_amt(double carrot_amt) {
        this.carrot_amt = carrot_amt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
