package io.gtrain.service.email;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.Verification;
import io.gtrain.service.verification.VerificationService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

/**
 * @author William Gentry
 */
@Service
public class OutgoingVerificationEmailService implements EmailService {

	private final static String VERIFICATION_URL = "http://localhost:8080/verify/email?id=";
	private final JavaMailSender javaMailSender;
	private final VerificationService verificationService;

	public OutgoingVerificationEmailService(JavaMailSender javaMailSender, VerificationService verificationService) {
		this.javaMailSender = javaMailSender;
		this.verificationService = verificationService;
	}

	@Override
	public Mono<GlmsMember> sendEmail(GlmsMember member) {
		return verificationService.createVerification(member.getUsername())
					.filter(StringUtils::hasText)
					.flatMap(verification -> {
						try {
							MimeMessage message = javaMailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
							helper.setFrom("admin@glms.com");
							helper.setSubject("GLMS Activation Link");
							helper.setText(getVerificationEmailBody(verification), true);
							helper.setTo(member.getEmail());
							javaMailSender.send(message);
							return Mono.just(member);
						} catch (MessagingException e) {
							e.printStackTrace();
							return Mono.defer(() -> Mono.error(() -> new IllegalStateException("Failed to send email to " + member.getEmail())));
						}
					});


	}


	private String getVerificationEmailBody(String verificationId) {
		return new StringBuilder()
					.append("Activate your account <a href=\"")
					.append(VERIFICATION_URL)
					.append(verificationId)
					.append("\">here</a>!")
					.toString();
	}
}
