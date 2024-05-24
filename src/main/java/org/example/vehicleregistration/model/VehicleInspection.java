package org.example.vehicleregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class VehicleInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private InspectionTerm dateTime;

    @ManyToOne
    private User user;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private InspectionCenter inspectionCenter;

    public VehicleInspection() {
    }

    public VehicleInspection(InspectionTerm dateTime, User user, Vehicle vehicle, InspectionCenter inspectionCenter) {
        this.dateTime = dateTime;
        this.user = user;
        this.vehicle = vehicle;
        this.inspectionCenter = inspectionCenter;
    }
}
