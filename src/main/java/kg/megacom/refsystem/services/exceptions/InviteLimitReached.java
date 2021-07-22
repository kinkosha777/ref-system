package kg.megacom.refsystem.services.exceptions;

public class InviteLimitReached extends RuntimeException {

    public InviteLimitReached(String message) {
        super(message);
    }
}
