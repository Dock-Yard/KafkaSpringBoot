package com.example.KafkaSpringBoot.pojo;

public class AirPlane {

    Integer id;
    String company;

    public AirPlane() {
    }
    public AirPlane(Integer id, String company) {
        this.id = id;
        this.company = company;
    }

    @Override
    public String toString() {
        return "AirPlane{" +
                "id=" + id +
                ", company='" + company + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

}
