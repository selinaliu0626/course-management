package datamodels;

import exceptionhandlers.InvalidDataException;
import interfaces.IClassroom;

import static controllers.Application.getDEBUG_LOGGER;

public class Classroom implements IClassroom {

    private String roomNumber;
    private String typeOfRoom;
    private int capacity;

    public void setRoomNumber(String p_roomNumber) throws InvalidDataException {
        // Test for valid room number
        if (p_roomNumber != null && p_roomNumber.isBlank()) {
            throw new InvalidDataException("No room number specified");
        }
        if (!p_roomNumber.matches("^[a-zA-Z]{2}[0-9]{3}$")) {
            throw new InvalidDataException("Invalid room number");
        } 
        // If valid, set room number
        String updatedRoomNumber = p_roomNumber.substring(0, 2).toUpperCase() +
                p_roomNumber.substring(2, 5);
        this.roomNumber = updatedRoomNumber;

        getDEBUG_LOGGER().finest("Setting room number: " + roomNumber);
    }

    public void setTypeOfRoom(String p_typeOfRoom) throws InvalidDataException {
        // Test for valid room type
        if (p_typeOfRoom != null && p_typeOfRoom.isBlank()) {
            throw new InvalidDataException("No room type specified");
        }
         if ((!p_typeOfRoom.equals("LAB")) && (!p_typeOfRoom.equals("CLASSROOM"))
                 && (!p_typeOfRoom.equals("LECTURE HALL"))) {
            throw new InvalidDataException("Invalid room type specified");
        }
        // If valid, set room type
        typeOfRoom = p_typeOfRoom;

        getDEBUG_LOGGER().finest("Setting type of room: " + typeOfRoom);
    }

    public void setCapacity(int p_capacity) throws InvalidDataException {
        // Test for valid room capacity
        if (p_capacity <= 0) {
            throw new InvalidDataException("Room capacity must be greater than 0");
        }
        // If valid, set room capacity
        capacity = p_capacity;

        getDEBUG_LOGGER().finest("Setting room capacity: " + capacity);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Classroom{" + "roomNumber=" + roomNumber + ", typeOfRoom=" + 
                typeOfRoom + ", capacity=" + capacity + '}';
    }

}
