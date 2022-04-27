package edu.uoc.epcsd.showcatalog.mappers;

import edu.uoc.epcsd.showcatalog.dtos.PerformanceDTO;
import edu.uoc.epcsd.showcatalog.entities.Performance;
import edu.uoc.epcsd.showcatalog.requests.PerformanceRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PerformanceMapper {

    public Performance map(PerformanceRequest request) {
        Performance performance = new Performance();
        performance.setDate(request.getDate());
        performance.setTime(request.getTime());
        performance.setStreamingURL(request.getStreamingURL());
        performance.setRemainingSeats(500);
        performance.setStatus(true);
        return performance;
    }

    public List<PerformanceDTO> mapToDTO(Set<Performance> performances) {
        List<PerformanceDTO> performancesDTO = new ArrayList<>();

        for (Performance p : performances) {
            PerformanceDTO performanceDTO = new PerformanceDTO();
            performanceDTO.setStreamingURL(p.getStreamingURL());
            performanceDTO.setDate(p.getDate());
            performanceDTO.setTime(p.getTime());
            performancesDTO.add(performanceDTO);
        }
        return performancesDTO;
    }

}
