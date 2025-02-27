package com.mycompany.webapplicationdb.model;

/**
 * Represents a user account in the system
 */
public class Account
{

    private String userName;
    private String password;
    private String userRole;

    // Default constructor
    public Account()
    {
    }

    // Parameterized constructor
    public Account(String userName, String password, String userRole)
    {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    // Getters and Setters
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUserRole()
    {
        return userRole;
    }

    public void setUserRole(String userRole)
    {
        this.userRole = userRole;
    }

    // Helper methods
    public boolean isAdmin()
    {
        return "ADMIN".equals(userRole) || "SUPER_ADMIN".equals(userRole);
    }

    public boolean isSuperAdmin()
    {
        return "SUPER_ADMIN".equals(userRole);
    }

    @Override
    public String toString()
    {
        return "Account{" + "userName=" + userName + ", userRole=" + userRole + '}';
    }
}
