package br.isertech.com.easyfinancesback.entity;

import br.isertech.com.easyfinancesback.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "role-uuid-generator")
    @GenericGenerator(
            name = "role-uuid-generator",
            strategy = "br.isertech.com.easyfinancesback.util.IserUUIDGenerator",
            parameters = @Parameter(name = "prefix", value = "Role")
    )
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private RoleType roleName;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.roleName.toString();
    }
}
