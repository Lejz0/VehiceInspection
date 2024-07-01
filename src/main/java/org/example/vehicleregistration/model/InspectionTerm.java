package org.example.vehicleregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class InspectionTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("inspections")
    private InspectionCenter inspectionCenter;

    private LocalDate date;

    private LocalTime time;

    private boolean isScheduled;

    public InspectionTerm() {

    }

    public InspectionTerm(InspectionCenter inspectionCenter, LocalDate date, LocalTime time, boolean isScheduled) {
        this.inspectionCenter = inspectionCenter;
        this.date = date;
        this.time = time;
        this.isScheduled = isScheduled;
    }


}
