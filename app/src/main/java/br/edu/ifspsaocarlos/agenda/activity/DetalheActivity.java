package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;


public class DetalheActivity extends AppCompatActivity {
    //private List<Contato> mContato;
    private long id;
    private Contato mContato;
    private Realm realm;
    private ContatoDAO cDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Realm database = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contato")){

            id = getIntent().getLongExtra("contato",0L);
            mContato = database.where(Contato.class).equalTo("id", id).findFirst();

            EditText nameText = (EditText)findViewById(R.id.editTextNome);
            nameText.setText(mContato.getNome());
            EditText foneText = (EditText)findViewById(R.id.editTextFone);
            foneText.setText(mContato.getFone());
            EditText fone2Text = (EditText)findViewById(R.id.editTextFone2);
            fone2Text.setText(mContato.getFone2());
            EditText emailText = (EditText)findViewById(R.id.editTextEmail);
            emailText.setText(mContato.getEmail());
            EditText aniverText = (EditText) findViewById(R.id.editTextAniver);
            aniverText.setText(mContato.getAniver());
            CheckBox favoritar = (CheckBox) findViewById(R.id.cbFavoritar);
            favoritar.setChecked(this.mContato.getFavorito());  //faltava passar o valor ...

            int pos =this.mContato.getNome().indexOf(" ");
            if (pos==-1)
                pos=this.mContato.getNome().length();
            setTitle(this.mContato.getNome().substring(0,pos));
        }
        cDAO = new ContatoDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato"))
        {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar()
    {
        cDAO.apagaContato(mContato);
        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        String name = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String aniver=((EditText) findViewById(R.id.editTextAniver)).getText().toString();
        Boolean isfavorito = ((CheckBox) findViewById(R.id.cbFavoritar)).isChecked() ? true : false;  //

        if (mContato==null) {
            mContato = new Contato();
        }else{
            mContato = new Contato();}
        mContato.setId(id);
        mContato.setNome(name);
        mContato.setFone(fone);
        mContato.setFone2(fone2);
        mContato.setEmail(email);
        mContato.setAniver(aniver);
        mContato.setFavorito(isfavorito);
        cDAO.salvaContato(mContato);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}

