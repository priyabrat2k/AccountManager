package net.appifiedtech.accountmanager;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Priyabrat on 04-05-2016.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    @Override
    public IBinder onBind(Intent intent) {
        if(intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT))
        {
            return getAuthenticator().getIBinder();
        }
        else
            return null;
    }

    private Authenticator getAuthenticator(){
        if(authenticator == null)
            authenticator = new Authenticator(this);
        return authenticator;
    }
}
