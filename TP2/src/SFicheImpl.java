/** Implementation of a serializable data slate.
 *
 * Created by saradion on 05/10/16.
 */
public class SFicheImpl implements SFiche {
    private String name;
    private String mail;

    public SFicheImpl(String new_name, String new_mail) {
        name = new_name;
        mail = new_mail;
    }

    @Override
    public String getNom() {
        return name;
    }

    @Override
    public String getEmail() {
        return mail;
    }
}
