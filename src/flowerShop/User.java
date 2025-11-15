package flowerShop;

public abstract class User {
    protected String name;
    protected String surname;
    protected String email;
    protected String hashpass;
    protected String address;
    protected int id;
    protected boolean logged;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() { return address; }

    public void setAddress(String address){ this.address = address; }

    public String getHashPass() {
        return hashpass;
    }

    public void setPassword(String encoded) {
        this.hashpass = encoded;
    }

    public int getId() {
        return id;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
