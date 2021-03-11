package steps;

import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.ContactUsPage;

public class ContactUsStep {

	ContactUsPage contactUs = new ContactUsPage();

	@Dado("que desejo submeter o formulário de contato")
	public void que_desejo_submeter_o_formulário_de_contato() {
		contactUs.iniciarWebDriver();
		contactUs.goToUrl();
		contactUs.verificarElementosTela();
	}

	@Quando("eu informo <Subject Heading>")
	public void eu_informo_subject_heading() {
		contactUs.selecionarSubjectHeading();
	}

	@Quando("eu informo <Email address>")
	public void eu_informo_email_address() {
		contactUs.informarEmail();
	}

	@Quando("eu informo <Order reference>")
	public void eu_informo_order_reference() {
		contactUs.informarReferencia();
	}

	@Quando("eu informo <Message>")
	public void eu_informo_message() {
		contactUs.informarMensagem();
	}

	@Quando("eu seleciono botão Send")
	public void eu_seleciono_botão_send() {
		contactUs.submeterFormulario();
	}

	@Então("eu devo ver a mensagem {string}")
	public void eu_devo_ver_a_mensagem(String string) {
		contactUs.validarMensagemSucessoEnvio(string);
	}

	@Então("eu devo ver a mensagem de erro para email {string}")
	public void eu_devo_ver_a_mensagem_de_erro_para_email(String string) {
		contactUs.validarMensagemErroEmail(string);
	}

	@After()
	public void encerrarNavegador() {
		contactUs.encerrarWebDriver();
	}
}
