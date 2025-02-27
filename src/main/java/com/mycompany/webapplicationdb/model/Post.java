package com.mycompany.webapplicationdb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents user posts in the system
 */
public class Post
{

    private String userName;
    private String post1;
    private String post2;
    private String post3;
    private String post4;
    private String post5;

    // Default constructor
    public Post()
    {
    }

    // Parameterized constructor
    public Post(String userName, String post1, String post2, String post3, String post4, String post5)
    {
        this.userName = userName;
        this.post1 = post1;
        this.post2 = post2;
        this.post3 = post3;
        this.post4 = post4;
        this.post5 = post5;
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

    public String getPost1()
    {
        return post1;
    }

    public void setPost1(String post1)
    {
        this.post1 = post1;
    }

    public String getPost2()
    {
        return post2;
    }

    public void setPost2(String post2)
    {
        this.post2 = post2;
    }

    public String getPost3()
    {
        return post3;
    }

    public void setPost3(String post3)
    {
        this.post3 = post3;
    }

    public String getPost4()
    {
        return post4;
    }

    public void setPost4(String post4)
    {
        this.post4 = post4;
    }

    public String getPost5()
    {
        return post5;
    }

    public void setPost5(String post5)
    {
        this.post5 = post5;
    }

    /**
     * Get all posts as a list, from newest to oldest
     *
     * @return List of post strings, with null values excluded
     */
    public List<String> getPostsAsList()
    {
        List<String> posts = new ArrayList<>();

        // Add posts from newest to oldest, skipping nulls
        if (post5 != null && !post5.isEmpty())
        {
            posts.add(post5);
        }
        if (post4 != null && !post4.isEmpty())
        {
            posts.add(post4);
        }
        if (post3 != null && !post3.isEmpty())
        {
            posts.add(post3);
        }
        if (post2 != null && !post2.isEmpty())
        {
            posts.add(post2);
        }
        if (post1 != null && !post1.isEmpty())
        {
            posts.add(post1);
        }

        return posts;
    }

    /**
     * Add a new post, shifting existing posts if necessary
     *
     * @param content The content of the new post
     */
    public void addNewPost(String content)
    {
        // Shift posts to make room for the new post
        post1 = post2;
        post2 = post3;
        post3 = post4;
        post4 = post5;
        post5 = content;
    }

    /**
     * Count the number of posts the user has
     *
     * @return Number of posts (0-5)
     */
    public int countPosts()
    {
        int count = 0;
        if (post1 != null && !post1.isEmpty())
        {
            count++;
        }
        if (post2 != null && !post2.isEmpty())
        {
            count++;
        }
        if (post3 != null && !post3.isEmpty())
        {
            count++;
        }
        if (post4 != null && !post4.isEmpty())
        {
            count++;
        }
        if (post5 != null && !post5.isEmpty())
        {
            count++;
        }
        return count;
    }
}
