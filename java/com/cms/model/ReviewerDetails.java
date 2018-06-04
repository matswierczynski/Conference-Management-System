package com.cms.model;

public class ReviewerDetails {
    private String firstName;
    private String lastName;
    private String email;
    private int papersNumber;
    private int conflictFactor;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPapersNumber() {
        return papersNumber;
    }

    public void setPapersNumber(int papersNumber) {
        this.papersNumber = papersNumber;
    }

    public int getConflictFactor() {
        return conflictFactor;
    }

    public void setConflictFactor(int conflictFactor) {
        this.conflictFactor = conflictFactor;
    }

}
