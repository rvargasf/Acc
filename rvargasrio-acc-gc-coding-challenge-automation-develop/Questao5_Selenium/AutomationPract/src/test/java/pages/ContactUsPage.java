package pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;

import common.BasePage;

public class ContactUsPage extends BasePage {

	public final String CONTACTUS_HEADER_LINK = "contact-link";
	public final String SUBJECT_HEADING = "id_contact";
	public final String EMAIL_ADDRESS = "email";
	public final String ORDER_REFERENCE = "id_order";
	public final String ATTACH_FILE = "uniform-fileUpload";
	public final String MESSAGE = "message";
	public final String SEND_BUTTON = "submitMessage";
	public final String SUCCESS_MESSAGE = "//p[contains(text(),'Your message has been successfully sent to our team.')]";
	public final String INVALID_MAIL_MESSAGE = "//li[contains(text(),'Invalid email address.')]";

	public void verificarElementosTela() {
		validarTitulo("Contact us - My Store");
		elementoVisivel(By.id(SUBJECT_HEADING));
		elementoVisivel(By.id(EMAIL_ADDRESS));
		elementoVisivel(By.id(ORDER_REFERENCE));
		elementoVisivel(By.id(ATTACH_FILE));
		elementoVisivel(By.id(MESSAGE));
		elementoVisivel(By.id(SEND_BUTTON));

	}

	public void submeterFormulario() {
		click(By.id(SEND_BUTTON));
	}

	public void informarMensagem() {
		escrever(By.id(MESSAGE), "Lorem ipsum dolor sit amet.");
	}

	public void informarReferencia() {
		escrever(By.id(ORDER_REFERENCE), "ACC-01239876");
	}

	public void informarEmail() {
		escrever(By.id(EMAIL_ADDRESS), "lorem@ipsum.com");
	}

	public void validarMensagemSucessoEnvio(String message) {
		aguardarCarregamentoPagina();
		String element = obterTexto(By.xpath(SUCCESS_MESSAGE));
		assertThat(element).isEqualTo(message);
	}

	public void validarMensagemErroEmail(String message) {
		aguardarCarregamentoPagina();
		String element = obterTexto(By.xpath(INVALID_MAIL_MESSAGE));
		assertThat(element).isEqualTo(message);
	}

	public void selecionarSubjectHeading() {
		selecionarCombo(SUBJECT_HEADING, "Customer service");
	}

}
