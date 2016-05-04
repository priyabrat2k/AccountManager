package net.appifiedtech.accountmanager;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

    public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String AUTH_TYPE = "AUTH_TYPE";
    public final static String IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    private AccountManager accountManager;
    private String accountName, accountType,accountPass;
    private EditText editTextAccountName, editTextAccountPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);
        editTextAccountName = (EditText) findViewById(R.id.accountName);
        editTextAccountPass = (EditText) findViewById(R.id.accountPassword);
        accountManager = AccountManager.get(getApplicationContext());
        accountName = getIntent().getStringExtra(ACCOUNT_NAME);
        accountType = getIntent().getStringExtra(ACCOUNT_TYPE);
        if(accountType == null)
            accountType = getString(R.string.account_type);
        findAccount(accountName);
    }

    public void makeLogin(View view) {
        accountName = editTextAccountName.getText().toString();
        accountPass = editTextAccountPass.getText().toString();
        if(accountName.length() > 0)
        {
            Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ACCOUNT_NAME,accountName);
            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE,accountType);
            String authToken = "45dfdfrg555TT";
            bundle.putString(AccountManager.KEY_AUTHTOKEN,authToken);

            Bundle userData = new Bundle();
            userData.putString("UserId","24");
            bundle.putBundle(AccountManager.KEY_USERDATA,userData);

            Intent intent = new Intent();
            intent.putExtras(bundle);

            Account account = new Account(accountName,accountType);
            if(accountManager.addAccountExplicitly(account,accountPass,bundle))
            {
                // Account Add Success
                accountManager.setAuthToken(account,accountType,authToken);
                setAccountAuthenticatorResult(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
            else
            {
                // Account Add Failed
                Toast.makeText(AuthenticatorActivity.this, "Add Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Account findAccount(String accountName){
        Account [] accounts = accountManager.getAccounts();
        for(Account account: accounts)
        {
            if(TextUtils.equals(account.name,accountName) && TextUtils.equals(account.type,getString(R.string.account_type))){
                return account;
            }
        }
        return null;
    }
}
