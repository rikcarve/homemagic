package ch.carve.homemagic;

import ch.carve.homemagic.model.Temperature;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TemperatureServiceTest {

    @InjectMocks
    TemperatureService service;

    @Mock
    InfluxDBClient influxDBClient;

    @Mock
    QueryApi queryApi;

    @BeforeEach
    public void setup() {
        // Mockito will inject mocks into the service. Only stub the query API retrieval here.
        when(influxDBClient.getQueryApi()).thenReturn(queryApi);
    }

    @Test
    public void testGetLastHours_filtersNullValuesAndFormats() {
        // prepare mocks: a FluxTable with two records (one with value, one with null)
        FluxTable table = mock(FluxTable.class);
        FluxRecord recordWithValue = mock(FluxRecord.class);
        FluxRecord recordNull = mock(FluxRecord.class);

        Instant instant = Instant.parse("2026-03-15T12:34:00Z");
        when(recordWithValue.getTime()).thenReturn(instant);
        when(recordWithValue.getValue()).thenReturn(21.3);

        when(recordNull.getValue()).thenReturn(null);

        when(table.getRecords()).thenReturn(List.of(recordWithValue, recordNull));
        when(queryApi.query(anyString())).thenReturn(List.of(table));

        List<Temperature> temps = service.getLastHours(6, 1, "sensor1");

        assertNotNull(temps);
        assertEquals(1, temps.size(), "should only contain the record with a non-null value");

        Temperature t = temps.get(0);
        assertEquals(instant, t.getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault());
        assertEquals(formatter.format(instant), t.getTimeString());

        assertEquals(String.format("%.1f", 21.3), t.getTemperature());
    }
}
