package com.backend.Backend.myTables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
/*Creo un record( classe immutabile) per contenere i valore di ogni ora lavorativa giornaliera e dei rispettivi datiImpiegati  */
public record OraImpiegatoRecord(Long oraId, LocalDate data, LocalTime inizio, LocalTime fine,
                                  List<Map<String,String>> datiImpiegati) {}
