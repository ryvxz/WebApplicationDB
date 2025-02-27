package com.mycompany.webapplicationdb.model;

import java.sql.Timestamp;

/**
 * Represents a message sent by a user to admin
 */
public class Message
{

    private int id;
    private String userName;
    private String message;
    private Timestamp timestamp;

    // Default constructor
    public Message()
    {
    }

    // Parameterized constructor
    public Message(int id, String userName, String message, Timestamp timestamp)
    {
        this.id = id;
        this.userName = userName;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Constructor without ID (for new messages)
    public Message(String userName, String message)
    {
        this.userName = userName;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {
        return "Message{" + "id=" + id + ", userName=" + userName
                + ", timestamp=" + timestamp + ", message=" + message + '}';
    }
}
