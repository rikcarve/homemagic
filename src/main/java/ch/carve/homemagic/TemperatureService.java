package ch.carve.homemagic;

import ch.carve.homemagic.model.Temperature;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import lombok.extern.slf4j.Slf4j;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class TemperatureService {

    private char[] token = "42qa5fiPhDGSNX85VlwucZGhB27o2_cCCi50sAogD5Rz8EzwbbECRgQImytfVPmSgZa1WoI2Sr4ujVBg2vR1gQ==".toCharArray();
    private String organisation = "039f976316bae1f4";
    private String bucket = "zigbee";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault());

    private InfluxDBClient influxDBClient;

    @PostConstruct
    public void init() {
        influxDBClient = InfluxDBClientFactory.create("http://berry2:8086", token, organisation, bucket);
    }

    @PreDestroy
    public void cleanup() {
        influxDBClient.close();
    }

    public List<Temperature> getLastHours(long hours, long interval, String sensor) {
        var restriction = Restrictions.and(
                Restrictions.column("topic").equal("zigbee2mqtt/" + sensor),
                Restrictions.field().equal("temperature")
        );
        String flux = Flux.from("zigbee")
                .range(-hours, ChronoUnit.HOURS)
                .filter(restriction)
                .aggregateWindow(interval, ChronoUnit.HOURS, "max")
                .toString();
        List<FluxTable> query = influxDBClient.getQueryApi().query(flux);

        List<Temperature> result = new ArrayList<>();
        return query.stream().flatMap(fluxTable -> fluxTable.getRecords().stream())
                .filter(r -> r.getValue() != null)

                .peek(r -> log.info(r.getTime() + "   " + formatter.format(r.getTime()) + ": " + r.getValue()))
                .map(r -> new Temperature(r.getTime(), formatter.format(r.getTime()), String.format("%.1f",r.getValue()))).collect(Collectors.toList());
    }
}
