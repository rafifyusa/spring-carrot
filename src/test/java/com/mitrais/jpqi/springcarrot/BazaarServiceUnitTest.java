package com.mitrais.jpqi.springcarrot;

import com.mitrais.jpqi.springcarrot.model.Bazaar;
import com.mitrais.jpqi.springcarrot.repository.BazaarRepository;
import com.mitrais.jpqi.springcarrot.service.BazaarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BazaarServiceUnitTest {

    @Autowired
    BazaarService bazaarService;

    @MockBean
    private BazaarRepository bazaarRepository;

    @Test
    public void whenFindByStatus_thenReturnBazaarList() {
        //given
        List<Bazaar> bazaarList = new ArrayList<>();

        //when
        List<Bazaar> found = bazaarService.findByStatus(true);
        System.out.println(found.size());
        assertThat(found).isEqualTo(bazaarList);
    }

    @Test
    public void whenCalledReturnTrue1() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    public void whenCalledReturnTrue2() {
        assertThat(2).isEqualTo(2);
    }

    @Test
    public void whenCalledReturnTrue3() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    public void whenCalledReturnTrue4() {
        assertThat(2).isEqualTo(2);
    }


}
