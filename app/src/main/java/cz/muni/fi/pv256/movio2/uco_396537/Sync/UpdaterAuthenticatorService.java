package cz.muni.fi.pv256.movio2.uco_396537.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by david on 8.1.17.
 */

public class UpdaterAuthenticatorService extends Service {

    private UpdaterAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new UpdaterAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
