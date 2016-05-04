package net.appifiedtech.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import net.appifiedtech.adapter.AccountsListAdapter;
import net.appifiedtech.models.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AccountManager accountManager;
    private Button btnAdd,btnShow;
    private ListView listView;
    private AccountsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountManager = AccountManager.get(this);
        btnAdd = (Button) findViewById(R.id.button1);
        btnAdd.setOnClickListener(this);
        btnShow = (Button) findViewById(R.id.button2);
        btnShow.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.button1)
        {
            startActivity(new Intent(getApplicationContext(),AuthenticatorActivity.class));
        }
        else if(id == R.id.button2)
        {
            adapter = new AccountsListAdapter(getApplicationContext(),R.layout.row_layout,getAllAccounts());
            listView.setAdapter(adapter);
        }
    }

    public ArrayList<Item> getAllAccounts(){
        ArrayList<Item> accountsList = new ArrayList<Item>();
        Account [] accounts = accountManager.getAccountsByType(getString(R.string.account_type));
        for(Account acc: accounts)
        {
            Item item = new Item(acc.type,acc.name);
            accountsList.add(item);
        }
        return accountsList;
    }
}
