package pl.wsb.fitnesstracker.user.api;

/**
 * Lightweight DTO exposing only basic user information for nested objects in other DTOs.
 * Contains: id, firstName, lastName, email.
 */
public class UserDtoLite {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    /**
     * Default constructor for UserDtoLite.
     */
    public UserDtoLite() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id user id
     * @param firstName user first name
     * @param lastName user last name
     * @param email user email
     */
    public UserDtoLite(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "UserDtoLite{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
