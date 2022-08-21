package ch.carve.homemagic;

import ch.carve.homemagic.model.Temperature;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class TemperatureService {

    private char[] token = "42qa5fiPhDGSNX85VlwucZGhB27o2_cCCi50sAogD5Rz8EzwbbECRgQImytfVPmSgZa1WoI2Sr4ujVBg2vR1gQ==".toCharArray();
    private String organisation = "039f976316bae1f4";
    private String bucket = "zigbee";

    private InfluxDBClient influxDBClient;

    @PostConstruct
    public void init() {
        influxDBClient = InfluxDBClientFactory.create("http://berry2:8086", token, organisation, bucket);
    }

    @PreDestroy
    public void cleanup() {
        influxDBClient.close();
    }

    public Set<Temperature> getLastHours(long hours) {
        var restriction = Restrictions.and(
                Restrictions.column("topic").equal("zigbee2mqtt/temperature1"),
                Restrictions.field().equal("temperature")
        );
        String flux = Flux.from("zigbee")
                .range(-hours, ChronoUnit.HOURS)
                .filter(restriction)
                .aggregateWindow(1L, ChronoUnit.HOURS, "max")
                .toString();
        List<FluxTable> query = influxDBClient.getQueryApi().query(flux);

        List<Temperature> result = new ArrayList<>();
        return query.stream().flatMap(fluxTable -> fluxTable.getRecords().stream())
                .map(r -> new Temperature(r.getTime(), (Double) r.getValue())).collect(Collectors.toSet());
    }
}
