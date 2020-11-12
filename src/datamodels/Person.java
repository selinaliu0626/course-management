package datamodels;

import exceptionhandlers.InvalidDataException;
import interfaces.IPerson;

import java.time.LocalDate;

import static controllers.Application.getDEBUG_LOGGER;

public abstract class Person implements IPerson {
    private String name;

    private String address;

    private LocalDate dateOfBirth;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String p_name) throws InvalidDataException {
        //Test for valid name
        if(p_name == null || p_name.isBlank()){
            throw new InvalidDataException("Name required");
        }
        //If valid,set the name
        name = p_name;
        getDEBUG_LOGGER().finest("setting name to: "+name);
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String p_address)throws InvalidDataException {
        //Test for valid address
        if(p_address == null || p_address.isBlank()){
            throw new InvalidDataException("Address required");
        }
        //If valid,set for the address
        address = p_address;
        getDEBUG_LOGGER().finest("setting address to: "+address);
    }

    @Override
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void setDateOfBirth(LocalDate p_dateOfBirth)throws InvalidDataException {
        //Test for valid date of birth
        if(p_dateOfBirth==null){
            this.dateOfBirth = null;
            throw new InvalidDataException(":Date of birth not specified, setting to null");
        }
        dateOfBirth = p_dateOfBirth;
        getDEBUG_LOGGER().finest("setting birthday to: "+dateOfBirth.toString());
    }

    @Override
    public abstract String toString();
}
