package hu.tsystems.msstools.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the System entity.
 */
public class SystemAppDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String ip;

    @NotNull
    private Integer port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemAppDTO systemDTO = (SystemAppDTO) o;
        if(systemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), systemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SystemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ip='" + getIp() + "'" +
            ", port='" + getPort() + "'" +
            "}";
    }
}
