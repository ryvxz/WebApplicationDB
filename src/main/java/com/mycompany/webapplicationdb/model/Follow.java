package com.mycompany.webapplicationdb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents user follows in the system
 */
public class Follow
{

    private String userName;
    private String follow1;
    private String follow2;
    private String follow3;

    // Default constructor
    public Follow()
    {
    }

    // Parameterized constructor
    public Follow(String userName, String follow1, String follow2, String follow3)
    {
        this.userName = userName;
        this.follow1 = follow1;
        this.follow2 = follow2;
        this.follow3 = follow3;
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

    public String getFollow1()
    {
        return follow1;
    }

    public void setFollow1(String follow1)
    {
        this.follow1 = follow1;
    }

    public String getFollow2()
    {
        return follow2;
    }

    public void setFollow2(String follow2)
    {
        this.follow2 = follow2;
    }

    public String getFollow3()
    {
        return follow3;
    }

    public void setFollow3(String follow3)
    {
        this.follow3 = follow3;
    }

    /**
     * Get all follows as a list
     *
     * @return List of usernames being followed, with null values excluded
     */
    public List<String> getFollowsAsList()
    {
        List<String> follows = new ArrayList<>();

        if (follow1 != null && !follow1.isEmpty())
        {
            follows.add(follow1);
        }
        if (follow2 != null && !follow2.isEmpty())
        {
            follows.add(follow2);
        }
        if (follow3 != null && !follow3.isEmpty())
        {
            follows.add(follow3);
        }

        return follows;
    }

    /**
     * Check if the user is already following the specified user
     *
     * @param username The username to check
     * @return true if already following, false otherwise
     */
    public boolean isFollowing(String username)
    {
        if (username == null)
        {
            return false;
        }

        return username.equals(follow1)
                || username.equals(follow2)
                || username.equals(follow3);
    }

    /**
     * Add a new follow if there's space available
     *
     * @param username The username to follow
     * @return true if successfully added, false if already at limit
     */
    public boolean addFollow(String username)
    {
        if (follow1 == null || follow1.isEmpty())
        {
            follow1 = username;
            return true;
        } else if (follow2 == null || follow2.isEmpty())
        {
            follow2 = username;
            return true;
        } else if (follow3 == null || follow3.isEmpty())
        {
            follow3 = username;
            return true;
        }

        return false; // No space available
    }

    /**
     * Remove a follow
     *
     * @param username The username to unfollow
     * @return true if successfully removed, false if not found
     */
    public boolean removeFollow(String username)
    {
        if (username == null)
        {
            return false;
        }

        if (username.equals(follow1))
        {
            follow1 = null;
            return true;
        } else if (username.equals(follow2))
        {
            follow2 = null;
            return true;
        } else if (username.equals(follow3))
        {
            follow3 = null;
            return true;
        }

        return false; // Not found
    }

    /**
     * Count the number of users being followed
     *
     * @return Number of follows (0-3)
     */
    public int countFollows()
    {
        int count = 0;
        if (follow1 != null && !follow1.isEmpty())
        {
            count++;
        }
        if (follow2 != null && !follow2.isEmpty())
        {
            count++;
        }
        if (follow3 != null && !follow3.isEmpty())
        {
            count++;
        }
        return count;
    }
}
