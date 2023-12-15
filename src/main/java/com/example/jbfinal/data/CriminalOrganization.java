package com.example.jbfinal.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CriminalOrganization {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Convict> convict;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriminalOrganization crimeOrg1 = (CriminalOrganization) o;

        return crimeOrg1.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return  id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CriminalOrganization{" +
                "\n\tid : " + id +
                "\n\tname : '" + name + '\'' +
                ", convicts : " + convict
                .stream()
                .map(acc -> Long.toString(acc.getId()))
                .reduce("'ids:", (a1, a2) -> a1 + " " + a2) + '\'' +
                "\n}";
    }
}
