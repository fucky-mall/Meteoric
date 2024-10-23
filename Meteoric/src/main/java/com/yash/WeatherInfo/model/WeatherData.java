package com.yash.WeatherInfo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pincode;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private LocalDate date;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private MainData main;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Weather> weather;

    @OneToOne(cascade = CascadeType.ALL)
    private Wind wind;
}
