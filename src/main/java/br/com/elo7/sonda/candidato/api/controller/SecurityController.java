package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.dto.security.LoginDTO;
import br.com.elo7.sonda.candidato.api.dto.security.TokenDTO;
import br.com.elo7.sonda.candidato.api.service.ITokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Security.PATH;

@RestController
@RequestMapping(PATH)
public class SecurityController {

	private AuthenticationManager authManager;

	private ITokenService tokenService;

	public SecurityController(AuthenticationManager authManager, ITokenService tokenService) {
		this.authManager = authManager;
		this.tokenService = tokenService;
	}

	@PostMapping
	public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginDTO form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.convert();

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.createToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}

