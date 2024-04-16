/**
 * The reported status of a Gopher resource.
 * <p>
 * OK - The server returned a response that was not an error.
 * <p>
 * ERROR - The server returned an error code, or the client was forced to abort
 * due to connection issues (e.g. timeout).
 * <p>
 * FOREIGN - The resource was located on an external server, defined in this project
 * to be any location with a different hostname or port.
 */
public enum Status {
    OK,
    ERROR,
    FOREIGN
}
