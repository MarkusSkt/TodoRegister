package com.example.markus.todoregister.data;

/**
 * Created by Markus on 8.4.2017.
 * Class that provides some help for the user to use the app
 */
public class Help {

    private String helpTitle;
    private String helpContent;

    public Help(String helpTitle, String helpContent) {
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
    }

    public String getTitle() {
        return this.helpTitle;
    }
    public String getContent() {
        return this.helpContent;
    }
}
