package com.universitatea.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.universitatea.prototype.Prototype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
public class Group implements Prototype<Group> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_id_seq")
    @SequenceGenerator(name = "group_id_seq", sequenceName = "group_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    // Constructor privat pentru Builder
    private Group(GroupBuilder builder) {
        this.groupCode = builder.groupCode;
        this.year = builder.year;
        this.specialization = builder.specialization;
        this.faculty = builder.faculty;
    }

    public static class GroupBuilder {
        private String groupCode;
        private Integer year;
        private String specialization;
        private Faculty faculty;

        public GroupBuilder setGroupCode(String groupCode) {
            this.groupCode = groupCode;
            return this;
        }

        public GroupBuilder setYear(Integer year) {
            this.year = year;
            return this;
        }

        public GroupBuilder setSpecialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public GroupBuilder setFaculty(Faculty faculty) {
            this.faculty = faculty;
            return this;
        }

        public Group build() {
            if (groupCode == null || year == null || specialization == null || faculty == null) {
                throw new IllegalStateException("All fields must be set, including faculty");
            }
            return new Group(this);
        }
    }

    // Prototype
    @Override
    public Group clone() {
        Group cloned = new Group();
        cloned.setGroupCode(this.groupCode);
        cloned.setYear(this.year);
        cloned.setSpecialization(this.specialization);
        cloned.setFaculty(this.faculty);
        return cloned;
    }
}
