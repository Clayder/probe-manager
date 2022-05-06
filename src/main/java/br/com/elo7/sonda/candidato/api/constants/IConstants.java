package br.com.elo7.sonda.candidato.api.constants;

public interface IConstants {

    interface Controller {
        String VERSION = "/api/v1";
        String SLUG = "{id}";

        interface Planet {
            String NAME = "/planets";
            String PATH = VERSION + NAME;
            String PLANET_POBE = SLUG + Probe.NAME;
            String PLANET_POBE_ID = PLANET_POBE + "/{probe_id}";
        }

        interface Probe {
            String NAME = "/probes";
            String PATH = VERSION + NAME;
        }

        interface Security {
            String NAME = "/auth";
            String PATH = VERSION + NAME;
        }

        interface Message {

            interface DefaultHttp {
                String DESCRIPTION_500 = "Erro interno.";
                String DESCRIPTION_400 = "Erro de validação.";
                String DESCRIPTION_422 = "Ocorreu erro com a regra de negócio.";
                String DESCRIPTION_404 = "Registro não encontrado.";
                String DESCRIPTION_201 = "Dados registrados com sucesso.";
                String DESCRIPTION_200 = "Requisição realizada com sucesso.";
                String DESCRIPTION_204 = "A requisição foi processada com sucesso.";
            }

            interface Planet extends IConstants.Controller.Message.DefaultHttp {

                String SUMMARY_REGISTER = "Realiza a criação do planeta, insere as sondas e executa a movimentação de cada sonda no planeta através dos comandos.";
                String DESCRIPTION_REGISTER_201 = "Planeta, sondas criadas e comandos realizados com sucesso.";
                String DESCRIPTION_REGISTER_PROBE_BY_PLANET_404 = "Planeta não encontrado.";
                String SUMMARY_INSERT = "Cadastrar um planeta.";
                String SUMMARY_UPDATE = "Realizar atualização dos dados de um planeta.";
                String SUMMARY_DELETE = "Realizar a exclusão lógica de um planeta.";
                String SUMMARY_LIST = "Listar os planetas.";
                String SUMMARY_PATCH_WIDTH_HEIGHT = "Atualizar o tamanho de um planeta.";
                String SUMMARY_REGISTER_PROBE_BY_PLANET = "Inserir e movimentar a sonda em um planeta específico.";

                String SUMMARY_GET = "Retornar um planeta.";
            }

            interface Probe extends IConstants.Controller.Message.DefaultHttp {

                String DESCRIPTION_MOVE_PROBE_404 = "Planeta não encontrado.";
                String SUMMARY_MOVE_PROBE = "Realizar a movimentação de uma sonda em um planeta específico.";
                String SUMMARY_DELETE = "Realizar a exclusão lógica de uma sonda.";
                String SUMMARY_LIST = "Listar as sondas.";

                String SUMMARY_GET = "Retornar uma sonda.";
            }

            interface Security extends IConstants.Controller.Message.DefaultHttp {
                String SUMMARY_AUTHENTICATE = "Gerar token JWT";
            }

        }
    }

    interface MessageError {

        interface Default {
            String REQUIRED_FIELD = "Required field.";
            String GREATER_THAN_ZERO = "Value must be greater than 0";
        }

        interface Planet extends Default {
            int NAME_SIZE_MIN = 2;
            int PASSWORD_SIZE_MIN = 4;
            int PASSWORD_SIZE_MAX = 8;
            int NAME_SIZE_MAX = 15;
            String NAME_LENGTH_FIELD = "The length must be between " + NAME_SIZE_MIN + " and " + NAME_SIZE_MAX + " characters.";
            String PASSWORD_LENGTH_FIELD = "The length must be between " + PASSWORD_SIZE_MIN + " and " + PASSWORD_SIZE_MAX + " characters.";

        }
    }

}
