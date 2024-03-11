package com.si_pmu.pmu.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true, exclude = {"course"})
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Partant extends BasicEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_ID")
    @JsonBackReference
    private Course course;

    @Override
    public String toString() {
        return super.toString();
    }
}
