package ch.carve.homemagic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class LightServiceTest {

    @InjectMocks
    LightService lightService;
    
    @Test
    public void testToggle() throws Exception {
        assertNotNull(lightService.getList());
    }

}
