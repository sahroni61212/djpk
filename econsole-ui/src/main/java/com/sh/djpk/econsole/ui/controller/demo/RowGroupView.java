package com.sh.djpk.econsole.ui.controller.demo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
 
@ManagedBean(name="dtRowGroupView")
@ViewScoped
public class RowGroupView implements Serializable {
     
    private List<Car> cars;
     
    @ManagedProperty("#{carService}")
    private CarService service;
 
    @PostConstruct
    public void init() {
        cars = service.createCars(50);
    }
     
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(CarService service) {
        this.service = service;
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
}