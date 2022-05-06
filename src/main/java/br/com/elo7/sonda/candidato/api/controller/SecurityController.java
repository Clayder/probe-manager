package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.dto.security.LoginDTO;
import br.com.elo7.sonda.candidato.api.dto.security.TokenDTO;
import br.com.elo7.sonda.candidato.api.service.ITokenService;
import br.com.elo7.sonda.candidato.domain.exceptions.controller.StandardError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.DefaultHttp.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.Security.SUMMARY_AUTHENTICATE;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Security.PATH;

@RestController
@RequestMapping(PATH)
@Tag(name = "Autenticação")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "500", description = DESCRIPTION_500, content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = StandardError.class))
                })
        }
)
public class SecurityController {

    private AuthenticationManager authManager;

    private ITokenService tokenService;

    public SecurityController(AuthenticationManager authManager, ITokenService tokenService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = SUMMARY_AUTHENTICATE)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = DESCRIPTION_200,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = TokenDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = @Content),
            }
    )
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

