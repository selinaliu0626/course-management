package interfaces;

import java.time.LocalDate;
import exceptionhandlers.InvalidDataException;

public interface IPerson {
    String getName();

    void setName(String p_name) throws InvalidDataException;

    String getAddress();

    void setAddress(String p_address)throws InvalidDataException;

    LocalDate getDateOfBirth();

    void setDateOfBirth(LocalDate p_dateOfBirth)throws InvalidDataException;

}
