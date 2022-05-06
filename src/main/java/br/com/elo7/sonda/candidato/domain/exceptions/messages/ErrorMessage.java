package br.com.elo7.sonda.candidato.domain.exceptions.messages;

public interface ErrorMessage {

    String NOT_FOUND = "Not found.";
    String PROBE_CANNOT_LEAVE_PLANET = "The probe cannot leave the planet.";

    String PLANET_NOT_FOUND = "Planet not found.";
    String PROBE_NOT_FOUND = "Probe not found.";
    String DUPLICATE_PLANET = "Planet already registered.";
    String AVOID_COLLISION_BETWEEN_PROBES = "There is a probe in that position.";

    String INVALID_COMMAND = "Invalid command";
    String INVALID_DIRECTION = "Invalid direction";



}
