
@Entity
@Data
@ToString
@Table(name = "Usuarios")
public class Usuario {
    private Long id;
    private String user;
    private String password;
}
