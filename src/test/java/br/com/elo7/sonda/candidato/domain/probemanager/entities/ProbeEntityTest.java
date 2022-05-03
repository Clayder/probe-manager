package br.com.elo7.sonda.candidato.domain.probemanager.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProbeEntityTest {

	ProbeEntity probe;

	@BeforeEach
    public void setUp() {
        this.probe = new ProbeEntity();
		PlanetEntity planet = new PlanetEntity();
		planet.setHeight(5);
		planet.setWidth(5);

		this.probe.setPlanetEntity(planet);
    }

    @Test
	@DisplayName("Should change probe direction from N To W when receive the command L.")
	public void should_change_probe_direction_from_N_To_W_when_receive_the_command_L() {
		this.probe.setDirection('N');
		this.probe.applyCommandToProbe('L');

		assertThat('W').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from W To S when receive the command L.")
	public void should_change_probe_direction_from_W_To_S_when_receive_the_command_L() {
		this.probe.setDirection('W');
		this.probe.applyCommandToProbe('L');

		assertThat('S').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from S To E when receive the command L.")
	public void should_change_probe_direction_from_S_To_E_when_receive_the_command_L() {
		this.probe.setDirection('S');
		this.probe.applyCommandToProbe('L');

		assertThat('E').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from E To N when receive the command L.")
	public void should_change_probe_direction_from_E_To_N_when_receive_the_command_L() {
		this.probe.setDirection('E');
		this.probe.applyCommandToProbe('L');

		assertThat('N').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from N To E when receive the command R.")
	public void should_change_probe_direction_from_N_To_E_when_receive_the_command_R() {
		this.probe.setDirection('N');
		this.probe.applyCommandToProbe('R');

		assertThat('E').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from E To S when receive the command R.")
	public void should_change_probe_direction_from_E_To_S_when_receive_the_command_R() {
		this.probe.setDirection('E');
		this.probe.applyCommandToProbe('R');

		assertThat('S').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from S To W when receive the command R.")
	public void should_change_probe_direction_from_S_To_W_when_receive_the_command_R() {
		this.probe.setDirection('S');
		this.probe.applyCommandToProbe('R');

		assertThat('W').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe direction from W To N when receive the command R.")
	public void should_change_probe_direction_from_W_To_N_when_receive_the_command_R() {
		this.probe.setDirection('W');
		this.probe.applyCommandToProbe('R');

		assertThat('N').isEqualTo(this.probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe position from 1 1 N To 1 2 N when receive the command M.")
	public void should_change_probe_position_from_1_1_N_To_1_2_N_when_receive_the_command_M() {
		this.probe.setX(1);
		this.probe.setY(1);
		this.probe.setDirection('N');
		this.probe.applyCommandToProbe('M');

		assertThat(2).isEqualTo(probe.getY());
		assertThat(1).isEqualTo(probe.getX());
		assertThat('N').isEqualTo(probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe position from 1 1 S To 1 0 S when receive the command M.")
	public void should_change_probe_position_from_1_1_S_To_1_0_S_when_receive_the_command_M() {
		this.probe.setX(1);
		this.probe.setY(1);
		this.probe.setDirection('S');
		this.probe.applyCommandToProbe('M');

		assertThat(0).isEqualTo(probe.getY());
		assertThat(1).isEqualTo(probe.getX());
		assertThat('S').isEqualTo(probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe position from 1 1 W To 0 1 W when receive the command M.")
	public void should_change_probe_position_from_1_1_W_To_0_1_W_when_receive_the_command_M() {
		this.probe.setX(1);
		this.probe.setY(1);
		this.probe.setDirection('W');
		this.probe.applyCommandToProbe('M');

		assertThat(0).isEqualTo(probe.getX());
		assertThat(1).isEqualTo(probe.getY());
		assertThat('W').isEqualTo(probe.getDirection());
	}

	@Test
	@DisplayName("Should change probe position from 1 1 E To 2 1 E when receive the command M.")
	public void should_change_probe_position_from_1_1_E_To_2_1_E_when_receive_the_command_M() {
		this.probe.setX(1);
		this.probe.setY(1);
		this.probe.setDirection('E');
		this.probe.applyCommandToProbe('M');

		assertThat(2).isEqualTo(probe.getX());
		assertThat(1).isEqualTo(probe.getY());
		assertThat('E').isEqualTo(probe.getDirection());
	}


}
