package com.example.jbfinal.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Convict {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String surname;
    private String alias;
    private Double height;
    @ManyToOne
    private HairColor hairColor;
    @ManyToOne
    private EyeColor eyeColor;
    private String distinguishingFeatures;
    @ManyToOne
    private Country citizenship;
    private String placeAndTimeOfBirth;
    private String lastPlaceOfHabitat;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Language> languages;
    private String criminalSpecialization;
    private String lastCriminalCase;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<CriminalOrganization> criminalOrganization;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Convict> accomplices;

    public static class Builder {

        private final Convict convict;

        public Builder() {
            this.convict = new Convict();
        }

        public Builder setId(Long Id) {
            convict.Id = Id;
            return this;
        }

        public Builder setName(String name) {
            convict.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            convict.surname = surname;
            return this;
        }

        public Builder setAlias(String alias) {
            convict.alias = alias;
            return this;
        }

        public Builder setHeight(Double height) {
            convict.height = height;
            return this;
        }


        public Builder setHairColor(HairColor hairColor) {
            convict.hairColor = hairColor;
            return this;
        }

        public Builder setEyeColor(EyeColor eyeColor) {
            convict.eyeColor = eyeColor;
            return this;
        }

        public Builder setDistinguishingFeatures(String df) {
            convict.distinguishingFeatures = df;
            return this;
        }

        public Builder setCitizenship(Country country) {
            convict.citizenship = country;
            return this;
        }

        public Builder setPlaceAndTimeOfBirth(String ptb) {
            convict.placeAndTimeOfBirth = ptb;
            return this;
        }

        public Builder setLastPlaceOfHabitat(String lph) {
            convict.lastPlaceOfHabitat = lph;
            return this;
        }

        public Builder setLanguages(List<Language> languages) {
            convict.languages = languages;
            return this;
        }

        public Builder setCriminalSpecialization(String cs) {
            convict.criminalSpecialization = cs;
            return this;
        }

        public Builder setLastCriminalCase(String lcc) {
            convict.lastCriminalCase = lcc;
            return this;
        }

        public Builder setCriminalOrganizations(List<CriminalOrganization> co) {
            convict.criminalOrganization = co;
            return this;
        }

        public Builder setAccomplices(List<Convict> accomplices) {
            convict.accomplices = accomplices;
            return this;
        }

        public Convict build() {
            return convict;
        }

    }

    public String listLanguages() {
        return String.join(", ", languages.stream().map(Language::getLanguage).toArray(String[]::new));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Convict convict = (Convict) o;

        return convict.getId().equals(this.Id);
    }

    @Override
    public int hashCode() {
        return Id != null ? Id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Convict{" +
                "\n\tId : " + Id +
                "\n\tname : '" + name + '\'' +
                "\n\tsurname : '" + surname + '\'' +
                "\n\talias : '" + alias + '\'' +
                "\n\theight : " + height +
                "\n\thairColor : " + hairColor +
                "\n\teyeColor : " + eyeColor +
                "\n\tdistinguishingFeatures : '" + distinguishingFeatures + '\'' +
                "\n\tcitizenship : " + citizenship +
                "\n\tplaceAndTimeOfBirth : '" + placeAndTimeOfBirth + '\'' +
                "\n\tlastPlaceOfHabitat : '" + lastPlaceOfHabitat + '\'' +
                "\n\tlanguages : " + languages +
                "\n\tcriminalSpecialization : '" + criminalSpecialization + '\'' +
                "\n\tlastCriminalCase : '" + lastCriminalCase + '\'' +
                "\n\tcriminalOrganization : " + criminalOrganization +
                // Replace accomplice toString method with id in order
                // to not run into a console output lock
                "\n\taccomplices : " + accomplices
                .stream()
                .map(acc -> Long.toString(acc.getId()))
                .reduce("'ids:", (a1, a2) -> a1 + " " + a2) + '\'' +
                "\n}";
    }
}
